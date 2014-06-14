package progen.experimenter.property;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class IlegalPropertySeparatorExceptionTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();
  
  @Test
  public void testString() {
    exception.expect(IlegalPropertySeparatorException.class);
    exception.expectMessage("The property delimiter is not valid. (test)");
    throw new IlegalPropertySeparatorException("test");
  }
  
}
