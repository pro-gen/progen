package progen.kernel.functions;

import org.junit.Before;
import org.junit.Test;
import progen.kernel.functions.False;
import progen.kernel.functions.Terminal;
import progen.kernel.tree.Node;
import progen.userprogram.UserProgram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author jirsis
 * @since 2.0
 */
public class FalseTest {

  private False falseFunction;

  @Before
  public void setUp() {
    falseFunction = new False();
  }

  @Test
  public void trueTest() {
    assertTrue(falseFunction instanceof Terminal);
    assertTrue(falseFunction.getArity() == 0);
    assertTrue(falseFunction.getReturnType().equals("boolean"));
    assertTrue(falseFunction.getSignature().equals("boolean"));
    assertTrue(falseFunction.getSymbol().equals("FALSE"));
    assertTrue(falseFunction.getValue().equals(false));

  }

  @Test
  public void evaluateTest() {
    List<Node> arguments = new ArrayList<Node>();
    UserProgram userProgram = null;
    HashMap<String, Object> returnAddr = null;
    assertFalse((Boolean) falseFunction.evaluate(arguments, userProgram, returnAddr));
  }

}
