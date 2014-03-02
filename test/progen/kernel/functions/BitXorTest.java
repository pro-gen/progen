package test.progen.kernel.functions;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import progen.kernel.functions.BitXor;
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
public class BitXorTest{

	private BitXor xor;
	
	@Before
	public void setUp(){
		xor=new BitXor();
	}
	
	@Test
	public void andTest() {
		assertTrue(xor instanceof NonTerminal);
		assertEquals(2, xor.getArity());
		assertEquals("int", xor.getReturnType());
		assertEquals("int$$int$$int", xor.getSignature());
		assertEquals("^", xor.getSymbol());
		
	}

	@Test
	public void evaluateTest(){
		List<Node> arguments=new ArrayList<Node>();
		UserProgram userProgram=null;
		HashMap<String, Object> returnAddr=null;
		
		Node a = new Node(new GrammarNonTerminalSymbol("Ax", xor.getReturnType().toString()));
		Node b = new Node(new GrammarNonTerminalSymbol("Ax", xor.getReturnType().toString()));
		
		IntN x =new IntN();
		x.setValue(0x12);
		GrammarTerminalSymbol x12 = new GrammarTerminalSymbol(x);
		IntM y =new IntM();
		y.setValue(0x11);
		GrammarTerminalSymbol x11 = new GrammarTerminalSymbol(y);
		IntM z =new IntM();
		z.setValue(0xffffffee);
		GrammarTerminalSymbol xffffffee = new GrammarTerminalSymbol(z);
		
		arguments.add(a);
		arguments.add(b);
		
		//0x11 ^ 0x12
		a.setFunction(x11);
		b.setFunction(x12);
		assertEquals(0x03, (Integer)xor.evaluate(arguments, userProgram, returnAddr), 0);
		
		//0x12 ^ 0x11
		a.setFunction(x12);
		b.setFunction(x11);
		assertEquals(0x03, (Integer)xor.evaluate(arguments, userProgram, returnAddr), 0);
		
		//0x11 ^ 0x11
		a.setFunction(x11);
		b.setFunction(x11);
		assertEquals(0x0, (Integer)xor.evaluate(arguments, userProgram, returnAddr), 0);
		
		//0x12 ^ 0x12
		a.setFunction(x12);
		b.setFunction(x12);
		assertEquals(0x0, (Integer)xor.evaluate(arguments, userProgram, returnAddr), 0);
		
		//0x11 ^ 0xffffffee
		a.setFunction(x11);
		b.setFunction(xffffffee);
		assertEquals(0xffffffff, (Integer)xor.evaluate(arguments, userProgram, returnAddr), 0);
	}
	
}