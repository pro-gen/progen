package progen.kernel.tree;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class IncompatibleOptionsInitTreeMethodExceptionTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();
  
  @Test
  public void testIntIntInt() {
    exception.expect(IncompatibleOptionsInitTreeMethodException.class);
    exception.expectMessage("Incompatible parameters to generate a valid tree.[maxNodes: 1, depth-interval: 2, 3]");
    throw new IncompatibleOptionsInitTreeMethodException(1, 2, 3);
  }

}