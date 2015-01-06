package progen.output.outputers;

import org.junit.Before;
import org.junit.Test;

/**
 * This test is only for coverage test, because all methods are empty
 * @author jirsis
 *
 */
public class NullOutputerTest {
  
  private NullOutputer outputer;

  @Before
  public void setUp() throws Exception {
    outputer = new NullOutputer();
  }

  @Test
  public void testInit() {
    outputer.init();
  }
  
  @Test
  public void testClose() {
    outputer.close();
  }
  
  @Test
  public void testPrint() {
    outputer.print();
  }

}
