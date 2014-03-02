package test.progen.kernel.functions;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import progen.kernel.functions.False;
import progen.kernel.functions.True;
import progen.kernel.functions.XOr;
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
public class XOrTest{

	private XOr xor;
	
	@Before
	public void setUp(){
		xor=new XOr();
	}
	
	@Test
	public void xorTest() {
		assertTrue(xor instanceof NonTerminal);
		assertTrue(xor.getArity()==2);
		assertTrue(xor.getReturnType().equals("boolean"));
		assertTrue(xor.getSignature().equals("boolean$$boolean$$boolean"));
		assertTrue(xor.getSymbol().equals("^"));
		
	}

	@Test
	public void evaluateTest(){
		List<Node> arguments=new ArrayList<Node>();
		UserProgram userProgram=null;
		HashMap<String, Object> returnAddr=null;
		
		Node a = new Node(new GrammarNonTerminalSymbol("Ax", xor.getReturnType().toString()));
		Node b = new Node(new GrammarNonTerminalSymbol("Ax", xor.getReturnType().toString()));
		
		GrammarTerminalSymbol trueSymbol = new GrammarTerminalSymbol(new True());
		GrammarTerminalSymbol falseSymbol = new GrammarTerminalSymbol(new False());
		
		arguments.add(a);
		arguments.add(b);
		
		a.setFunction(trueSymbol);
		b.setFunction(trueSymbol);
		assertFalse((Boolean)xor.evaluate(arguments, userProgram, returnAddr));
		
		a.setFunction(trueSymbol);
		b.setFunction(falseSymbol);
		assertTrue((Boolean)xor.evaluate(arguments, userProgram, returnAddr));
		
		a.setFunction(falseSymbol);
		b.setFunction(trueSymbol);
		assertTrue((Boolean)xor.evaluate(arguments, userProgram, returnAddr));
		
		a.setFunction(falseSymbol);
		b.setFunction(falseSymbol);
		assertFalse((Boolean)xor.evaluate(arguments, userProgram, returnAddr));
	}
	
}
