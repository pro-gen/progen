package progen.kernel.population;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class UnknownUserProgramExceptionTest {
  
  @Rule
  public ExpectedException exception = ExpectedException.none();
  

  @Test
  public void testString() {
    exception.expect(UnknownUserProgramException.class);
    exception.expectMessage("User program class not found. (Userprogram.class)");
    throw new UnknownUserProgramException("Userprogram.class");
  }

}
