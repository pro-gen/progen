package test.progen.kernel.functions;

import org.junit.Before;
import org.junit.Test;
import progen.kernel.functions.DoublePow;
import progen.kernel.functions.DoubleX;
import progen.kernel.functions.DoubleY;
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
public class DoublePowTest {

  private DoublePow pow;

  @Before
  public void setUp() {
    pow = new DoublePow();
  }

  @Test
  public void powTest() {
    assertTrue(pow instanceof NonTerminal);
    assertTrue(pow.getArity() == 2);
    assertTrue(pow.getReturnType().equals("double"));
    assertTrue(pow.getSignature().equals("double$$double$$double"));
    assertTrue(pow.getSymbol().equals("^"));

  }

  @Test
  public void evaluateTest() {
    List<Node> arguments = new ArrayList<Node>();
    UserProgram userProgram = null;
    HashMap<String, Object> returnAddr = null;

    Node a = new Node(new GrammarNonTerminalSymbol("Ax", pow.getReturnType().toString()));
    Node b = new Node(new GrammarNonTerminalSymbol("Ax", pow.getReturnType().toString()));

    DoubleX x = new DoubleX();
    x.setValue(1.5);
    GrammarTerminalSymbol one = new GrammarTerminalSymbol(x);
    DoubleY y = new DoubleY();
    y.setValue(-2.0);
    GrammarTerminalSymbol two = new GrammarTerminalSymbol(y);
    DoubleY z = new DoubleY();
    z.setValue(0.0);
    GrammarTerminalSymbol zero = new GrammarTerminalSymbol(z);

    arguments.add(a);
    arguments.add(b);

    //1.5^-2?
    a.setFunction(one);
    b.setFunction(two);
    assertEquals(0.444, ((Double) pow.evaluate(arguments, userProgram, returnAddr)), 0.004);


    //1.5^0.0?
    a.setFunction(one);
    b.setFunction(zero);
    assertEquals(1.0, ((Double) pow.evaluate(arguments, userProgram, returnAddr)), 0.0);

    //-2.0^0.0?
    a.setFunction(two);
    b.setFunction(zero);
    assertEquals(1.0, ((Double) pow.evaluate(arguments, userProgram, returnAddr)), 0.0);

  }

}
