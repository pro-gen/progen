package progen.context;

import progen.kernel.error.Error;

/**
 * Excepción que es lanzada cuando no se encuentra un fichero de propiedades
 * obligatorio, como puede ser el master-file o el fichero concreto del dominio
 * implementado por el usuario.
 * 
 * @author jirsis
 * 
 */
public class MissingContextFileException extends RuntimeException {
  private static final long serialVersionUID = 6593534316625884222L;
  private static final int ID_ERROR=18;

  /**
   * Constructor de la excepción. Recibe el fichero que se intentaba leer y que
   * no ha sido encontrado.
   * 
   * @param message
   *          El nombre del fichero que se intentó leer.
   */
  public MissingContextFileException(String message) {
    super(getMessage(message));
  }

  /**
   * Constructor genérico de la excepción.
   */
  public MissingContextFileException() {
    super(Error.get(ID_ERROR).trim());
  }
  
  public MissingContextFileException(String message, Exception originalException){
    super(getMessage(message), originalException);
  }
  
  private static String getMessage(String message) {
    return Error.get(ID_ERROR).trim() + "(" + message + ")";
  }

}
