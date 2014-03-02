package test.progen.kernel.functions;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import progen.kernel.functions.IntGratherThan;
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
public class IntGratherThanTest{

	private IntGratherThan gt;
	
	@Before
	public void setUp(){
		gt=new IntGratherThan();
	}
	
	@Test
	public void gratherThanTest() {
		assertTrue(gt instanceof NonTerminal);
		assertEquals(2, gt.getArity());
		assertEquals("boolean", gt.getReturnType());
		assertEquals("boolean$$int$$int", gt.getSignature());
		assertEquals(">", gt.getSymbol());
		
	}
	
	@Test
	public void evaluateTest(){
		List<Node> arguments=new ArrayList<Node>();
		UserProgram userProgram=null;
		HashMap<String, Object> returnAddr=null;
		
		Node a = new Node(new GrammarNonTerminalSymbol("Ax", gt.getReturnType().toString()));
		Node b = new Node(new GrammarNonTerminalSymbol("Ax", gt.getReturnType().toString()));
		
		IntN x =new IntN();
		x.setValue(1);
		GrammarTerminalSymbol one = new GrammarTerminalSymbol(x);
		IntM y =new IntM();
		y.setValue(2);
		GrammarTerminalSymbol two = new GrammarTerminalSymbol(y);
		
		arguments.add(a);
		arguments.add(b);
		
		//1>2?
		a.setFunction(one);
		b.setFunction(two);
		assertFalse((Boolean)gt.evaluate(arguments, userProgram, returnAddr));
		
		//2>1?
		a.setFunction(two);
		b.setFunction(one);
		assertTrue((Boolean)gt.evaluate(arguments, userProgram, returnAddr));
		
		//1>1?
		a.setFunction(one);
		b.setFunction(one);
		assertFalse((Boolean)gt.evaluate(arguments, userProgram, returnAddr));
		
		//2>2?
		a.setFunction(two);
		b.setFunction(two);
		assertFalse((Boolean)gt.evaluate(arguments, userProgram, returnAddr));
	}

}
