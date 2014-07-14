/**
 * 
 */
package progen.kernel.evolution;

import progen.kernel.error.Error;

/**
 * Excepción que se lanzará en el momento en el que se intente crear un operador
 * genético que no exista.
 * 
 * @author jirsis
 * @since 2.0
 */
public class UndefinedGenneticOperatorException extends RuntimeException {

  private static final int ID_ERROR = 10;
  private static final long serialVersionUID = 7881104979746238388L;

  /**
   * Contructor genérico de la excepción que recibe por parámetro el nombre del
   * operador que no se encontró.
   * 
   * @param message
   *          Nombre del operador que se intentó instanciar.
   */
  public UndefinedGenneticOperatorException(String message) {
    super(Error.get(ID_ERROR).trim() + " (" + message + ")");
  }
}
