package test.progen.kernel.functions;

import org.junit.Before;
import org.junit.Test;
import progen.kernel.functions.DoubleX;
import progen.kernel.functions.Terminal;
import progen.kernel.tree.Node;
import progen.userprogram.UserProgram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * @author jirsis
 * @since 2.0
 */
public class DoubleXTest {

  private DoubleX dX;

  @Before
  public void setUp() {
    dX = new DoubleX();
    dX.setValue(0.0);
  }

  @Test
  public void doubleXTest() {
    assertTrue(dX instanceof Terminal);
    assertTrue(dX.getArity() == 0);
    assertTrue(dX.getReturnType().equals("double"));
    assertTrue(dX.getSignature().equals("double"));
    assertTrue(dX.getSymbol().equals("dX"));
    assertTrue((Double) (dX.getValue()) == 0);

  }

  @Test
  public void evaluateTest() {
    List<Node> arguments = new ArrayList<Node>();
    UserProgram userProgram = null;
    HashMap<String, Object> returnAddr = null;
    assertTrue((Double) dX.evaluate(arguments, userProgram, returnAddr) == 0.0);

    dX.setValue(Math.PI);
    assertTrue((Double) dX.evaluate(arguments, userProgram, returnAddr) == Math.PI);
  }

}
