package progen.kernel.population;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import progen.context.ProGenContext;
import progen.kernel.grammar.Grammar;

/**
 * Clase que representa a una población completa en programación genética.
 * 
 * @author jirsis
 * @since 1.0
 */
public class Population {

  private static final String PROGEN_TOTAL_RPB_PROPERTY = "progen.total.RPB";

  private static final String PROGEN_POPULATION_SIZE_PROPERTY = "progen.population.size";

  /**
   * Gramáticas que definirán los distintos árboles que representa a un
   * individuo.
   * 
   * @see Individual
   */
  private HashMap<String, Grammar> grammars;

  /** Indica el número de árboles RPB que tiene cada individuo de la poblción. */
  private int totalRPB;
  /** Indica el número de árboles ADF que tiene cada individuo de la poblción. */
  private int totalADF;

  /** Representación del conjunto de individuos que conforman la población. */
  private List<Individual> individuals;

  /**
   * Constructor genérico de la clase. Carga la población en función del tamaño
   * definido en el fichero de configuración, a través de la propiedad
   * <code>progen.population.size</code>
   * 
   */
  public Population() {
    final int populationSize = ProGenContext.getOptionalProperty(PROGEN_POPULATION_SIZE_PROPERTY, 100);
    ProGenContext.setProperty(PROGEN_POPULATION_SIZE_PROPERTY, populationSize + "");
    final Population pop = new Population(populationSize);
    this.grammars = pop.grammars;
    this.individuals = pop.individuals;
    this.totalADF = pop.totalADF;
    this.totalRPB = pop.totalRPB;
  }

  /**
   * Constructor de la clase que crea una población del tamaño especificado en
   * el parámetro.
   * 
   * @param populationSize
   *          Tamaño de la población a crear.
   */
  public Population(int populationSize) {
    grammars = new HashMap<String, Grammar>();
    totalRPB = 0;
    totalADF = 0;
    this.loadGrammars();

    individuals = new ArrayList<Individual>();
    initIndividuals(populationSize);
  }

  /**
   * Crea las estructuras de las gramáticas que se utilizarán para crear todos
   * los árboles de los individuos.
   */
  private void loadGrammars() {
    totalRPB = ProGenContext.getOptionalProperty(PROGEN_TOTAL_RPB_PROPERTY, 1);
    ProGenContext.setProperty(PROGEN_TOTAL_RPB_PROPERTY, totalRPB + "");
    totalADF = ProGenContext.getOptionalProperty("progen.total.ADF", 0);

    loadGrammar("RPB", totalRPB);
    loadGrammar("ADF", totalADF);

  }

  /**
   * Crea todas las gramáticas de un tipo determinado, RPB o ADF. Cargará en
   * memoria una única instancia de la gramática generada por cada function-set,
   * de tal forma, que si varios árboles comparten el mismo function-set,
   * tendrán la misma referencia a la gramática que lo genera.
   * 
   * @param type
   *          El tipo de gramática a cargar, [RPB | ADF].
   * @param total
   *          Número total de gramáticas que hay que cargar.
   * 
   */
  private void loadGrammar(String type, int total) {
    Grammar grammarLoaded;
    String functionSet;
    for (int i = 0; i < total; i++) {
      grammarLoaded = null;
      functionSet = ProGenContext.getMandatoryProperty("progen." + type + i + ".functionSet");
      for (Grammar grammar : grammars.values()) {
        if (grammar.getFunctionSetId().equals(functionSet)) {
          grammarLoaded = grammar;
        }
      }

      if (grammarLoaded == null) {
        grammarLoaded = Grammar.makeInstance(type + "" + i);
      }
      grammars.put(type + "" + i, grammarLoaded);
    }
  }

  /**
   * Inicialización del conjunto de individuos, indicando el tamaño de la
   * población.
   * 
   * @param populationSize
   *          El tamaño de la población.
   */
  private void initIndividuals(int populationSize) {
    for (int i = 0; i < populationSize; i++) {
      individuals.add(new Individual(grammars));
    }
  }

  /**
   * Representación de la población en forma de String. Se consigue una
   * representación de todos los individuos que conforman cada población.
   */
  public String toString() {
    final StringBuilder population = new StringBuilder();
    for (int i = 0; i < individuals.size(); i++) {
      population.append(individuals.get(i) + "\n");
    }
    return population.toString();
  }

  /**
   * Devuelve el conjunto de individuos que componene la población.
   * 
   * @return los individuos.
   */
  public List<Individual> getIndividuals() {
    return individuals;
  }

  /**
   * Devuelve el idindividuo que ocupa la posición indicada como parámetro.
   * 
   * @param id
   *          El identificador del individuo.
   * @return El individuo deseado.
   */
  public Individual getIndividual(int id) {
    return individuals.get(id);
  }

  /**
   * Método para establecer un nuevo conjunto de individuos que formará la
   * población.
   * 
   * @param individuals
   *          El nuevo conjunto de individuos que define la población.
   */
  public void setPopulation(List<Individual> individuals) {
    this.individuals = individuals;
  }

  /**
   * Devuelve el individuo que tiene el valor más bajo de <i>rawFitness</i>
   * 
   * @return El mejor individuo de toda la población.
   */
  public Individual getBestIndividual() {
    Individual best;
    Individual candidate;
    best = this.getIndividual(0);
    for (int i = 1; i < individuals.size(); i++) {
      candidate = getIndividual(i);
      if (candidate.getRawFitness() < best.getRawFitness()) {
        best = candidate;
      }
    }
    return best;
  }

  /**
   * Returns the size of the population
   * 
   * @return the size of the population
   */
  public int size() {
    return individuals.size();
  }

}
