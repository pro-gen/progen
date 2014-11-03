package progen.kernel.functions;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * @author jirsis
 * @since 2.0
 */
public class ERC01Test {

  private ERC01 erc;

  @Before
  public void setup() {
    erc = new ERC01();
  }

  @Test
  public void ERCTest() {
    double value = (Double) erc.getValue();
    assertTrue(value > 0.0);
    assertTrue(value < 1.0);
  }

  @Test
  public void evaluateTest() {
    double value = (Double) erc.getValue();
    erc.setValue(3.0);

    assertTrue(value == (Double) erc.getValue());
  }

  @Test
  public void cloneTest(){
    ERC otherERC = erc.clone();
    assertTrue(otherERC instanceof ERC01);
    assertNotEquals(erc, otherERC);    
  }
}
