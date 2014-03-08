package test.progen.kernel.functions;

import org.junit.Before;
import org.junit.Test;
import progen.kernel.functions.DoubleNotEquals;
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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author jirsis
 * @since 2.0
 */
public class DoubleNotEqualsTest {

  private DoubleNotEquals neq;

  @Before
  public void setUp() {
    neq = new DoubleNotEquals();
  }

  @Test
  public void gratherEqualsThanTest() {
    assertTrue(neq instanceof NonTerminal);
    assertTrue(neq.getArity() == 2);
    assertTrue(neq.getReturnType().equals("boolean"));
    assertTrue(neq.getSignature().equals("boolean$$double$$double"));
    assertTrue(neq.getSymbol().equals("!="));

  }

  @Test
  public void evaluateTest() {
    List<Node> arguments = new ArrayList<Node>();
    UserProgram userProgram = null;
    HashMap<String, Object> returnAddr = null;

    Node a = new Node(new GrammarNonTerminalSymbol("Ax", neq.getReturnType().toString()));
    Node b = new Node(new GrammarNonTerminalSymbol("Ax", neq.getReturnType().toString()));

    DoubleX x = new DoubleX();
    x.setValue(1.0);
    GrammarTerminalSymbol one = new GrammarTerminalSymbol(x);
    DoubleY y = new DoubleY();
    y.setValue(2.0);
    GrammarTerminalSymbol two = new GrammarTerminalSymbol(y);

    arguments.add(a);
    arguments.add(b);

    //1!=2?
    a.setFunction(one);
    b.setFunction(two);
    assertTrue((Boolean) neq.evaluate(arguments, userProgram, returnAddr));

    //2!=1?
    a.setFunction(two);
    b.setFunction(one);
    assertTrue((Boolean) neq.evaluate(arguments, userProgram, returnAddr));

    //1!=1?
    a.setFunction(one);
    b.setFunction(one);
    assertFalse((Boolean) neq.evaluate(arguments, userProgram, returnAddr));

    //2!=2?
    a.setFunction(two);
    b.setFunction(two);
    assertFalse((Boolean) neq.evaluate(arguments, userProgram, returnAddr));
  }

}
