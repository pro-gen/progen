package progen.kernel.population;

import java.util.HashMap;
import java.util.Map;

import progen.context.ProGenContext;
import progen.kernel.functions.ADF;
import progen.kernel.functions.Terminal;
import progen.kernel.grammar.Grammar;
import progen.kernel.grammar.GrammarTerminalSymbol;
import progen.kernel.tree.Tree;
import progen.roles.Task;
import progen.userprogram.UserProgram;

/**
 * Clase que representa la información necesaria para representar un individuo
 * completo en programación genética.
 * 
 * @author jirsis
 * @since 1.0
 * 
 */
public class Individual implements Task, Comparable<Individual>, Cloneable {
  private static final String COLON_SPACE_SYMBOL = ": ";

  private static final String RETURN_SYMBOL = "\n";

  private static final String ADF_PROPERTY = "ADF";

  private static final String RPB_PROPERTY = "RPB";

  private static final String PROGEN_TOTAL_RPB_PROPERTY = "progen.total.RPB";

  private static final long serialVersionUID = -3776497075849167016L;

  /**
   * Árboles que definen el individuo como tal. Se almacenan tanto RPBs como
   * ADFs y se identifican siguiendo la nomenclatura RPBi, ADFi, donde i es un
   * número [0, máximo árbol definido).
   */
  private Map<String, Tree> trees;

  /**
   * Resultados de la evaluación de los árboles. Únicamente tiene sentido
   * almacenar el resultado de los RPB dado que son éstos los únicos que pueden
   * ser evaluados.
   */
  private Map<String, Object> results;

  /**
   * Gramáticas que generan todos y cada unos de los árboles que forman el
   * individuo concreto.
   */
  private Map<String, Grammar> grammars;

  /**
   * Número total de árboles RPB en un individuo concreto.
   */
  private int totalRPB;

  /**
   * Número total de árboles ADF en un individuo concreto.
   */
  private int totalADF;

  /**
   * Valor del <code>raw-fitness</code> resultante de ejecutar la función de
   * evaluación para este individuo.
   */
  private double rawFitness;

  /**
   * Indica si es necesario reevaluar los árboles de un individuo antes de
   * devolver los resultados. Es una optimización que evita tener que recalcular
   * constantemente todos los árboles aún cuando no hayan cambiado los valores
   * de las variables definidas en dichos árboles.
   */
  private boolean updated;

  /**
   * Tabla en la que se almacena el valor concreto de todas las variables
   * disponibles en la ejecución de un individuo.
   */
  private Map<String, Object> variables;

  /**
   * Representación del individuo para ser impresa de alguna forma. Modifica la
   * versión estándar en la que se muestran todos los árboles (ADF y RPB) que
   * definen un individuo.
   */
  private String printabeIndividual;

  /**
   * Constructor genérico de la clase. Recibe el conjunto de gramáticas que se
   * utilizarán para generar los árboles que definen el indidivuo.
   * 
   * @param grammars
   *          Gramáticas que generarán todos los árboles, tanto RPBs como ADFs
   *          que conforman un individuo concreto.
   */
  public Individual(Map<String, Grammar> grammars) {
    Tree tree;
    trees = new HashMap<String, Tree>();
    results = new HashMap<String, Object>();

    totalRPB = ProGenContext.getOptionalProperty(PROGEN_TOTAL_RPB_PROPERTY, 1);
    ProGenContext.setProperty(PROGEN_TOTAL_RPB_PROPERTY, String.valueOf(totalRPB));
    totalADF = ProGenContext.getOptionalProperty("progen.total.ADF", 0);

    for (int i = 0; i < totalRPB; i++) {
      tree = new Tree();
      tree.generate(grammars.get(RPB_PROPERTY + i));
      trees.put(RPB_PROPERTY + i, tree);
    }

    for (int i = 0; i < totalADF; i++) {
      tree = new Tree();
      tree.generate(grammars.get(ADF_PROPERTY + i));
      trees.put(ADF_PROPERTY + i, tree);
    }

    this.grammars = grammars;
    this.rawFitness = Double.MAX_VALUE;
    this.updated = true;
    this.variables = new HashMap<String, Object>();
    this.printabeIndividual = null;
  }

