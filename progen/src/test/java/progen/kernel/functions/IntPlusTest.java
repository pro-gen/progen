package progen.kernel.functions;

import org.junit.Before;
import org.junit.Test;
import progen.kernel.functions.IntN;
import progen.kernel.functions.IntPlus;
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
public class IntPlusTest {

  private IntPlus plus;

  @Before
  public void setUp() {
    plus = new IntPlus();
  }

  @Test
  public void plusTest() {
    assertTrue(plus instanceof NonTerminal);
    assertEquals(2, plus.getArity());
    assertEquals("int", plus.getReturnType());
    assertEquals("int$$int$$int", plus.getSignature());
    assertEquals("+", plus.getSymbol());

  }

  @Test
  public void evaluateTest() {
    List<Node> arguments = new ArrayList<Node>();
    UserProgram userProgram = null;
    HashMap<String, Object> returnAddr = null;

    Node a = new Node(new GrammarNonTerminalSymbol("Ax", plus.getReturnType().toString()));
    Node b = new Node(new GrammarNonTerminalSymbol("Ax", plus.getReturnType().toString()));

    IntN x = new IntN();
    x.setValue(1);
    GrammarTerminalSymbol one = new GrammarTerminalSymbol(x);
    IntN y = new IntN();
    y.setValue(2);
    GrammarTerminalSymbol two = new GrammarTerminalSymbol(y);

    arguments.add(a);
    arguments.add(b);

    //1+2?
    a.setFunction(one);
    b.setFunction(two);
    assertEquals(3, ((Integer) plus.evaluate(arguments, userProgram, returnAddr)), 0.0);

    //2+1?
    a.setFunction(two);
    b.setFunction(one);
    assertEquals(3, ((Integer) plus.evaluate(arguments, userProgram, returnAddr)), 0.0);

    //1+1?
    a.setFunction(one);
    b.setFunction(one);
    assertEquals(2, ((Integer) plus.evaluate(arguments, userProgram, returnAddr)), 0.0);

    //2+2?
    a.setFunction(two);
    b.setFunction(two);
    assertEquals(4, ((Integer) plus.evaluate(arguments, userProgram, returnAddr)), 0.0);
  }

}
