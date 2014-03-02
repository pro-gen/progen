package test.progen.kernel.functions;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import progen.kernel.functions.ERC01;

/**
 * 
 * @author jirsis
 * @since 2.0
 */
public class ERC01Test{

	private ERC01 erc;
	
	@Before
	public void setup(){
		erc=new ERC01();
	}
	
	@Test
	public void ERCTest() {
		double value = (Double)erc.getValue();
		assertTrue(value>0.0);
		assertTrue(value<1.0);
	}

	@Test
	public void evaluateTest(){
		double value = (Double)erc.getValue();
		erc.setValue(3.0);
		
		assertTrue(value==(Double)erc.getValue());
	}
	
}
