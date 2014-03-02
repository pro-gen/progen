package test.progen.kernel.functions;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import progen.kernel.functions.BitAnd;
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
public class BitAndTest{

	private BitAnd and;
	
	@Before
	public void setUp(){
		and=new BitAnd();
	}
	
	@Test
	public void andTest() {
		assertTrue(and instanceof NonTerminal);
		assertEquals(2, and.getArity());
		assertEquals("int", and.getReturnType());
		assertEquals("int$$int$$int", and.getSignature());
		assertEquals("&", and.getSymbol());
		
	}

	@Test
	public void evaluateTest(){
		List<Node> arguments=new ArrayList<Node>();
		UserProgram userProgram=null;
		HashMap<String, Object> returnAddr=null;
		
		Node a = new Node(new GrammarNonTerminalSymbol("Ax", and.getReturnType().toString()));
		Node b = new Node(new GrammarNonTerminalSymbol("Ax", and.getReturnType().toString()));
		
		IntN x =new IntN();
		x.setValue(0x12);
		GrammarTerminalSymbol x12 = new GrammarTerminalSymbol(x);
		IntM y =new IntM();
		y.setValue(0x11);
		GrammarTerminalSymbol x11 = new GrammarTerminalSymbol(y);
		
		arguments.add(a);
		arguments.add(b);
		
		//0x11 & 0x12
		a.setFunction(x11);
		b.setFunction(x12);
		assertEquals(0x10, (Integer)and.evaluate(arguments, userProgram, returnAddr), 0);
		
		//0x12 & 0x11
		a.setFunction(x12);
		b.setFunction(x11);
		assertEquals(0x10, (Integer)and.evaluate(arguments, userProgram, returnAddr), 0);
		
		//0x11 & 0x11
		a.setFunction(x11);
		b.setFunction(x11);
		assertEquals(0x11, (Integer)and.evaluate(arguments, userProgram, returnAddr), 0);
		
		//0x12 & 0x12
		a.setFunction(x12);
		b.setFunction(x12);
		assertEquals(0x12, (Integer)and.evaluate(arguments, userProgram, returnAddr), 0);
	}
	
}
