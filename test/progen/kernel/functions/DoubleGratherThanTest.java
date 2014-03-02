package test.progen.kernel.functions;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import progen.kernel.functions.DoubleGratherThan;
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
public class DoubleGratherThanTest{

	private DoubleGratherThan gt;
	
	@Before
	public void setUp(){
		gt=new DoubleGratherThan();
	}
	
	@Test
	public void gratherThanTest() {
		assertTrue(gt instanceof NonTerminal);
		assertTrue(gt.getArity()==2);
		assertTrue(gt.getReturnType().equals("boolean"));
		assertTrue(gt.getSignature().equals("boolean$$double$$double"));
		assertTrue(gt.getSymbol().equals(">"));
		
	}
	
	@Test
	public void evaluateTest(){
		List<Node> arguments=new ArrayList<Node>();
		UserProgram userProgram=null;
		HashMap<String, Object> returnAddr=null;
		
		Node a = new Node(new GrammarNonTerminalSymbol("Ax", gt.getReturnType().toString()));
		Node b = new Node(new GrammarNonTerminalSymbol("Ax", gt.getReturnType().toString()));
		
		DoubleX x =new DoubleX();
		x.setValue(1.0);
		GrammarTerminalSymbol one = new GrammarTerminalSymbol(x);
		DoubleY y =new DoubleY();
		y.setValue(2.0);
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
