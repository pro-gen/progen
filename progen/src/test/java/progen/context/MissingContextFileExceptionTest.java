package progen.context;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class MissingContextFileExceptionTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void testString() {
    exception.expect(MissingContextFileException.class);
    exception.expectMessage("File not found. (extra)");
    throw new MissingContextFileException("extra");
  }

  @Test
  public void test() {
    exception.expect(MissingContextFileException.class);
    exception.expectMessage("File not found");
    throw new MissingContextFileException();
  }

}
