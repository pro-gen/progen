package test.progen.kernel.functions;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import progen.kernel.functions.True;
import progen.kernel.tree.Node;
import progen.userprogram.UserProgram;
import progen.kernel.functions.Terminal;

/**
 * 
 * @author jirsis
 * @since 2.0
 */
public class TrueTest{

	private True trueFunction;
	
	@Before
	public void setUp(){
		trueFunction=new True();
	}
	
	@Test
	public void trueTest() {
		assertTrue(trueFunction instanceof Terminal);
		assertTrue(trueFunction.getArity()==0);
		assertTrue(trueFunction.getReturnType().equals("boolean"));
		assertTrue(trueFunction.getSignature().equals("boolean"));
		assertTrue(trueFunction.getSymbol().equals("TRUE"));
		
	}

	@Test
	public void evaluateTest(){
		List<Node> arguments=new ArrayList<Node>();
		UserProgram userProgram=null;
		HashMap<String, Object> returnAddr=null;
		assertTrue((Boolean)trueFunction.evaluate(arguments, userProgram, returnAddr));
	}
	
}
