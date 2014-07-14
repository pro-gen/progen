package progen.kernel.grammar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import progen.ProGenException;
import progen.context.ProGenContext;
import progen.kernel.functions.ARG;
import progen.kernel.functions.Function;
import progen.kernel.grammar.validations.GrammarCheck;
import progen.kernel.hypergp.HGPGrammar;

/**
 * Una gramática se define como una cuádrupla compuesta por:
 * <ul>
 * <li>
 * Axioma: Símbolo no terminal del que se empieza a derivar todo el árbol de
 * generación.</li>
 * <li>
 * No terminales: Conjunto de símbolos auxiliares que no pueden aparecer en la
 * palabra final que se defina a través de la gramática. Son símbolos auxiliares
 * que ayudan a evolucionar cada palabra intermedia hasta una final.</li>
 * <li>
 * Terminales: Conjunto de símbolos que aparecen en la palabra final.</li>
 * <li>
 * Producciones: Conjunto de reglas que se utilizan para generar una palabra
 * válida.</li>
 * </ul>
 * 
 * La gramáticas válidas para ProGen son gramáticas de tipo 2 (independientes de
 * contexto) en la que todas las producciones que se definan, tienen que ser
 * recursivas lineales a izquierdas.
 * 
 * @author jirsis
 * @since 2.0
 */
public class Grammar implements Serializable {
  private static final String PROGEN_FUNCTION_SET_PROPERTY = "progen.functionSet";

  private static final String PROGEN_PROPERTY = "progen.";

  private static final long serialVersionUID = 2709479285994382736L;

  /** Identificador del <i>function-set</i> que define la gramática. */
  private String functionSetId;

  /** Conjunto de símbolos terminales. */
  private List<GrammarTerminalSymbol> grammarTerminalSymbols;
  /** Conjunto de símbolos no terminales. */
  private List<GrammarNonTerminalSymbol> grammarNonTerminalSymbols;
  /** Axioma. */
  private GrammarNonTerminalSymbol axiom;
  /**
   * Conjunto de producciones que generarán una palabra válida por el lenguaje
   * definido.
   */
  private List<Production> productions;

  /**
   * Constructor de la clase en la que se espera un identificador de árbol, del
   * estilo "RPB" o "ADF", de tal forma que se inicialicen los valores y se
   * realicen las comprobaciones pertinentes para asegurarse de que la gramática
   * está bien formada y no tenga problemas que impidan ejecutar más adelante
   * los árboles generados.
   * 
   * @param idTree
   *          Identificador del árbol que utilizará esta gramática. Deberá
   *          recibir algo del estilo "RPBx" o "ADFx", donde x es un entero
   *          &gt;= 0.
   */
  protected Grammar(String idTree) {
    String returnValue;

    functionSetId = ProGenContext.getMandatoryProperty(PROGEN_PROPERTY + idTree + ".functionSet");
    returnValue = ProGenContext.getMandatoryProperty(PROGEN_FUNCTION_SET_PROPERTY + functionSetId + ".return");

    axiom = new GrammarNonTerminalSymbol("Ax", returnValue);
    grammarTerminalSymbols = new ArrayList<GrammarTerminalSymbol>();
    grammarNonTerminalSymbols = new ArrayList<GrammarNonTerminalSymbol>();
    grammarNonTerminalSymbols.add(axiom);
    productions = new ArrayList<Production>();

    if (!loadGrammar(Integer.parseInt(functionSetId))) {
      throw new DuplicatedFunctionException(idTree);
    }

    if (idTree.startsWith("ADF")) {
      generateADFProductions(idTree);
    }

    try {
      validate();
    } catch (GrammarNotValidException e) {
      throw new ProGenException(e.getMessage());
    }
  }

  /**
   * Devuelve el function-set que definió esta gramática.
   * 
   * @return El function-set que definió esta gramática.
   */
  public String getFunctionSetId() {
    return functionSetId;
  }

  /**
   * Devuelve el símbolo no terminal por el que se empieza a derivar toda la
   * palabra.
   * 
   * @return El axioma de la gramática.
   */
  public GrammarNonTerminalSymbol getAxiom() {
    return axiom;
  }

  /**
   * Devuelve el conjunto de producciones que define esta gramática.
   * 
   * @return El conjunto de producciones.
   * @see progen.kernel.grammar.Production
   */
  public List<Production> getProductions() {
    return productions;
  }

