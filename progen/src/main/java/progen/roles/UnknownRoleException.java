package progen.roles;

import progen.kernel.error.Error;

/**
 * Excepción que se lanza si se intenta crear un ExecutionRole que no es ninguno
 * de los permitidos.
 * 
 * @author jirsis
 * @see ExecutionRole
 * @since 2.0
 */
public class UnknownRoleException extends RuntimeException {

  private static final int ID_ERROR = 21;
  /** Para serialización. */
  private static final long serialVersionUID = -7868949342835598751L;

  /**
   * Constructor de la clase que recibe el nombre del tipo de rol que se intentó
   * crear.
   * 
   * @param msg
   *          El tipo de rol a crear.
   */
  public UnknownRoleException(String msg) {
    super(getMessage(msg));
  }
  
  public UnknownRoleException(String msg, Exception originalException) {
    super(getMessage(msg), originalException);
  }

  private static String getMessage(String msg) {
    return Error.get(ID_ERROR) + "(" + msg + ")";
  }

}
