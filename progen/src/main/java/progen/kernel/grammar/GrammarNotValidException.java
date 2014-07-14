package progen.kernel.grammar;

import progen.kernel.error.Error;

/**
 * @author jirsis
 * 
 */
public class GrammarNotValidException extends RuntimeException {

  private static final int UNKNOWN_ERROR_ID = 255;
  /** Para serialización */
  private static final long serialVersionUID = 12775764959942792L;

  /**
   * Constructor por defecto
   */
  public GrammarNotValidException() {
    super(Error.get(UNKNOWN_ERROR_ID));
  }

  /**
   * Constructor que recibe el identificador del error a mostrar.
   * 
   * @param idError
   */
  public GrammarNotValidException(int idError) {
    super(Error.get(idError));
  }
}
