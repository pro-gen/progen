package progen.output.plugins;

import progen.kernel.error.Error;

/**
 * Excepción que será lanzada cuando se intente crear un Plugin que no existe en
 * la colección de los que están disponibles.
 * 
 * @author jirsis
 * @since 2.0
 * @see progen.output.plugins
 */
public class UnknownPluginException extends RuntimeException {

  private static final int ID_ERROR = 23;
  /** Para serialización */
  private static final long serialVersionUID = -7276399548518521418L;

  /**
   * Constructor de la excepción en la que se recibe como parámetro el nombre
   * del Plugin que se intentó instanciar.
   * 
   * @param msg
   *          Plugin que se intentó instanciar.
   */
  public UnknownPluginException(String msg) {
    super(Error.get(ID_ERROR).trim() + "(" + msg + ")");
  }

}
