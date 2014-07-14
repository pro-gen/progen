package progen.kernel.population;

import progen.kernel.error.Error;

/**
 * Excepcion que sera lanzada una vez se intente crear una instancia del
 * UserProgram definido en el fichero de configuracion del experimento y no se
 * encuentre en la ruta especificada.
 * 
 * @author jirsis
 * 
 */
public class UnknownUserProgramException extends RuntimeException {

  private static final int ID_ERROR = 16;
  /** Para serialización */
  private static final long serialVersionUID = -5689785997668176059L;

  /**
   * Constructor de la excepcion en la que se especifica la clase que se intento
   * instanciar y fue imposible, porque no existe.
   * 
   * @param msg
   *          La clase a instanciar y que no existe.
   */
  public UnknownUserProgramException(String msg) {
    super(Error.get(ID_ERROR) + " (" + msg + ")");
  }

}
