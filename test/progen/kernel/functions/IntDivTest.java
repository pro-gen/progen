package test.progen.kernel.functions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import progen.kernel.functions.IntDiv;
import progen.kernel.functions.IntM;
import progen.kernel.functions.IntN;
import progen.kernel.functions.NonTerminal;
import progen.kernel.grammar.GrammarNonTerminalSymbol;
import progen.kernel.grammar.GrammarTerminalSymbol;

import progen.kernel.tree.Node;
import progen.userprogram.UserProgram;

/**
 * 
 * @author jirsis
 * @since 2.0
 */
public class IntDivTest{

	private IntDiv div;
	
	@Before
	public void setUp(){
		div=new IntDiv();
	}
	
	@Test
	public void plusTest() {
		assertTrue(div instanceof NonTerminal);
		assertTrue(div.getArity()==2);
		assertTrue(div.getReturnType().equals("double"));
		assertTrue(div.getSignature().equals("double$$int$$int"));
		assertTrue(div.getSymbol().equals("/"));
		
	}
	
	@Test
	public void evaluateTest(){
		List<Node> arguments=new ArrayList<Node>();
		UserProgram userProgram=null;
		HashMap<String, Object> returnAddr=null;
		
		Node a = new Node(new GrammarNonTerminalSymbol("Ax", div.getReturnType().toString()));
		Node b = new Node(new GrammarNonTerminalSymbol("Ax", div.getReturnType().toString()));
		
		IntN x =new IntN();
		x.setValue(1);
		GrammarTerminalSymbol one = new GrammarTerminalSymbol(x);
		IntM y =new IntM();
		y.setValue(-2);
		GrammarTerminalSymbol two = new GrammarTerminalSymbol(y);
		IntM z =new IntM();
		z.setValue(0);
		GrammarTerminalSymbol zero = new GrammarTerminalSymbol(z);
		
		arguments.add(a);
		arguments.add(b);
		
		//1/-2?
		a.setFunction(one);
		b.setFunction(two);
		assertEquals(-0.5, ((Double)div.evaluate(arguments, userProgram, returnAddr)), 0.0);
		
		//-2/1?
		a.setFunction(two);
		b.setFunction(one);
		assertEquals(-2.0, ((Double)div.evaluate(arguments, userProgram, returnAddr)), 0.0);
		
		
		//1/1?
		a.setFunction(one);
		b.setFunction(one);
		assertEquals(1.0, ((Double)div.evaluate(arguments, userProgram, returnAddr)), 0.0);
		
		//2/2?
		a.setFunction(two);
		b.setFunction(two);
		assertEquals(1.0, ((Double)div.evaluate(arguments, userProgram, returnAddr)), 0.0);
		
		//1/0?
		a.setFunction(one);
		b.setFunction(zero);
		assertEquals(Double.POSITIVE_INFINITY, ((Double)div.evaluate(arguments, userProgram, returnAddr)), 0.0);
	}

}
