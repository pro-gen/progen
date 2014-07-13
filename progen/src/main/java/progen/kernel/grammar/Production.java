package progen.kernel.grammar;

import java.io.Serializable;

import progen.kernel.functions.ERC;

/**
 * Clase que representa las producciones de la gramática que generará todos los
 * programas del dominio implementado por el usuario.
 * 
 * Todas las producciones serán de la forma<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; A ::= bC<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; A ::= b<br>
 * Donde:
 * <ul>
 * <li>A, C pertenecen al conjunto de símbolos no terminales de la gramítica.</li>
 * <li>b pertenece al conjunto de símbolos terminales de la gramática.</li>
 * </ul>
 * 
 * 
 * En este caso, como los terminales de la gramática, son funciones los símbolos
 * no terminales que están a la derecha, son los argumentos que necesita dicha
 * función.
 * 
 * @author jirsis
 * @since v2.0
 */
public class Production implements Serializable {
  private static final long serialVersionUID = 8087033089940604792L;
  
  /**
   * Representa la parte izquierda de la producción (A, en la descripción
   * general)
   */
  private GrammarNonTerminalSymbol left;
  /**
   * Representa el símbolo terminal (b, en la descripción general)
   */
  private GrammarTerminalSymbol function;
  /**
   * Representa a los argumentos de la función (C, en la descripción general)
   */
  private GrammarNonTerminalSymbol[] args;

  /**
   * Constructor genérico que recibe los elementos necesarios por parámetro,
   * para formar producciones de la forma:
   * 
   * <code> left ::= function args</code>
   * 
   * @param left
   *          parte izquierda de la producción.
   * @param function
   *          función de la producción.
   * @param args
   *          no terminales de la parte derecha de la producción. Con este
   *          conjunto de no terminales, se contruyen nuevos no terminales para
   *          almecenar en esta instancia.
   */
  public Production(GrammarNonTerminalSymbol left, GrammarTerminalSymbol function, GrammarNonTerminalSymbol args[]) {
    this.left = left;
    this.function = function;
    this.args = new GrammarNonTerminalSymbol[args.length];
    for (int i = 0; i < args.length; i++) {
      this.args[i] = args[i];
    }
  }

  /**
   * Representación en forma de <code>String</code> de la producción. Esta
   * representación es de la forma <code> A -> b C </code>
   */
  public String toString() {
    StringBuffer stb = new StringBuffer();
    stb.append(left.toString());
    stb.append(" -> ");
    stb.append(function.toString());
    stb.append(" ");
    for (int i = 0; i < args.length; i++) {
      stb.append(args[i].toString() + " ");
    }

    return stb.toString().trim();
  }

  /**
   * Devuelve la parte izquierda de esta Production
   * 
   * @return La parte izquierda de esta <code> Production </code>
   */
  public GrammarNonTerminalSymbol getLeft() {
    return left;
  }

  /**
   * Devuelve los argumentos de la función de esta Production
   * 
   * @return Los argumentos de la función de esta <code>Production</code>
   */
  @edu.umd.cs.findbugs.annotations.SuppressWarnings(value="EI_EXPOSE_REP", justification="Its is a getter method")
  public GrammarNonTerminalSymbol[] getArgs() {
    return args;
  }

  /**
   * Devuelve la función de esta Production
   * 
   * @return La función de esta <code>Production</code>
   */
  public GrammarTerminalSymbol getFunction() {
    GrammarTerminalSymbol function;
    if (this.function.getFunction() instanceof ERC) {
      ERC erc = (ERC) this.function.getFunction();
      function = new GrammarTerminalSymbol(erc.clone());
    } else {
      function = this.function;
    }
    return function;
  }

  /**
   * Comprueba si el símbolo no terminal pasado por parámetro se puede generar
   * directamente ejecutando esta Production.
   * 
   * @param symbol
   *          a comprobar si es generado en esta producción.
   * @return <code>true</code> si se genera, devuelve <code>false</code> si no
   *         lo es.
   */
  public boolean isSymbolGenerated(GrammarNonTerminalSymbol symbol) {
    boolean generate = false;
    int i = 0;

    while (i < args.length && !generate) {
      if (args[i].compareTo(symbol) == 0) {
        generate = true;
      }
      i++;
    }

    return generate;
  }

  /**
   * Comprueba si el símbolo terminal pasado por parámetro se puede generar
   * directamente ejecutando esta Production.
   * 
   * @param symbol
   *          a comprobar si es generado en esta producción.
   * @return <code>true</code> si se genera, devuelve <code>false</code> si no
   *         lo es.
   */
  public boolean isSymbolGenerated(GrammarTerminalSymbol symbol) {
    return function.getFunction().getSymbol().compareTo(symbol.getFunction().getSymbol()) == 0;
  }

  /**
   * Comprueba si el Object pasado por parámetro es igual a esta Production.
   * 
   * @param other
   *          Object para comparar.
   * @return <code>true</code> si son iguales, <code>false</code> en caso
   *         contrario.
   */
  public boolean equals(Object other) {
    boolean equals = false;
    if (other instanceof Production) {
      equals = equals((Production) other);
    }
    return equals;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  public int hashCode() {
    return toString().hashCode();
  }

  /**
   * Comprueba si la Production pasada por parámetro es igual a la instancia
   * <code>this</code>
   * 
   * @param other
   *          Production para comparar
   * @return <code>true</code> si son iguales, <code>false</code> en caso
   *         contrario.
   */
  public boolean equals(Production other) {
    boolean equal;
    equal = this.left.equals(other.left);
    equal &= this.function.equals(other.function);

    equal &= this.args.length == other.args.length;

    if (equal) {
      for (int i = 0; equal && i < this.args.length; i++) {
        equal &= this.args[i].compareTo(other.args[i]) == 0;
      }
    }

    return equal;
  }

  /**
   * Método que crea una nueva <code>Production</code> a partir del
   * <code>GrammarTerminalSymbol</code> pasado por parámetro. Únicamente se
   * creará esta nueva producción si el terminal coincide con la
   * <code>Production</code> actual, en caso contrario, se devolverá una
   * referencia a esta misma.
   * 
   * @param newSymbol
   *          el símbolo nuevo con el que se creará la nueva
   *          <code>Production</code>.
   * @return la <code>Production</code> actualizada.
   */
  public Production updateFunction(GrammarTerminalSymbol newSymbol) {
    Production production = this;
    if (function.compareTo(newSymbol) == 0) {
      production = new Production(left, newSymbol, args);
    }

    return production;
  }
}