package progen.kernel.grammar;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import progen.kernel.grammar.validations.GrammarNotValidExceptionEnum;

public class GrammarNotValidExceptionTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();
  
  @Test
  public void testNonTerminalSymbols() {
    exception.expect(GrammarNotValidException.class);
    exception.expectMessage("Grammar not valid. The grammar doesn't generate productions like Ax :: = xxx");
    throw new GrammarNotValidException(GrammarNotValidExceptionEnum.NON_TERMINAL_SYMBOLS_IN_PRODUCTIONS_ERROR);
  }
  
  @Test
  public void testInaccesibleProductions() {
    exception.expect(GrammarNotValidException.class);
    exception.expectMessage("Grammar not valid. The grammar has inaccesible productions.");
    throw new GrammarNotValidException(GrammarNotValidExceptionEnum.INACCESIBLE_PRODUCTIONS_ERROR);
  }
  
  @Test
  public void testSuperfluousProduction() {
    exception.expect(GrammarNotValidException.class);
    exception.expectMessage("Grammar not valid. The grammar has superfluous productions.");
    throw new GrammarNotValidException(GrammarNotValidExceptionEnum.SUPERFLUOUS_PRODUCTION_ERROR);
  }

}
