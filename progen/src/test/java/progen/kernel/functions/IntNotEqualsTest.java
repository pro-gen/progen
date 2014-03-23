package progen.kernel.functions;

import org.junit.Before;
import org.junit.Test;
import progen.kernel.functions.IntM;
import progen.kernel.functions.IntN;
import progen.kernel.functions.IntNotEquals;
import progen.kernel.functions.NonTerminal;
import progen.kernel.grammar.GrammarNonTerminalSymbol;
import progen.kernel.grammar.GrammarTerminalSymbol;
import progen.kernel.tree.Node;
import progen.userprogram.UserProgram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author jirsis
 * @since 2.0
 */
public class IntNotEqualsTest {

  private IntNotEquals neq;

  @Before
  public void setUp() {
    neq = new IntNotEquals();
  }

  @Test
  public void gratherEqualsThanTest() {
    assertTrue(neq instanceof NonTerminal);
    assertEquals(2, neq.getArity());
    assertEquals("boolean", neq.getReturnType());
    assertEquals("boolean$$int$$int", neq.getSignature());
    assertEquals("!=", neq.getSymbol());

  }

  @Test
  public void evaluateTest() {
    List<Node> arguments = new ArrayList<Node>();
    UserProgram userProgram = null;
    HashMap<String, Object> returnAddr = null;

    Node a = new Node(new GrammarNonTerminalSymbol("Ax", neq.getReturnType().toString()));
    Node b = new Node(new GrammarNonTerminalSymbol("Ax", neq.getReturnType().toString()));

    IntN x = new IntN();
    x.setValue(1);
    GrammarTerminalSymbol one = new GrammarTerminalSymbol(x);
    IntM y = new IntM();
    y.setValue(2);
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
