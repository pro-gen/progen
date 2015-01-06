package progen.kernel.grammar;

import progen.kernel.error.Error;
import progen.kernel.grammar.validations.GrammarNotValidExceptionEnum;

/**
 * @author jirsis
 * 
 */
public class GrammarNotValidException extends RuntimeException {

  /** Para serialización */
  private static final long serialVersionUID = 12775764959942792L;

  public GrammarNotValidException(GrammarNotValidExceptionEnum error){
    super(Error.get(error.error()));
  }
  
}
