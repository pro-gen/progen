package progen.kernel.grammar;

import java.io.Serializable;

/**
 * Implementación de un símbolo no terminal de una gramática. Proporciona los
 * métodos necesarios para especificar y definir tanto el símbolo que representa
 * al no terminal como el valor concreto.
 * 
 * @author jirsis
 * @since 2.0
 */
public class GrammarNonTerminalSymbol implements GrammarSymbol, Serializable {
  private static final long serialVersionUID = 6074031156225323119L;
  /** Valor del no terminal. */
  private String value;
  /** Simbolo que representa al no terminal. */
  private String symbol;

  /**
   * Constructor genérico de la clase que recibe el símbolo y el valor que
   * definen al no terminal.
   * 
   * @param symbol
   *          Símbolo para su representación.
   * @param value
   *          Valor del no terminal
   */
  public GrammarNonTerminalSymbol(String symbol, String value) {
    this.symbol = symbol;
    this.value = value;
  }

  /**
   * Devuelve la representación en forma de String del no terminal.
   * 
   * @return la representación del símbolo no terminal.
   */
  public String toString() {
    return getSymbol();
  }
  
  public int hashCode() {
    return symbol.hashCode();
  }

  /**
   * Comprueba que el parámetro proporcionado es de un símbolo igual al actual.
   * 
   * @param other
   *          Símbolo con el que comparar.
   * @return <code>true</code> los dos símbolos se representan de la misma
   *         forma.
   */
  @Override
  public boolean equals(Object object){
    boolean isEquals = false;
    if (object instanceof GrammarNonTerminalSymbol){
      isEquals = this.compareTo((GrammarNonTerminalSymbol) object) == 0;
    }
    return isEquals;
  }

  @Override
  public int compareTo(GrammarSymbol other) {
    return symbol.compareTo(other.getSymbol());
  }
  

  @Override
  public String getSymbol() {
    return symbol;
  }

  public String getValue() {
    return value;
  }
  
}
