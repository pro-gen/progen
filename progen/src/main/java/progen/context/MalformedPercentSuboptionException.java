package progen.context;

import progen.kernel.error.Error;

/**
 * Escepción que será lanzada cuando se recupere el valor de una opción que
 * define subopciones como porcentajes y la suma de todas las subopciones no
 * sume un 100%.
 * 
 * @author jirsis
 * 
 */
public class MalformedPercentSuboptionException extends RuntimeException {

  private static final int ID_ERROR = 7;

  private static final long serialVersionUID = -245792084266343630L;

  /**
   * Constructor de la excepción en la que se proporciona el nombre de la
   * propiedad que no cumple con el requisito de que todas las subopciones sumen
   * un 100% en el porcentaje.
   * 
   * @param message
   *          Nombre de la propiedad que no cumple la condición.
   */
  public MalformedPercentSuboptionException(String message) {
    super(Error.get(ID_ERROR).trim() + " (" + message + ")");
  }

}
