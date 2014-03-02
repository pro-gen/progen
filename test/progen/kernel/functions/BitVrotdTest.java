package test.progen.kernel.functions;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import progen.kernel.functions.BitVrotd;
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
public class BitVrotdTest{

	private BitVrotd vrotd;
	
	@Before
	public void setUp(){
		vrotd=new BitVrotd();
	}
	
	@Test
	public void andTest() {
		assertTrue(vrotd instanceof NonTerminal);
		assertEquals(1, vrotd.getArity());
		assertEquals("int", vrotd.getReturnType());
		assertEquals("int$$int", vrotd.getSignature());
		assertEquals("vrotd", vrotd.getSymbol());
		
	}

	@Test
	public void evaluateTest(){
		List<Node> arguments=new ArrayList<Node>();
		UserProgram userProgram=null;
		HashMap<String, Object> returnAddr=null;
		
		Node a = new Node(new GrammarNonTerminalSymbol("Ax", vrotd.getReturnType().toString()));
		
		IntN x =new IntN();
		x.setValue(0x7fff7fff);
		GrammarTerminalSymbol x7fff7fff = new GrammarTerminalSymbol(x);
		
		arguments.add(a);
		
		//0x11 vrotd
		a.setFunction(x7fff7fff);
		assertEquals(-1.073758209E9, (Integer)vrotd.evaluate(arguments, userProgram, returnAddr), 0);
		
	}
	
}
