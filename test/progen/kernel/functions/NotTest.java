package test.progen.kernel.functions;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import progen.kernel.functions.False;
import progen.kernel.functions.Not;
import progen.kernel.functions.True;
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
public class NotTest{

	private Not not;
	
	@Before
	public void setUp(){
		not=new Not();
	}
	
	@Test
	public void notTest() {
		assertTrue(not instanceof NonTerminal);
		assertEquals(1, not.getArity());
		assertEquals("boolean", not.getReturnType());
		assertEquals("boolean$$boolean", not.getSignature());
		assertEquals("NOT", not.getSymbol());
		
	}

	@Test
	public void evaluateTest(){
		List<Node> arguments=new ArrayList<Node>();
		UserProgram userProgram=null;
		HashMap<String, Object> returnAddr=null;
		
		Node a = new Node(new GrammarNonTerminalSymbol("Ax", not.getReturnType().toString()));
		
		GrammarTerminalSymbol trueSymbol = new GrammarTerminalSymbol(new True());
		GrammarTerminalSymbol falseSymbol = new GrammarTerminalSymbol(new False());
		
		arguments.add(a);
		
		//NOT true
		a.setFunction(trueSymbol);
		assertFalse((Boolean)not.evaluate(arguments, userProgram, returnAddr));
		
		//NOT false
		a.setFunction(falseSymbol);
		assertTrue((Boolean)not.evaluate(arguments, userProgram, returnAddr));
		
	}
	
}
