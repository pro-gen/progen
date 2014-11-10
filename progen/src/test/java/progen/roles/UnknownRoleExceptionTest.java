package progen.roles;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class UnknownRoleExceptionTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void testString() {
    exception.expect(UnknownRoleException.class);
    exception.expectMessage("Can not create the execution role.(extra)");
    throw new UnknownRoleException("extra");
  }

  @Test
  public void testStringException() {
    exception.expect(UnknownRoleException.class);
    exception.expectMessage("Can not create the execution role.(extra)");
    throw new UnknownRoleException("extra", new IllegalArgumentException());
  }

}
