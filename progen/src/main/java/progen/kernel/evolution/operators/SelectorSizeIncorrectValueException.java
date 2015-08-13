package progen.kernel.evolution.operators;

import progen.kernel.error.Error;

/**
 * @author jirsis
 * @since 2.0
 * 
 */
public class SelectorSizeIncorrectValueException extends RuntimeException {
  private static final long serialVersionUID = 7989167828358778207L;

  private static final int ID_ERROR = 26;

  /**
   * Constructor de la excepción en la que se recibe por parámetro el tamaño del
   * selector esperado y el valor actual.
   * 
   * @param expected
   *          Valor esperado del selector.
   * @param provided
   *          Valor actual.
   */
  public SelectorSizeIncorrectValueException(int expected, int provided) {
    super(Error.get(ID_ERROR) + "(" + provided + "!=" + expected + ")");
  }
}
