package progen.kernel.functions;

import org.junit.Before;
import org.junit.Test;
import progen.kernel.functions.DoubleMult;
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
public class DoubleMultTest {

  private DoubleMult mult;

  @Before
  public void setUp() {
    mult = new DoubleMult();
  }

  @Test
  public void multTest() {
    assertTrue(mult instanceof NonTerminal);
    assertTrue(mult.getArity() == 2);
    assertTrue(mult.getReturnType().equals("double"));
    assertTrue(mult.getSignature().equals("double$$double$$double"));
    assertTrue(mult.getSymbol().equals("*"));

  }

  @Test
  public void evaluateTest() {
    List<Node> arguments = new ArrayList<Node>();
    UserProgram userProgram = null;
    HashMap<String, Object> returnAddr = null;

    Node a = new Node(new GrammarNonTerminalSymbol("Ax", mult.getReturnType().toString()));
    Node b = new Node(new GrammarNonTerminalSymbol("Ax", mult.getReturnType().toString()));

    DoubleX x = new DoubleX();
    x.setValue(1.5);
    GrammarTerminalSymbol one = new GrammarTerminalSymbol(x);
    DoubleY y = new DoubleY();
    y.setValue(2.0);
    GrammarTerminalSymbol two = new GrammarTerminalSymbol(y);

    arguments.add(a);
    arguments.add(b);

    //1.5*2?
    a.setFunction(one);
    b.setFunction(two);
    assertEquals(3.0, ((Double) mult.evaluate(arguments, userProgram, returnAddr)), 0.0);

    //2*1.5?
    a.setFunction(two);
    b.setFunction(one);
    assertEquals(3.0, ((Double) mult.evaluate(arguments, userProgram, returnAddr)), 0.0);

    //1.5*1.5?
    a.setFunction(one);
    b.setFunction(one);
    assertEquals(2.25, ((Double) mult.evaluate(arguments, userProgram, returnAddr)), 0.0);

    //2*2?
    a.setFunction(two);
    b.setFunction(two);
    assertEquals(4.0, ((Double) mult.evaluate(arguments, userProgram, returnAddr)), 0.0);
  }

}
