package progen.kernel.functions;

import org.junit.Before;
import org.junit.Test;
import progen.kernel.functions.BitMult;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author jirsis
 * @since 2.0
 */
public class BitMultTest {

  private BitMult mult;

  @Before
  public void setUp() {
    mult = new BitMult();
  }

  @Test
  public void andTest() {
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
    x.setValue(0x12);
    GrammarTerminalSymbol x12 = new GrammarTerminalSymbol(x);
    IntM y = new IntM();
    y.setValue(0x11);
    GrammarTerminalSymbol x11 = new GrammarTerminalSymbol(y);
    IntM z = new IntM();
    z.setValue(0x0);
    GrammarTerminalSymbol x0 = new GrammarTerminalSymbol(z);

    arguments.add(a);
    arguments.add(b);

    //0x11 * 0x12
    a.setFunction(x11);
    b.setFunction(x12);
    assertEquals(0x11 * 0x12, (Integer) mult.evaluate(arguments, userProgram, returnAddr), 0);

    //0x12 * 0x11
    a.setFunction(x12);
    b.setFunction(x11);
    assertEquals(0x12 * 0x11, (Integer) mult.evaluate(arguments, userProgram, returnAddr), 0);

    //0x11 * 0x11
    a.setFunction(x11);
    b.setFunction(x11);
    assertEquals(0x11 * 0x11, (Integer) mult.evaluate(arguments, userProgram, returnAddr), 0);

    //0x12 * 0x12
    a.setFunction(x12);
    b.setFunction(x12);
    assertEquals(0x12 * 0x12, (Integer) mult.evaluate(arguments, userProgram, returnAddr), 0);

    //0x12 * 0x0
    a.setFunction(x12);
    b.setFunction(x0);
    assertEquals(0x12 * 0x0, (Integer) mult.evaluate(arguments, userProgram, returnAddr), 0);

    //0x12 * 0x1
    a.setFunction(x12);
    z.setValue(0x1);
    assertEquals(0x12 * 0x1, (Integer) mult.evaluate(arguments, userProgram, returnAddr), 0);

  }

}
