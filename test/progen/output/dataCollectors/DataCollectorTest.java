package test.progen.output.dataCollectors;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import progen.context.ProGenContext;
import progen.output.dataCollectors.DataCollector;
import progen.output.plugins.Plugin;

public class DataCollectorTest{

	private DataCollector data;
	
	@Before
	public void setUp()throws Exception {
		ProGenContext.makeInstance();
		ProGenContext.setProperty("progen.datacollector2.name", "dataCollector");
		
		data=new DataCollector("progen.datacollector2");
	}
	
	@Test
	public void testAddValue() {
		for(int i=0;i<10;i++){
			data.addValue("statistical", new Double(i));
		}
		Plugin best=data.getPlugin("best");
		Plugin worst=data.getPlugin("worst");
		Plugin mean=data.getPlugin("mean");
		Plugin median=data.getPlugin("median");
		assertTrue((Double)(best.getValue()) == 0.0);
		assertTrue((Double)(worst.getValue()) == 9.0);
		assertTrue((Double)(mean.getValue()) == 4.5);
		assertTrue((Double)(median.getValue()) == 4.0);
	}

}
