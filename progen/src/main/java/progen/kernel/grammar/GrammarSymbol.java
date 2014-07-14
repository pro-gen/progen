package progen.kernel.grammar;

/**
 * Definición de la interfaz de uso de un GrammarSymbol.
 * 
 * @author jirsis
 * @since 2.0
 */
public interface GrammarSymbol extends Comparable<GrammarSymbol> {
  /**
   * Devuelve el símbolo con el que se identifica un GrammarSymbol concreto.
   * 
   * @return el símbolo con el que se identifica.
   */
  String getSymbol();

  /**
   * Devuelve el valor concreto que tiene un símbolo.
   * 
   * @return El valor del símbolo.
   */
  Object getValue();

}
