package test.progen.experimenter.property;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import progen.context.ProGenContext;
import progen.experimenter.property.EnumerationProperty;
import progen.experimenter.property.IlegalPropertySeparatorException;
import progen.experimenter.property.LoopProperty;
import progen.experimenter.property.PropertyFactory;

public class PropertyFactoryTest {

	@Before
	public void setUp() throws Exception {
		ProGenContext.makeInstance();
		ProGenContext.setProperty("progen.experimenter.loop-normal", "10;200;1");
		ProGenContext.setProperty("progen.experimenter.loop-percent", "2;202;2%");
		ProGenContext.setProperty("progen.experimenter.loop-format-exception", "a;2;4");
		ProGenContext.setProperty("progen.experimenter.enumeration", "1|2|3|4|a|b|c");
		ProGenContext.setProperty("fail.progen.experimenter", "a,b,c,d");
	}
	
	@After
	public void tearDown() throws Exception{
		ProGenContext.clearContext();
	}

	@Test(expected=IlegalPropertySeparatorException.class)
	public void testMakeInstance() {
		assertTrue(PropertyFactory.makeInstance("progen.experimenter.loop-normal") instanceof LoopProperty);
		assertTrue(PropertyFactory.makeInstance("progen.experimenter.loop-percent") instanceof LoopProperty);
		assertTrue(PropertyFactory.makeInstance("progen.experimenter.enumeration") instanceof EnumerationProperty);
		PropertyFactory.makeInstance("fail.progen.experimenter");
	}

}
