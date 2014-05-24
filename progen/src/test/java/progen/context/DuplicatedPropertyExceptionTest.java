package progen.context;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class DuplicatedPropertyExceptionTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();
  
  
  @Test
  public void testMissingContextFileExceptionString() {
    exception.expect(DuplicatedPropertyException.class);
    exception.expectMessage("Already defined property.(extra)");
    throw new DuplicatedPropertyException("extra");
  }

  @Test
  public void testDuplicatedPropertyException() {
      exception.expect(DuplicatedPropertyException.class);
      exception.expectMessage("Already defined property.");
      throw new DuplicatedPropertyException();
    }
    
}
