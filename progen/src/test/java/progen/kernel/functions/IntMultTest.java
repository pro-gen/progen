package test.progen.kernel.functions;

import org.junit.Before;
import org.junit.Test;
import progen.kernel.functions.IntM;
import progen.kernel.functions.IntMult;
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
public class IntMultTest {

  private IntMult mult;

  @Before
  public void setUp() {
    mult = new IntMult();
  }

  @Test
  public void multTest() {
    assertTrue(mult instanceof NonTerminal);
    assertEquals(2, mult.getArity());
    assertEquals("int", mult.getReturnType());
    assertEquals("int$$int$$int", mult.getSignature());
    assertEquals("*", mult.getSymbol());

  }

  @Test
  public void evaluateTest() {
    List<Node> arguments = new ArrayList<Node>();
    UserProgram userProgram = null;
    HashMap<String, Object> returnAddr = null;

    Node a = new Node(new GrammarNonTerminalSymbol("Ax", mult.getReturnType().toString()));
    Node b = new Node(new GrammarNonTerminalSymbol("Ax", mult.getReturnType().toString()));

    IntN x = new IntN();
    x.setValue(1);
    GrammarTerminalSymbol one = new GrammarTerminalSymbol(x);
    IntM y = new IntM();
    y.setValue(2);
    GrammarTerminalSymbol two = new GrammarTerminalSymbol(y);

    arguments.add(a);
    arguments.add(b);

    //1*2?
    a.setFunction(one);
    b.setFunction(two);
    assertEquals(2, ((Integer) mult.evaluate(arguments, userProgram, returnAddr)), 0);

    //2*1?
    a.setFunction(two);
    b.setFunction(one);
    assertEquals(2, ((Integer) mult.evaluate(arguments, userProgram, returnAddr)), 0);

    //1*1?
    a.setFunction(one);
    b.setFunction(one);
    assertEquals(1, ((Integer) mult.evaluate(arguments, userProgram, returnAddr)), 0);

    //2*2?
    a.setFunction(two);
    b.setFunction(two);
    assertEquals(4, ((Integer) mult.evaluate(arguments, userProgram, returnAddr)), 0);
  }

}
