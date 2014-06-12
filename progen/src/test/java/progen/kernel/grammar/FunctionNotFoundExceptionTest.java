package progen.kernel.grammar;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FunctionNotFoundExceptionTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();
  
  
  @Test
  public void testString() {
    exception.expect(FunctionNotFoundException.class);
    exception.expectMessage("Function not found in classpath. [Function]");
    throw new FunctionNotFoundException("Function");
  }

}
