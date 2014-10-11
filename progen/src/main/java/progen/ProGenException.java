package progen;

/**
 * 
 * Excepción que se lanza de forma genérica y que mostrará únicamente el mensaje
 * que causó el problema.
 * 
 * @author jirsis
 * 
 */
public class ProGenException extends RuntimeException {

  /** Para serialización */
  private static final long serialVersionUID = 7316073380476351983L;

  /**
   * Constructor de la clase que recibe el mensaje de la excepción original.
   * 
   * @param msg
   *          El mensaje original de la excepción.
   */
  public ProGenException(String msg) {
    super(msg);
  }
  
  public ProGenException(String msg, Exception originalException) {
    super(msg, originalException);
  }

}
