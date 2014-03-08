package test.progen.kernel.functions;

import org.junit.Before;
import org.junit.Test;
import progen.kernel.functions.DoubleDiv;
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
public class DoubleDivTest {

  private DoubleDiv div;

  @Before
  public void setUp() {
    div = new DoubleDiv();
  }

  @Test
  public void plusTest() {
    assertTrue(div instanceof NonTerminal);
    assertTrue(div.getArity() == 2);
    assertTrue(div.getReturnType().equals("double"));
    assertTrue(div.getSignature().equals("double$$double$$double"));
    assertTrue(div.getSymbol().equals("/"));

  }

  @Test
  public void evaluateTest() {
    List<Node> arguments = new ArrayList<Node>();
    UserProgram userProgram = null;
    HashMap<String, Object> returnAddr = null;

    Node a = new Node(new GrammarNonTerminalSymbol("Ax", div.getReturnType().toString()));
    Node b = new Node(new GrammarNonTerminalSymbol("Ax", div.getReturnType().toString()));

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

    //1.5/-2?
    a.setFunction(one);
    b.setFunction(two);
    assertEquals(-0.75, ((Double) div.evaluate(arguments, userProgram, returnAddr)), 0.0);

    //-2/1.5?
    a.setFunction(two);
    b.setFunction(one);
    assertEquals(-1.333, ((Double) div.evaluate(arguments, userProgram, returnAddr)), 0.03);


    //1.5/1.5?
    a.setFunction(one);
    b.setFunction(one);
    assertEquals(1.0, ((Double) div.evaluate(arguments, userProgram, returnAddr)), 0.0);

    //2/2?
    a.setFunction(two);
    b.setFunction(two);
    assertEquals(1.0, ((Double) div.evaluate(arguments, userProgram, returnAddr)), 0.0);

    //1.5/0.0?
    a.setFunction(one);
    b.setFunction(zero);
    assertEquals(Double.POSITIVE_INFINITY, ((Double) div.evaluate(arguments, userProgram, returnAddr)), 0.0);
  }

}
