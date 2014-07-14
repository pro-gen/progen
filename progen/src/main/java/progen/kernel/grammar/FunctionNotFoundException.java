package progen.kernel.grammar;

import progen.kernel.error.Error;

/**
 * Excepción que será lanzada cuando se intente instanciar una función
 * determinada y no se encuentre la clase que lo implemente.
 * 
 * @author jirsis
 * @since 2.0
 */
public class FunctionNotFoundException extends RuntimeException {

  private static final int ID_ERROR = 32;
  /** Para serialización */
  private static final long serialVersionUID = -7230931813580048615L;

  /**
   * Constructor por defecto de la excepción, en la que se define un mensaje de
   * error concreto.
   * 
   * @param message
   *          El mensaje de error concreto de la excepción.
   */
  public FunctionNotFoundException(String message) {
    super(Error.get(ID_ERROR) + " [" + message + "]");
  }

}
