package progen.kernel.functions;

import org.junit.Before;
import org.junit.Test;
import progen.kernel.functions.IntM;
import progen.kernel.functions.IntN;
import progen.kernel.functions.IntPow;
import progen.kernel.functions.NonTerminal;
import progen.kernel.grammar.GrammarNonTerminalSymbol;
import progen.kernel.grammar.GrammarTerminalSymbol;
import progen.kernel.tree.Node;
import progen.userprogram.UserProgram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author jirsis
 * @since 2.0
 */
public class IntPowTest {

  private IntPow pow;

  @Before
  public void setUp() {
    pow = new IntPow();
  }

  @Test
  public void powTest() {
    assertTrue(pow instanceof NonTerminal);
    assertEquals(2, pow.getArity());
    assertEquals("double", pow.getReturnType());
    assertEquals("double$$int$$int", pow.getSignature());
    assertEquals("^", pow.getSymbol());

  }

  @Test
  public void evaluateTest() {
    List<Node> arguments = new ArrayList<Node>();
    UserProgram userProgram = null;
    HashMap<String, Object> returnAddr = null;

    Node a = new Node(new GrammarNonTerminalSymbol("Ax", pow.getReturnType().toString()));
    Node b = new Node(new GrammarNonTerminalSymbol("Ax", pow.getReturnType().toString()));

    IntN x = new IntN();
    x.setValue(1);
    GrammarTerminalSymbol one = new GrammarTerminalSymbol(x);
    IntM y = new IntM();
    y.setValue(-2);
    GrammarTerminalSymbol two = new GrammarTerminalSymbol(y);
    IntM z = new IntM();
    z.setValue(0);
    GrammarTerminalSymbol zero = new GrammarTerminalSymbol(z);

    arguments.add(a);
    arguments.add(b);

    //1^-2?
    a.setFunction(one);
    b.setFunction(two);
    assertEquals(1, ((Double) pow.evaluate(arguments, userProgram, returnAddr)), 0.0);


    //1^0?
    a.setFunction(one);
    b.setFunction(zero);
    assertEquals(1, ((Double) pow.evaluate(arguments, userProgram, returnAddr)), 0);

    //-2.0^0.0?
    a.setFunction(two);
    b.setFunction(zero);
    assertEquals(1, ((Double) pow.evaluate(arguments, userProgram, returnAddr)), 0.0);

  }

}
