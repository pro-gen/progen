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

  @Override
  public String toString() {
    return function.toString();
  }

  public int compareTo(GrammarTerminalSymbol symbol) {
    return function.compareTo(symbol.getFunction());
  }
  
  public int compareTo(GrammarSymbol other) {
    return compareTo((GrammarTerminalSymbol) other);
  }

  @Override
  public boolean equals(Object other) {
    boolean equals = false;
    if (other instanceof GrammarTerminalSymbol) {
      equals = this.compareTo((GrammarTerminalSymbol)other) == 0;
    }
    return equals;
  }

  public int hashCode() {
    return function.hashCode();
  }


  public String getSymbol() {
    return function.getSymbol();
  }

  public Object getValue() {
    return null;
  }

}
