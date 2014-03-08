package test.progen.kernel.functions;

import org.junit.Before;
import org.junit.Test;
import progen.kernel.functions.And;
import progen.kernel.functions.False;
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
public class AndTest {

  private And and;

  @Before
  public void setUp() {
    and = new And();
  }

  @Test
  public void andTest() {
    assertTrue(and.getArity() == 2);
    assertTrue(and.getReturnType().equals("boolean"));
    assertTrue(and.getSignature().equals("boolean$$boolean$$boolean"));
    assertTrue(and.getSymbol().equals("&&"));

  }

  @Test
  public void evaluateTest() {
    List<Node> arguments = new ArrayList<Node>();
    UserProgram userProgram = null;
    HashMap<String, Object> returnAddr = null;

    Node a = new Node(new GrammarNonTerminalSymbol("Ax", and.getReturnType().toString()));
    Node b = new Node(new GrammarNonTerminalSymbol("Ax", and.getReturnType().toString()));

    GrammarTerminalSymbol trueSymbol = new GrammarTerminalSymbol(new True());
    GrammarTerminalSymbol falseSymbol = new GrammarTerminalSymbol(new False());

    arguments.add(a);
    arguments.add(b);

    a.setFunction(trueSymbol);
    b.setFunction(trueSymbol);
    assertTrue((Boolean) and.evaluate(arguments, userProgram, returnAddr));

    a.setFunction(trueSymbol);
    b.setFunction(falseSymbol);
    assertFalse((Boolean) and.evaluate(arguments, userProgram, returnAddr));

    a.setFunction(falseSymbol);
    b.setFunction(trueSymbol);
    assertFalse((Boolean) and.evaluate(arguments, userProgram, returnAddr));

    a.setFunction(falseSymbol);
    a.setFunction(falseSymbol);
    assertFalse((Boolean) and.evaluate(arguments, userProgram, returnAddr));
  }

}
