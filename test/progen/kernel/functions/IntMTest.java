package test.progen.kernel.functions;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import progen.kernel.functions.IntM;
import progen.kernel.tree.Node;
import progen.userprogram.UserProgram;
import progen.kernel.functions.Terminal;

/**
 * 
 * @author jirsis
 * @since 2.0
 */
public class IntMTest{

	private IntM iM;
	
	@Before
	public void setUp(){
		iM=new IntM();
		iM.setValue(0);
	}
	
	@Test
	public void doubleXTest() {
		assertTrue(iM instanceof Terminal);
		assertTrue(iM.getArity()==0);
		assertTrue(iM.getReturnType().equals("int"));
		assertTrue(iM.getSignature().equals("int"));
		assertTrue(iM.getSymbol().equals("iM"));
		assertTrue((Integer)(iM.getValue())==0);
		
	}

	@Test
	public void evaluateTest(){
		List<Node> arguments=new ArrayList<Node>();
		UserProgram userProgram=null;
		HashMap<String, Object> returnAddr=null;
		assertTrue((Integer)iM.evaluate(arguments, userProgram, returnAddr)==0);
		
		iM.setValue(3);
		assertTrue((Integer)iM.evaluate(arguments, userProgram, returnAddr)==3);
	}
	
}
