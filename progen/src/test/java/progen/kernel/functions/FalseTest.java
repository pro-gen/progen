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

import static org.junit.Assert.assertEquals;
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
    assertEquals(0, falseFunction.getArity());
    assertEquals("boolean", falseFunction.getReturnType());
    assertEquals("boolean", falseFunction.getSignature());
    assertEquals("FALSE", falseFunction.getSymbol());
    assertEquals(false, falseFunction.getValue());
  }

  @Test
  public void evaluateTest() {
    List<Node> arguments = new ArrayList<Node>();
    UserProgram userProgram = null;
    HashMap<String, Object> returnAddr = null;
    assertFalse((Boolean) falseFunction.evaluate(arguments, userProgram, returnAddr));
  }
 
  @Test
  public void setValueTest(){
    falseFunction.setValue(null);
    assertEquals(0, falseFunction.getArity());
    assertEquals("boolean", falseFunction.getReturnType());
    assertEquals("boolean", falseFunction.getSignature());
    assertEquals("FALSE", falseFunction.getSymbol());
    assertEquals(false, falseFunction.getValue());
  }

}
