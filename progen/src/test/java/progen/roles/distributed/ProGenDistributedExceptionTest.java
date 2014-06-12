package progen.roles.distributed;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import progen.roles.distributed.ProGenDistributedException;

public class ProGenDistributedExceptionTest {
  
  @Rule
  public ExpectedException exception = ExpectedException.none();
  
  @Test
  public void testIntString() {
    exception.expect(ProGenDistributedException.class);
    exception.expectMessage("Dispatcher's port is already in use. [1234]");
    throw new ProGenDistributedException(39, "1234");
  }

  @Test
  public void testString() {
    exception.expect(ProGenDistributedException.class);
    exception.expectMessage("Failure at RMI communication [extra]");
    throw new ProGenDistributedException("extra");
  }

  @Test
  public void test() {
      exception.expect(ProGenDistributedException.class);
      exception.expectMessage("Failure at RMI communication");
      throw new ProGenDistributedException();
    }
  
}
