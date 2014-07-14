package progen.context;

import progen.kernel.error.Error;

/**
 * Excepción que se lanza cuando se ejecuta cualquier método de ProGenProperties
 * y no se ha inicializado antes.
 * 
 * @author jirsis
 * 
 * @see progen.context.ProGenContext#makeInstance(String)
 */
public class UninitializedContextException extends RuntimeException {
  private static final int ID_ERROR = 19;
  private static final long serialVersionUID = -1926903181381689034L;

  /**
   * Constructor genérico de la excepción.
   */
  public UninitializedContextException() {
    super(Error.get(ID_ERROR).trim());
  }

}
