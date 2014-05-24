package progen.context;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class UninitializedContextExceptionTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();
  
  @Test
  public void test() {
      exception.expect(UninitializedContextException.class);
      exception.expectMessage("No inicialized properties. First call makeInstance() method.");
      throw new UninitializedContextException();
    }
    
}
