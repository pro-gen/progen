package test.progen.experimenter.property;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import progen.context.ProGenContext;
import progen.experimenter.property.LoopProperty;

public class LoopPropertyTest {
	private LoopProperty loopNormal;
	private LoopProperty loopPercent;
	
	@Before
	public void setUp() throws Exception {
		ProGenContext.makeInstance();
		ProGenContext.setProperty("progen.experimenter.loop-normal", "10;200;1");
		ProGenContext.setProperty("progen.experimenter.loop-percent", "2;202;2%");
		
		ProGenContext.setProperty("fail.progen.loop-exceded", "0;2;1;1");
		ProGenContext.setProperty("fail.progen.loop-format-exception", "a;2;4");
		
		loopNormal=new LoopProperty("progen.experimenter.loop-normal");
		loopPercent=new LoopProperty("progen.experimenter.loop-percent");
	}
	
	@After
	public void tearDown() throws Exception{
		ProGenContext.clearContext();
	}

	@Test
	public void testLoopProperty() {
		loopNormal=new LoopProperty("progen.experimenter.loop-normal");
		assertTrue(loopNormal.hasNext());
		assertTrue(loopNormal.getLabel().equals("progen.loop-normal"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testErrorLoop(){
		loopNormal=new LoopProperty("fail.progen.loop-exceded");
	}

	@Test(expected=IllegalArgumentException.class)
	public void testErrorFormatLoop(){
		loopNormal=new LoopProperty("fail.progen.loop-format-exception");
	}
	
	@Test
	public void testGetLabel() {
		assertTrue(loopNormal.getLabel().equals("progen.loop-normal"));
		assertTrue(loopPercent.getLabel().equals("progen.loop-percent"));
	}

	@Test
	public void testGetValue() {
		assertTrue(loopNormal.getValue().equals("10.0"));
		assertTrue(loopPercent.getValue().equals("200.0%"));
		
		loopNormal.nextValue();
		loopNormal.nextValue();
		loopNormal.nextValue();
		assertTrue(loopNormal.getValue().equals("13.0"));
		
		loopPercent.nextValue();
		loopPercent.nextValue();
		loopPercent.nextValue();
		assertTrue(loopPercent.getValue().equals("1400.0%"));
	}

	@Test
	public void testHasNext() {
		for(int i=10;i<200;i++){
			assertTrue(loopNormal.hasNext());
			loopNormal.nextValue();
		}
		assertFalse(loopNormal.hasNext());
		
		for(int i=0;i<200;i+=4){
			assertTrue(loopPercent.hasNext());
			loopPercent.nextValue();
		}
		assertFalse(loopPercent.hasNext());
	}

	@Test
	public void testNextValue() {
		for(int i=10;i<199;i++){
			assertTrue(loopNormal.nextValue().equals((i+1)+".0"));
		}
		loopNormal.nextValue();
		assertTrue(loopNormal.getValue().equals("200.0"));
		
		for(int i=2;i<198;i+=4){
			assertTrue(loopPercent.nextValue().equals((i+4)+"00.0%"));
		}
		loopPercent.nextValue();
		assertTrue(loopPercent.getValue().equals("20200.0%"));
	}

	@Test
	public void testReset() {
		int rnd = (int)(Math.random()*100);
		for(int i=0;i<rnd;i++){
			loopNormal.nextValue();
			loopPercent.nextValue();
		}
		loopNormal.reset();
		loopPercent.reset();
		assertTrue(loopNormal.getValue().equals("10.0"));
		assertTrue(loopPercent.getValue().equals("200.0%"));
	}
}
