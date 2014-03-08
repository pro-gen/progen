package test.progen.kernel.functions;

import org.junit.Before;
import org.junit.Test;
import progen.kernel.functions.IntGratherEqualsThan;
import progen.kernel.functions.IntM;
import progen.kernel.functions.IntN;
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
public class IntGratherEqualsThanTest {

  private IntGratherEqualsThan ge;

  @Before
  public void setUp() {
    ge = new IntGratherEqualsThan();
  }

  @Test
  public void gratherEqualsThanTest() {
    assertTrue(ge instanceof NonTerminal);
    assertTrue(ge.getArity() == 2);
    assertTrue(ge.getReturnType().equals("boolean"));
    assertTrue(ge.getSignature().equals("boolean$$int$$int"));
    assertTrue(ge.getSymbol().equals(">="));

  }

  @Test
  public void evaluateTest() {
    List<Node> arguments = new ArrayList<Node>();
    UserProgram userProgram = null;
    HashMap<String, Object> returnAddr = null;

    Node a = new Node(new GrammarNonTerminalSymbol("Ax", ge.getReturnType().toString()));
    Node b = new Node(new GrammarNonTerminalSymbol("Ax", ge.getReturnType().toString()));

    IntN x = new IntN();
    x.setValue(1);
    GrammarTerminalSymbol one = new GrammarTerminalSymbol(x);
    IntM y = new IntM();
    y.setValue(2);
    GrammarTerminalSymbol two = new GrammarTerminalSymbol(y);

    arguments.add(a);
    arguments.add(b);

    //1>=2?
    a.setFunction(one);
    b.setFunction(two);
    assertFalse((Boolean) ge.evaluate(arguments, userProgram, returnAddr));

    //2>=1?
    a.setFunction(two);
    b.setFunction(one);
    assertTrue((Boolean) ge.evaluate(arguments, userProgram, returnAddr));

    //1>=1?
    a.setFunction(one);
    b.setFunction(one);
    assertTrue((Boolean) ge.evaluate(arguments, userProgram, returnAddr));

    //2>=2?
    a.setFunction(two);
    b.setFunction(two);
    assertTrue((Boolean) ge.evaluate(arguments, userProgram, returnAddr));
  }

}
