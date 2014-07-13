package progen.kernel.grammar;

import java.io.Serializable;

import progen.kernel.functions.Function;

/**
 * Implements the Terminal Symbols
 * 
 * @author jirsis
 * 
 */
public class GrammarTerminalSymbol implements GrammarSymbol, Serializable {
  private static final long serialVersionUID = 3985246114734927067L;

  private Function function;

  public GrammarTerminalSymbol(Function function) {
    this.function = function;
  }

  public Function getFunction() {
    return function;
  }

  public String toString() {
    return function.toString();
  }

  public int compareTo(GrammarTerminalSymbol symbol) {
    return function.compareTo(symbol.getFunction());
  }

  public boolean equals(GrammarTerminalSymbol symbol) {
    return this.compareTo(symbol) == 0;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  public boolean equals(Object other) {
    boolean equals = false;
    if (other instanceof GrammarTerminalSymbol) {
      equals = equals((GrammarTerminalSymbol) other);
    }
    return equals;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  public int hashCode() {
    return function.hashCode();
  }

  /*
   * (non-Javadoc)
   * 
   * @see progen.kernel.grammar.GrammarSymbol#compareTo(progen.kernel.grammar.
   * GrammarSymbol)
   */
  public int compareTo(GrammarSymbol other) {
    return compareTo((GrammarTerminalSymbol) other);
  }

  public String getSymbol() {
    return function.getSymbol();
  }

  public Object getValue() {
    return null;
  }

}
