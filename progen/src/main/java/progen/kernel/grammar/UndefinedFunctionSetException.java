package progen.kernel.grammar;

/**
 * Excepción que será lanzada cuando se haga referencia a un function set que no
 * haya sido definido anteriormente.
 * 
 * @author jirsis
 * @since 2.0
 */
public class UndefinedFunctionSetException extends RuntimeException {

  /** Para serialización */
  private static final long serialVersionUID = 4281333347079824203L;

  /**
   * Constructor por defecto de la excepción, en el que se define un mensaje de
   * error.
   * 
   * @param message
   *          El mensaje con el que se creará la excepción.
   */
  public UndefinedFunctionSetException(String message) {
    super(message);
  }
}
