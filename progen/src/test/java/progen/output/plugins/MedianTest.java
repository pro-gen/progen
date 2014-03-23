package progen.output.plugins;

import junit.framework.TestCase;
import progen.output.plugins.Median;

public class MedianTest extends TestCase {

  private Median median;

  protected void setUp() throws Exception {
    super.setUp();

    median = new Median();

    for (int i = 1; i <= 100; i = i + 2) {
      median.addValue(new Double(i));
    }

    for (int i = 2; i <= 100; i = i + 2) {
      median.addValue(new Double(i));
    }
  }

  public void testGetValue() {
    assertTrue((Double) median.getValue() == 50);
  }

  public void testName() {
    assertTrue(median.getName().equals("median"));
  }
}