  /**
   * Devuelve el conjunto de producciones en las que se utiliza el símbolo
   * proporcionado como parámetro para poder ejecutar esa producción.
   * 
   * @param symbol
   *          símbolo que aparece en la parte izquierda de la producción.
   * @return Conjunto de producciones que se pueden derivar a partir del
   *         símbolo.
   */
  public List<Production> getProductions(GrammarSymbol symbol) {
    if (symbol instanceof GrammarNonTerminalSymbol) {
      return getProductions((GrammarNonTerminalSymbol) symbol);
    } else {
      throw new ClassCastException(symbol.toString());
    }
  }

  /**
   * Devuelve el conjunto de producciones en las que se utiliza el símbolo no
   * terminal proporcionado como parámetro para poder ejecutar esa producción.
   * 
   * @param left
   *          símbolo que aparece en la parte izquierda de la producción.
   * @return Conjunto de producciones que se pueden derivar a partir del
   *         símbolo.
   */
  public List<Production> getProductions(GrammarNonTerminalSymbol left) {
    final List<Production> prods = new ArrayList<Production>();
    for (Production p : productions) {
      if (p.getLeft().compareTo(left) == 0) {
        prods.add(p);
      }
    }
    return prods;
  }

  public List<Production> getProductionsCompatibleWithFunction(GrammarNonTerminalSymbol nonTerminal, GrammarTerminalSymbol terminal) {
    final List<Production> prods = new ArrayList<Production>();
    for (Production p : productions) {
      if (p.getLeft().compareTo(nonTerminal) == 0) {
        if (terminal.getFunction().isCompatibleWith(p.getFunction().getFunction())) {
          prods.add(p);
        }
      }
    }
    return prods;
  }

  /**
   * Devuelve el conjunto de producciones en las que aparece en la parte
   * izquierda el símbolo deseado, de tal forma que el orden de las producciones
   * del conjunto es aleatorio.
   * 
   * @param left
   *          Símbolo que genera la producción.
   * @return El conjunto de producciones que genera dicho símbolo.
   */
  public List<Production> getRandomProductions(GrammarNonTerminalSymbol left) {
    final List<Production> prods = this.getProductions(left);
    final List<Production> random = new ArrayList<Production>();
    while (prods.size() != 0) {
      random.add(prods.remove((int) (Math.random() * prods.size())));
    }
    return random;
  }

  /**
   * Devuelve el conjunto de símbolos terminales de la gramática.
   * 
   * @return El conjunto de símbolos terminales de la gramática.
   */
  public List<GrammarTerminalSymbol> getGrammarTerminalSymbols() {
    return grammarTerminalSymbols;
  }

  /**
   * Devuelve el conjunto de símbolos no terminales de la gramática.
   * 
   * @return el conunto de símbolos no terminales de la gramática.
   */
  public List<GrammarNonTerminalSymbol> getGrammarNonTerminalSymbols() {
    return grammarNonTerminalSymbols;
  }

  /**
   * Comprueba que la gramática definida cumple con todas las restricciones
   * definidas o lanzará una GrammarNotValidException
   * 
   * @throws GrammarNotValidException
   *           Lanzada cuando la gramática no pase las validaciones definidas.
   * 
   * @see progen.kernel.grammar.validations
   */
  public void validate() {
    new GrammarCheck(this).validate();
  }

  /**
   * Define todas y cada una de las producciones que se especifican en el
   * identificador de function-set pasado por parámetro, indicando si se ha
   * podido llevar la operación a cabo o no.
   * 
   * @param functionSet
   *          identificador de <i>function-set</i> que definirá el conjunto de
   *          producciones que es necesario crear.
   * @return <code>true</code> si se crearon todas las producciones definidas en
   *         el <i>function-set</i> o <code>false</code> en caso contrario.
   */
  private boolean loadGrammar(int functionSet) {
    final String [] functions = ProGenContext.getMandatoryProperty(PROGEN_FUNCTION_SET_PROPERTY + functionSet).trim().split(",[ ]*");
    Function function;
    Production production;
    GrammarNonTerminalSymbol [] args;
    GrammarNonTerminalSymbol left;
    GrammarTerminalSymbol right;
    boolean loadedOK = true;

    for (int i = 0; loadedOK && i < functions.length; i++) {

      function = Function.load(functions[i]);
      left = getGrammarNonTerminalSymbol(function.getReturnType());
      args = getGrammarNonTerminalSymbol(function.getArgsType());
      right = new GrammarTerminalSymbol(function);
      production = new Production(left, right, args);
      if (!productions.contains(production)) {
        productions.add(production);
        grammarTerminalSymbols.add(right);
      } else {
        loadedOK = false;
      }

    }
    return loadedOK;
  }

