/**
 * 
 */
package progen.context;

import progen.kernel.error.Error;

/**
 * Excepción que se lanza cuando se intenta recuperar una propiedad que es de
 * carácter obligatorio y no se encuentra después de inicializar las
 * propiedades.
 * 
 * @author jirsis
 * 
 */
public class UndefinedMandatoryPropertyException extends RuntimeException {

  private static final int ID_ERROR = 5;
  private static final long serialVersionUID = 1606940045200720802L;

  /**
   * Consturctor de la excepción. Recibe como parámetro el nombre de la
   * propiedad que es obligatorio que esté definida en alguno de los ficheros de
   * propiedades.
   * 
   * @param message
   *          Nombre de la propiedad obligatoria.
   */
  public UndefinedMandatoryPropertyException(String message) {
    super(Error.get(ID_ERROR).trim() + " [" + message + "]");
  }
}
