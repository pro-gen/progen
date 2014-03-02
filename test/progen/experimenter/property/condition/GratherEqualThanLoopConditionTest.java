package test.progen.experimenter.property.condition;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import progen.experimenter.property.condition.GratherEqualThanLoopCondition;

public class GratherEqualThanLoopConditionTest {

	private GratherEqualThanLoopCondition condition;
	
	@Before
	public void setUp() throws Exception {
		condition=new GratherEqualThanLoopCondition(-1);
	}

	@Test
	public void testEnd() {
		int i;
		for(i=100;i>0;i--){
			assertFalse(i+"", condition.end(i, 0));
		}
		assertTrue(condition.end(i, 0));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFailZero(){
		new GratherEqualThanLoopCondition(0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFailPositive(){
		new GratherEqualThanLoopCondition(1);
	}

}
