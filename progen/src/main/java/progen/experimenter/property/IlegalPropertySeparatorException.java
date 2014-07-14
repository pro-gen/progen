package progen.experimenter.property;

import progen.kernel.error.Error;

/**
 * 
 * Excepción que será lanzada cuando se esté procesando una propiedad en el
 * experimenter que no tenga los campos separados por un separador válido.
 * 
 * @author jirsis
 * @since 2.0
 */
public class IlegalPropertySeparatorException extends RuntimeException {

  private static final long serialVersionUID = -4365783823140585119L;
  private static final int ID_ERROR = 28;

  /**
   * Constructor que recibe el nombre de la propiedad mal definida.
   * 
   * @param label
   *          La propiedad mal definida.
   */
  public IlegalPropertySeparatorException(String label) {
    super(Error.get(ID_ERROR) + " (" + label + ")");
  }

}
