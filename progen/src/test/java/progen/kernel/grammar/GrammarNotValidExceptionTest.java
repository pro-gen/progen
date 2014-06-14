package progen.kernel.grammar;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class GrammarNotValidExceptionTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();
  
  @Test
  public void test() {
    exception.expect(GrammarNotValidException.class);
    exception.expectMessage("Unkown error");
    throw new GrammarNotValidException();
  }
  
  @Test
  public void testInt() {
    exception.expect(GrammarNotValidException.class);
    exception.expectMessage("Unkown error");
    throw new GrammarNotValidException(255);
  }

}