  /**
   * Representación en forma de String de la gramática. En este caso, únicamente
   * se representarán las producciones, ya que el resto de elementos que definen
   * la gramática, se pueden obtener de forma inmediata viendo esta información
   * y la nomenclatura seguida.
   */
  public String toString() {
    StringBuffer grammar = new StringBuffer();
    for (Production production : productions) {
      grammar.append(production.toString() + "\n");
    }
    return grammar.toString();
  }

  /**
   * Devuelve el símbolo no terminal que genera un valor de retorno pasado por
   * parámetro. En el caso de que ya exista un no-terminal que genere ese mismo
   * valor de retorno, no se crea uno nuevo y se devuelve el que se haya
   * encontrado. En caso contrario, se crea un nuevo símbolo no terminal y se
   * añade al conjunto de no terminales de la gramática.
   * 
   * @param returnValue
   *          Valor que tendrá que retornar este símbolo no terminal.
   * @return El símbolo no terminal que cumple con la condición de generar ese
   *         valor de retorno.
   */
  private GrammarNonTerminalSymbol getGrammarNonTerminalSymbol(Object returnValue) {
    boolean find = false;
    GrammarNonTerminalSymbol symbol = null;
    for (GrammarNonTerminalSymbol symbolTemp : grammarNonTerminalSymbols) {
      if (!find) {
        if (symbolTemp.getValue().compareTo((String) returnValue) == 0) {
          find = true;
          symbol = symbolTemp;
        }
      }
    }

    if (!find) {
      symbol = new GrammarNonTerminalSymbol("R" + (grammarNonTerminalSymbols.size() - 1), (String) returnValue);
      grammarNonTerminalSymbols.add(symbol);
    }

    return symbol;
  }

  /**
   * Devuelve el conjunto de símbolos no terminales que generar los distintos
   * valores de retorno proporcionados como parámetro.
   * 
   * @param returnValue
   *          conjunto de valores de retorno de los que se quiere obtener los
   *          símbolos no terminales de los que se pueden generar.
   * @return Conjunto de símbolos no terminales que generan los distintos
   *         valores de retorno
   */
  private GrammarNonTerminalSymbol[] getGrammarNonTerminalSymbol(Object returnValue[]) {
    GrammarNonTerminalSymbol [] symbols;
    symbols = new GrammarNonTerminalSymbol[returnValue.length];
    for (int i = 0; i < returnValue.length; i++) {
      symbols[i] = getGrammarNonTerminalSymbol(returnValue[i]);
    }
    return symbols;
  }

  /**
   * Se encarga de generar las reglas para utilizar los posibles argumentos de
   * este ADF en la gramatica que se ha generado.
   * 
   * @param idADF
   *          identificador del ADF.
   */
  private void generateADFProductions(String idADF) {
    GrammarNonTerminalSymbol left;
    GrammarTerminalSymbol right;
    Function function;
    Production production;
    final String[] args = ProGenContext.getMandatoryProperty(PROGEN_PROPERTY + idADF + ".interface").split("\\$\\$");

    for (int i = 1; i < args.length; i++) {
      function = new ARG(idADF, args[i], i - 1);
      left = getGrammarNonTerminalSymbol(function.getReturnType());
      right = new GrammarTerminalSymbol(function);
      production = new Production(left, right, getGrammarNonTerminalSymbol(function.getArgsType()));
      if (!productions.contains(production)) {
        productions.add(production);
        grammarTerminalSymbols.add(right);
      }
    }
  }

  /**
   * Método estático que creará las instancias de la gramática que vendrá
   * identifada por el parámetro
   * 
   * @param idTree
   *          Identificador del árbol del que se quiere generar la gramática.
   * @return gramática que generará el árbolo solicitado.
   */
  public static Grammar makeInstance(String idTree) {
    Grammar grammar = null;
    if ("on".equals(ProGenContext.getOptionalProperty("progen.hgp", "off"))) {
      grammar = new HGPGrammar(idTree);
    } else {
      grammar = new Grammar(idTree);
    }
    return grammar;
  }

}