  /**
   * Constructor de copia que crea un nuevo individuo a partir de otro
   * proporcionado como parámetro.
   * 
   * @param individual
   *          El individuo a copiar.
   */
  public Individual(Individual individual) {
    this.rawFitness = individual.rawFitness;
    this.results = new HashMap<String, Object>();
    this.totalRPB = individual.totalRPB;
    this.totalADF = individual.totalADF;
    this.grammars = individual.grammars;
    this.updated = individual.updated;
    this.trees = new HashMap<String, Tree>();
    for (String key : individual.trees.keySet()) {
      this.trees.put(key, new Tree(individual.trees.get(key)));
    }
    this.variables = new HashMap<String, Object>();
    for (String key : individual.variables.keySet()) {
      this.variables.put(key, individual.variables.get(key));
    }

  }

  /**
   * Devuelve los árboles que contiene el individuo.
   * 
   * @return Los árboles que contiene el individuo.
   */
  public Map<String, Tree> getTrees() {
    return trees;
  }

  /**
   * Devuelve las gramáticas utilizadas para generar los distintos árboles.
   * 
   * @return Las gramáticas utilizadas para generar los distintos árboles.
   */
  public Map<String, Grammar> getGrammars() {
    return grammars;
  }

  /**
   * Representación en forma de <code>String</code> de un individuo, es decir,
   * se obtiene representación de todos los árboles que contiene.
   * 
   * @return La representación del individuo.
   */
  @Override
  public String toString() {
    final StringBuilder individual = new StringBuilder();

    if (this.printabeIndividual != null && this.printabeIndividual.length() > 0) {
      individual.append(printabeIndividual);
    } else {
      int RPB = 0;
      int adf = 0;
      while (trees.get(RPB_PROPERTY + RPB) != null) {
        individual.append(RETURN_SYMBOL+ RPB_PROPERTY + RPB + COLON_SPACE_SYMBOL + trees.get(RPB_PROPERTY + RPB));
        RPB++;
      }
      while (trees.get(ADF_PROPERTY + adf) != null) {
        individual.append(RETURN_SYMBOL+ADF_PROPERTY + adf + COLON_SPACE_SYMBOL + trees.get(ADF_PROPERTY + adf));
        adf++;
      }
    }
    return individual.toString();
  }

  /**
   * Definición de un valor concreto para todas las variables que existan en
   * cualquier árbol.
   * 
   * @param variable
   *          Identificador de la variable a la que se le va a asignar el valor.
   * @param value
   *          Valor concreto que tendrá la variable.
   */
  public void setVariable(String variable, Object value) {
    this.updated = true;
    for (String idGrammar : grammars.keySet()) {
      final Grammar grammar = grammars.get(idGrammar);
      for (GrammarTerminalSymbol function : grammar.getGrammarTerminalSymbols()) {
        if (function.getSymbol().equals(variable)) {
          ((Terminal) function.getFunction()).setValue(value);
        }
      }
    }
  }

  /**
   * Función que evalúa y obtiene un resultado de la ejecución de todos los
   * árboles que tiene un individuo concreto. El resultado se deja almacenado en
   * la variable para tal efecto.
   * 
   * @param variables
   *          Almacén que contiene todas las variables definidas por un
   *          identificador y su valor concreto.
   * @param userprogram
   *          Definición del problema que ha tenido que implementar el usuario.
   */
  private void evaluateTree(Map<String, Object> variables, UserProgram userprogram) {
    // se actualizan los ADF correspondientes para que utilicen el arbol de
    // este individuo
    for (int i = 0; i < totalADF; i++) {
      for (int j = 0; j < totalRPB; j++) {
        for (GrammarTerminalSymbol adf : grammars.get(RPB_PROPERTY + j).getGrammarTerminalSymbols()) {
          if (adf.getSymbol().compareTo(ADF_PROPERTY + i) == 0) {
            ((ADF) adf.getFunction()).setADFTree(trees.get(ADF_PROPERTY + i));
          }
        }
      }
    }
    for (int i = 0; i < totalRPB; i++) {
      results.put(RPB_PROPERTY + i, trees.get(RPB_PROPERTY + i).evaluate(userprogram, variables));
    }
    this.updated = false;
  }

