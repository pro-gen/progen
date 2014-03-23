package progen.kernel.functions;

import org.junit.Before;
import org.junit.Test;
import progen.kernel.functions.DoubleMinus;
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
public class DoubleMinusTest {

  private DoubleMinus minus;

  @Before
  public void setUp() {
    minus = new DoubleMinus();
  }

  @Test
  public void minusTest() {
    assertTrue(minus instanceof NonTerminal);
    assertTrue(minus.getArity() == 2);
    assertTrue(minus.getReturnType().equals("double"));
    assertTrue(minus.getSignature().equals("double$$double$$double"));
    assertTrue(minus.getSymbol().equals("-"));

  }

  @Test
  public void evaluateTest() {
    List<Node> arguments = new ArrayList<Node>();
    UserProgram userProgram = null;
    HashMap<String, Object> returnAddr = null;

    Node a = new Node(new GrammarNonTerminalSymbol("Ax", minus.getReturnType().toString()));
    Node b = new Node(new GrammarNonTerminalSymbol("Ax", minus.getReturnType().toString()));

    DoubleX x = new DoubleX();
    x.setValue(1.5);
    GrammarTerminalSymbol one = new GrammarTerminalSymbol(x);
    DoubleY y = new DoubleY();
    y.setValue(2.0);
    GrammarTerminalSymbol two = new GrammarTerminalSymbol(y);

    arguments.add(a);
    arguments.add(b);

    //1.5-2?
    a.setFunction(one);
    b.setFunction(two);
    assertEquals(-0.5, ((Double) minus.evaluate(arguments, userProgram, returnAddr)), 0.0);

    //2-1.5?
    a.setFunction(two);
    b.setFunction(one);
    assertEquals(0.5, ((Double) minus.evaluate(arguments, userProgram, returnAddr)), 0.0);

    //1.5-1.5?
    a.setFunction(one);
    b.setFunction(one);
    assertEquals(0.0, ((Double) minus.evaluate(arguments, userProgram, returnAddr)), 0.0);

    //2-2?
    a.setFunction(two);
    b.setFunction(two);
    assertEquals(0.0, ((Double) minus.evaluate(arguments, userProgram, returnAddr)), 0.0);
  }

}
