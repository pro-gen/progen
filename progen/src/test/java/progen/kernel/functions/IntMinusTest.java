package test.progen.kernel.functions;

import org.junit.Before;
import org.junit.Test;
import progen.kernel.functions.IntM;
import progen.kernel.functions.IntMinus;
import progen.kernel.functions.IntN;
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
public class IntMinusTest {

  private IntMinus minus;

  @Before
  public void setUp() {
    minus = new IntMinus();
  }

  @Test
  public void minusTest() {
    assertTrue(minus instanceof NonTerminal);
    assertEquals(2, minus.getArity());
    assertEquals("int", minus.getReturnType());
    assertEquals("int$$int$$int", minus.getSignature());
    assertEquals("-", minus.getSymbol());

  }

  @Test
  public void evaluateTest() {
    List<Node> arguments = new ArrayList<Node>();
    UserProgram userProgram = null;
    HashMap<String, Object> returnAddr = null;

    Node a = new Node(new GrammarNonTerminalSymbol("Ax", minus.getReturnType().toString()));
    Node b = new Node(new GrammarNonTerminalSymbol("Ax", minus.getReturnType().toString()));

    IntN x = new IntN();
    x.setValue(1);
    GrammarTerminalSymbol one = new GrammarTerminalSymbol(x);
    IntM y = new IntM();
    y.setValue(2);
    GrammarTerminalSymbol two = new GrammarTerminalSymbol(y);

    arguments.add(a);
    arguments.add(b);

    //1-2?
    a.setFunction(one);
    b.setFunction(two);
    assertEquals(-1, ((Integer) minus.evaluate(arguments, userProgram, returnAddr)), 0);

    //2-1?
    a.setFunction(two);
    b.setFunction(one);
    assertEquals(1, ((Integer) minus.evaluate(arguments, userProgram, returnAddr)), 0);

    //1-1?
    a.setFunction(one);
    b.setFunction(one);
    assertEquals(0, ((Integer) minus.evaluate(arguments, userProgram, returnAddr)), 0);

    //2-2?
    a.setFunction(two);
    b.setFunction(two);
    assertEquals(0, ((Integer) minus.evaluate(arguments, userProgram, returnAddr)), 0);
  }

}
