package progen.roles;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FactoryNotFoundExceptionTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();
  
  
  @Test
  public void testString() {
    exception.expect(FactoryNotFoundException.class);
    exception.expectMessage("Concrete factory not found. [Factory]");
    throw new FactoryNotFoundException("Factory");
  }

}
