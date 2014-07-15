package progen.kernel.grammar.validations;

import progen.kernel.grammar.Grammar;

/**
 * Clase que encadena y comprueba las distintas comprobaciones que se realizan
 * para comprobar que una gramática está bien formada y puede generar árboles
 * evaluables en la aplicación.
 * 
 * @author jirsis
 * @since 2.0
 * @see progen.kernel.grammar.validations
 */
public class GrammarCheck {
  /** La gramática a comprobar. **/
  private Grammar grammar;

  /**
   * Constructor genérico de la clase que recibe como parámetro la gramática a
   * comprobar.
   * 
   * @param grammar
   *          La gramática a comprobar.
   */
  public GrammarCheck(Grammar grammar) {
    this.grammar = grammar;
  }

  /**
   * Método que realiza todas las validaciones necesarias sobre la gramática. En
   * caso de que no sea correcta, se lanzará una excepción de tipo
   * GrammarNotValidException
   * 
   * @throws GrammarNotValidException
   *           Excepción lanzada si no cumple con las validaciones definidas.
   * @see GrammarNotValidException
   * 
   */
  public void validate(){
    new GrammarNonTerminalSymbolProduction().validate(grammar);
    new InaccesibleProductions().validate(grammar);
    new SuperfluousProductions().validate(grammar);
  }

}
