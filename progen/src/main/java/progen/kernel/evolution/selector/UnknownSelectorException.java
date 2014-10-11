/**
 * 
 */
package progen.kernel.evolution.selector;

import progen.kernel.error.Error;

/**
 * Excepción que se lanza cuando se intenta instanciar un selector que no
 * existe.
 * 
 * @author jirsis
 * @since 2.0
 */
public class UnknownSelectorException extends RuntimeException {

  private static final int ID_ERROR = 24;
  private static final long serialVersionUID = -2978138289477720126L;

  /**
   * Constructor de la excepción en la que se recibe el nombre del selector que
   * se intentó instanciar.
   * 
   * @param name
   *          Nombre del selector que no existe.
   */
  public UnknownSelectorException(String name) {
    super(getMessage(name));
  }
  public UnknownSelectorException(String name, Exception originalException) {
    super(getMessage(name), originalException);
  }
  private static String getMessage(String name) {
    return Error.get(ID_ERROR).trim() + " (" + name + ")";
  }

}
