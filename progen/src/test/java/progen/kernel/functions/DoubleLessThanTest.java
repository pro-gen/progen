package progen.kernel.functions;

import org.junit.Before;
import org.junit.Test;
import progen.kernel.functions.DoubleLessThan;
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
public class DoubleLessThanTest {

  private DoubleLessThan lt;

  @Before
  public void setUp() {
    lt = new DoubleLessThan();
  }

  @Test
  public void lessThanTest() {
    assertTrue(lt instanceof NonTerminal);
    assertTrue(lt.getArity() == 2);
    assertTrue(lt.getReturnType().equals("boolean"));
    assertTrue(lt.getSignature().equals("boolean$$double$$double"));
    assertTrue(lt.getSymbol().equals("<"));

  }

  @Test
  public void evaluateTest() {
    List<Node> arguments = new ArrayList<Node>();
    UserProgram userProgram = null;
    HashMap<String, Object> returnAddr = null;

    Node a = new Node(new GrammarNonTerminalSymbol("Ax", lt.getReturnType().toString()));
    Node b = new Node(new GrammarNonTerminalSymbol("Ax", lt.getReturnType().toString()));

    DoubleX x = new DoubleX();
    x.setValue(1.0);
    GrammarTerminalSymbol one = new GrammarTerminalSymbol(x);
    DoubleY y = new DoubleY();
    y.setValue(2.0);
    GrammarTerminalSymbol two = new GrammarTerminalSymbol(y);

    arguments.add(a);
    arguments.add(b);

    //1<2?
    a.setFunction(one);
    b.setFunction(two);
    assertTrue((Boolean) lt.evaluate(arguments, userProgram, returnAddr));

    //2<1?
    a.setFunction(two);
    b.setFunction(one);
    assertFalse((Boolean) lt.evaluate(arguments, userProgram, returnAddr));

    //1<1?
    a.setFunction(one);
    b.setFunction(one);
    assertFalse((Boolean) lt.evaluate(arguments, userProgram, returnAddr));

    //2<2?
    a.setFunction(two);
    b.setFunction(two);
    assertFalse((Boolean) lt.evaluate(arguments, userProgram, returnAddr));
  }

}
