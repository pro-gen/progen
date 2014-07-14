package progen.kernel.evolution;

import progen.kernel.error.Error;

/**
 * Esta excepción se lanzará cuando se intentente instanciar un operador
 * genético y ocurra algún tipo de error no determinado.
 * 
 * @author jirsis
 * @since 2.0
 */
public class GenneticOperatorException extends RuntimeException {
  private static final int ID_ERROR = 11;
  /** Para serialización */
  private static final long serialVersionUID = 4810973346982942355L;

  /**
   * Constructor genérico de la excepción. Recibe una cadena que es el mensaje
   * de la excepción original.
   * 
   * @param message
   *          Original que originó la excepción.
   */
  public GenneticOperatorException(String message) {
    super(Error.get(ID_ERROR).trim() + " (" + message + ")");
  }
}
