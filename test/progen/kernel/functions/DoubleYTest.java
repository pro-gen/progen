package test.progen.kernel.functions;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import progen.kernel.functions.DoubleY;
import progen.kernel.tree.Node;
import progen.userprogram.UserProgram;
import progen.kernel.functions.Terminal;

/**
 * 
 * @author jirsis
 * @since 2.0
 */
public class DoubleYTest{

	private DoubleY dY;
	
	@Before
	public void setUp(){
		dY=new DoubleY();
		dY.setValue(0.0);
	}
	
	@Test
	public void doubleXTest() {
		assertTrue(dY instanceof Terminal);
		assertTrue(dY.getArity()==0);
		assertTrue(dY.getReturnType().equals("double"));
		assertTrue(dY.getSignature().equals("double"));
		assertTrue(dY.getSymbol().equals("dY"));
		assertTrue((Double)(dY.getValue())==0);
		
	}

	@Test
	public void evaluateTest(){
		List<Node> arguments=new ArrayList<Node>();
		UserProgram userProgram=null;
		HashMap<String, Object> returnAddr=null;
		assertTrue((Double)dY.evaluate(arguments, userProgram, returnAddr)==0.0);
		
		dY.setValue(Math.PI);
		assertTrue((Double)dY.evaluate(arguments, userProgram, returnAddr)==Math.PI);
	}
	
}
