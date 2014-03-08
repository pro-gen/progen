package test.progen.kernel.functions;

import org.junit.Before;
import org.junit.Test;
import progen.kernel.functions.False;
import progen.kernel.functions.NonTerminal;
import progen.kernel.functions.Or;
import progen.kernel.functions.True;
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
public class OrTest {

  private Or or;

  @Before
  public void setUp() {
    or = new Or();
  }

  @Test
  public void orTest() {
    assertTrue(or instanceof NonTerminal);
    assertTrue(or.getArity() == 2);
    assertTrue(or.getReturnType().equals("boolean"));
    assertTrue(or.getSignature().equals("boolean$$boolean$$boolean"));
    assertTrue(or.getSymbol().equals("||"));

  }

  @Test
  public void evaluateTest() {
    List<Node> arguments = new ArrayList<Node>();
    UserProgram userProgram = null;
    HashMap<String, Object> returnAddr = null;

    Node a = new Node(new GrammarNonTerminalSymbol("Ax", or.getReturnType().toString()));
    Node b = new Node(new GrammarNonTerminalSymbol("Ax", or.getReturnType().toString()));

    GrammarTerminalSymbol trueSymbol = new GrammarTerminalSymbol(new True());
    GrammarTerminalSymbol falseSymbol = new GrammarTerminalSymbol(new False());

    arguments.add(a);
    arguments.add(b);

    a.setFunction(trueSymbol);
    b.setFunction(trueSymbol);
    assertTrue((Boolean) or.evaluate(arguments, userProgram, returnAddr));

    a.setFunction(trueSymbol);
    b.setFunction(falseSymbol);
    assertTrue((Boolean) or.evaluate(arguments, userProgram, returnAddr));

    a.setFunction(falseSymbol);
    b.setFunction(trueSymbol);
    assertTrue((Boolean) or.evaluate(arguments, userProgram, returnAddr));

    a.setFunction(falseSymbol);
    b.setFunction(falseSymbol);
    assertFalse((Boolean) or.evaluate(arguments, userProgram, returnAddr));
  }

}
