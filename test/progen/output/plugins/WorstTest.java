package test.progen.output.plugins;

import java.util.Random;

import progen.output.plugins.Worst;
import junit.framework.TestCase;

public class WorstTest extends TestCase {

	private Worst worst;

	private Integer elPeor;

	protected void setUp() throws Exception {
		super.setUp();

		worst = new Worst();
		elPeor = new Integer(1000);

		worst.addValue(Integer.valueOf(elPeor));
		for (int i = 0; i < 100; i++) {
			worst.addValue(i);
		}
	}

	public void testAddValue() {
		for (int i = 0; i < 30; i++) {
			Integer integer = Integer.valueOf(new Random().nextInt()* 100);
			worst.addValue(integer);
		}
	}

	public void testGetValue() {
		Integer peor = (Integer) worst.getValue();
		assertTrue(peor.intValue() == elPeor);
	}

	public void testGetValueFail() {
		Integer peor = (Integer) worst.getValue();
		assertFalse(peor.intValue() == 0);
	}

	public void testGetName() {
		assertEquals(worst.getName(), "worst");
	}

}
