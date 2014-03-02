package test.progen.kernel.functions;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import progen.kernel.functions.IntN;
import progen.kernel.tree.Node;
import progen.userprogram.UserProgram;
import progen.kernel.functions.Terminal;

/**
 * 
 * @author jirsis
 * @since 2.0
 */
public class IntNTest{

	private IntN iN;
	
	@Before
	public void setUp(){
		iN=new IntN();
		iN.setValue(0);
	}
	
	@Test
	public void doubleXTest() {
		assertTrue(iN instanceof Terminal);
		assertTrue(iN.getArity()==0);
		assertTrue(iN.getReturnType().equals("int"));
		assertTrue(iN.getSignature().equals("int"));
		assertTrue(iN.getSymbol().equals("iN"));
		assertTrue((Integer)(iN.getValue())==0);
		
	}

	@Test
	public void evaluateTest(){
		List<Node> arguments=new ArrayList<Node>();
		UserProgram userProgram=null;
		HashMap<String, Object> returnAddr=null;
		assertTrue((Integer)iN.evaluate(arguments, userProgram, returnAddr)==0);
		
		iN.setValue(3);
		assertTrue((Integer)iN.evaluate(arguments, userProgram, returnAddr)==3);
	}
	
}