  /**
   * Devuelve el resultado de ejecutar el árbol solicitado como parámetro.
   * Únicamente tiene sentido solicitar los resultados de los árboles de tipo
   * RPB.
   * 
   * @param idTree
   *          Identificador del árbol del que se quiere obtener el resultado de
   *          su ejecución.
   * @param userProgram
   *          Referencia al programa modelado por el usuario.
   * @return Object del tipo que devuelve el function set que defina la
   *         gramática que se utilizá para definir ese árbol.
   */
  public Object evaluate(String idTree, UserProgram userProgram) {
    // TODO: comprobar que se está solicianto de un RPB, en caso contrario dar
    // un fallo.
    if (updated) {
      this.evaluateTree(variables, userProgram);
    }
    return results.get(idTree);
  }

  /**
   * Devuelve únicamente el valor de la ejecución del árbol identificado como
   * <code>RPB0</code>, ya que no se tiene sentido definir un individuo en el
   * que no exista ni siquiera este árbol.
   * 
   * @param userProgram
   *          Referencia al programa modelado por el usuario. *
   * @return Object del tipo que devuelve el function-set que defina la
   *         gramática que se utilizá para definir ese árbol.
   */
  public Object evaluate(UserProgram userProgram) {
    return this.evaluate("RPB0", userProgram);
  }

  /**
   * Especifica el valor del <i>rawFitness</i> de este individuo, según se haya
   * definido en el UserProgram de un problema concreto
   * 
   * @see progen.userprogram.UserProgram
   * 
   * @param fitness
   *          el valor del <i>rawFitness</i>.
   */
  public void setRawFitness(double fitness) {
    this.rawFitness = fitness;
  }

  /**
   * Devuelve el valor del <i>rawFitness</i> de este individuo.
   * 
   * @return fitness el valor del <i>rawFitness</i> de este individuo.
   */
  public double getRawFitness() {
    return rawFitness;
  }

  /**
   * Devuelve el valor del <i>adjustedFitness</i> según la definición:
   * <code>adjustedFitness=1/(1+rawFitness);</code>
   * 
   * @return el valor del <i>adjustedFitness</i>
   */
  public double getAdjustedFitness() {
    return 1 / (1 + rawFitness);
  }

  @Override
  public void calculate(UserProgram userProgram) {
    this.rawFitness = userProgram.fitness(this);
  }

  @Override
  public boolean isDone() {
    return !updated;
  }

  /**
   * Compara un individuo con otro proporcionado como parámetro.
   * 
   * @param other
   *          El individuo con el que comparar.
   * @return Devuelve la diferencia entre el rawFitness de los dos, de la forma
   *         <code>this-other</code>
   */
  @Override
  public int compareTo(Individual other) {
    return Double.compare(this.rawFitness, other.rawFitness);
  }

  @Override
  public boolean equals(Object other) {
    boolean equals = false;
    if (other instanceof Individual) {
      equals = this.equalsIndividual((Individual) other);
    } else {
      equals = false;
    }
    return equals;
  }

  @Override
  public int hashCode() {
    return toString().hashCode();
  }

  /**
   * Compara dos individuos para comprobar si son iguales o no. Se considerará
   * que dos individuos son iguales únicamente si todos sus árboles son iguales.
   * 
   * @param other
   *          Individuo con el que comparar
   * @return <code>true</code> si los dos individuos son iguales.
   */
  private boolean equalsIndividual(Individual other) {
    boolean isEquals = true;
    if (other == null) {
      isEquals = false;
    } else {
      for (int i = 0; i < totalRPB; i++) {
        isEquals = isEquals && trees.get(RPB_PROPERTY + i).toString().compareTo(other.getTrees().get(RPB_PROPERTY + i).toString()) == 0;
      }
      for (int i = 0; i < totalADF; i++) {
        isEquals = isEquals && trees.get(ADF_PROPERTY + i).toString().compareTo(other.getTrees().get(ADF_PROPERTY + i).toString()) == 0;
      }
    }
    return isEquals;
  }

  @Override
  public Object getCalculateResult() {
    return this;
  }

  @Override
  public Individual clone() {
    try {
      super.clone();
    } catch (CloneNotSupportedException e) {
      // ignore this
    }
    return new Individual(this);
  }

  /**
   * Devuelve el número total de árboles RPB que componen el individuo.
   * 
   * @return el número total de RPB.
   */
  public int getTotalRPB() {
    return totalRPB;
  }

  /**
   * Devuelve el número total de árboles ADF que componen el individuo.
   * 
   * @return el número total de ADF.
   */
  public int getTotalADF() {
    return totalADF;
  }

  /**
   * @param printable
   */
  public void setPrintableIndividual(String printable) {
    this.printabeIndividual = printable;
  }

}
