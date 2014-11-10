package progen.roles;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class UnknownRoleImplementationExceptionTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void testString() {
    exception.expect(UnknownRoleImplementationException.class);
    exception.expectMessage("Definition class Role not found.[extra]");
    throw new UnknownRoleImplementationException("extra");
  }

  @Test
  public void testStringException() {
    exception.expect(UnknownRoleImplementationException.class);
    exception.expectMessage("Definition class Role not found.[extra]");
    throw new UnknownRoleImplementationException("extra", new IllegalArgumentException());
  }

}
