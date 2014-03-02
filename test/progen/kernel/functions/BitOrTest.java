package test.progen.kernel.functions;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import progen.kernel.functions.BitOr;
import progen.kernel.functions.IntM;
import progen.kernel.functions.IntN;
import progen.kernel.tree.Node;
import progen.userprogram.UserProgram;
import progen.kernel.functions.NonTerminal;
import progen.kernel.grammar.GrammarNonTerminalSymbol;
import progen.kernel.grammar.GrammarTerminalSymbol;

/**
 * 
 * @author jirsis
 * @since 2.0
 */
public class BitOrTest{

	private BitOr or;
	
	@Before
	public void setUp(){
		or=new BitOr();
	}
	
	@Test
	public void andTest() {
		assertTrue(or instanceof NonTerminal);
		assertEquals(2, or.getArity());
		assertEquals("int", or.getReturnType());
		assertEquals("int$$int$$int", or.getSignature());
		assertEquals("|", or.getSymbol());
		
	}

	@Test
	public void evaluateTest(){
		List<Node> arguments=new ArrayList<Node>();
		UserProgram userProgram=null;
		HashMap<String, Object> returnAddr=null;
		
		Node a = new Node(new GrammarNonTerminalSymbol("Ax", or.getReturnType().toString()));
		Node b = new Node(new GrammarNonTerminalSymbol("Ax", or.getReturnType().toString()));
		
		IntN x =new IntN();
		x.setValue(0x12);
		GrammarTerminalSymbol x12 = new GrammarTerminalSymbol(x);
		IntM y =new IntM();
		y.setValue(0x11);
		GrammarTerminalSymbol x11 = new GrammarTerminalSymbol(y);
		
		arguments.add(a);
		arguments.add(b);
		
		//0x11 | 0x12
		a.setFunction(x11);
		b.setFunction(x12);
		assertEquals(0x13, (Integer)or.evaluate(arguments, userProgram, returnAddr), 0);
		
		//0x12 | 0x11
		a.setFunction(x12);
		b.setFunction(x11);
		assertEquals(0x13, (Integer)or.evaluate(arguments, userProgram, returnAddr), 0);
		
		//0x11 | 0x11
		a.setFunction(x11);
		b.setFunction(x11);
		assertEquals(0x11, (Integer)or.evaluate(arguments, userProgram, returnAddr), 0);
		
		//0x12 | 0x12
		a.setFunction(x12);
		b.setFunction(x12);
		assertEquals(0x12, (Integer)or.evaluate(arguments, userProgram, returnAddr), 0);
	}
	
}
