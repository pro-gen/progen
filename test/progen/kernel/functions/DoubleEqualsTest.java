package test.progen.kernel.functions;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import progen.kernel.functions.DoubleEquals;
import progen.kernel.functions.DoubleX;
import progen.kernel.functions.DoubleY;
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
public class DoubleEqualsTest{

	private DoubleEquals eq;
	
	@Before
	public void setUp(){
		eq=new DoubleEquals();
	}
	
	@Test
	public void equalsThanTest() {
		assertTrue(eq instanceof NonTerminal);
		assertTrue(eq.getArity()==2);
		assertTrue(eq.getReturnType().equals("boolean"));
		assertTrue(eq.getSignature().equals("boolean$$double$$double"));
		assertTrue(eq.getSymbol().equals("=="));
		
	}
	
	@Test
	public void evaluateTest(){
		List<Node> arguments=new ArrayList<Node>();
		UserProgram userProgram=null;
		HashMap<String, Object> returnAddr=null;
		
		Node a = new Node(new GrammarNonTerminalSymbol("Ax", eq.getReturnType().toString()));
		Node b = new Node(new GrammarNonTerminalSymbol("Ax", eq.getReturnType().toString()));
		
		DoubleX x =new DoubleX();
		x.setValue(1.0);
		GrammarTerminalSymbol one = new GrammarTerminalSymbol(x);
		DoubleY y =new DoubleY();
		y.setValue(2.0);
		GrammarTerminalSymbol two = new GrammarTerminalSymbol(y);
		
		arguments.add(a);
		arguments.add(b);
		
		//1==2?
		a.setFunction(one);
		b.setFunction(two);
		assertFalse((Boolean)eq.evaluate(arguments, userProgram, returnAddr));
		
		//2==1?
		a.setFunction(two);
		b.setFunction(one);
		assertFalse((Boolean)eq.evaluate(arguments, userProgram, returnAddr));
		
		//1==1?
		a.setFunction(one);
		b.setFunction(one);
		assertTrue((Boolean)eq.evaluate(arguments, userProgram, returnAddr));
		
		//2==2?
		a.setFunction(two);
		b.setFunction(two);
		assertTrue((Boolean)eq.evaluate(arguments, userProgram, returnAddr));
	}

}
