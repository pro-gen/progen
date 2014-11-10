package progen.kernel.evolution.selector;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TournamentSizeMandatoryExceptionTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void testException() {
    exception.expect(TournamentSizeMandatoryException.class);
    exception.expectMessage("You must define the tournament size, is mandatory parameter.");
    throw new TournamentSizeMandatoryException(new IllegalArgumentException());
  }

  @Test
  public void test() {
    exception.expect(TournamentSizeMandatoryException.class);
    exception.expectMessage("You must define the tournament size, is mandatory parameter.");
    throw new TournamentSizeMandatoryException();
  }

}
