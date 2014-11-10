package progen.kernel.evolution.operators;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SelectorSizeIncorrectValueExceptionTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();
  
  
  @Test
  public void testIntInt() {
    exception.expect(SelectorSizeIncorrectValueException.class);
    exception.expectMessage("The size of the selector is not correct.(1!=0)");
    throw new SelectorSizeIncorrectValueException(0,1);
  }

}

