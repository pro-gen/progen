package progen.context;

import progen.kernel.error.Error;

/**
 * Excepción que es lanzada cuando una opción es definida de forma errónea.
 * 
 * @author jirsis
 * 
 */
public class MalformedParameterException extends RuntimeException {

  private static final int ID_ERROR = 8;
  private static final long serialVersionUID = -1647531135847340217L;

  /**
   * Constructor de la excepción. Recibe el nombre de la propiedad que está mal
   * construída.
   * 
   * @param message
   */
  public MalformedParameterException(String message) {
    super(getMessage(message));
  }
  
  public MalformedParameterException(String message, Exception originalException) {
    super(getMessage(message), originalException);
  }

  private static String getMessage(String message) {
    return Error.get(ID_ERROR).trim() + " (" + message + ")";
  }

}
