package progen.kernel.functions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class Bit32ERCTest {
  
  private ERC erc;
  
  @Before
  public void setup(){
    erc = new Bit32ERC();
  }

  @Test
  public void testDefineERC() {
    int firstValue = (int)erc.defineERC();
    int secondValue = (int)erc.defineERC();
    assertNotEquals(firstValue, secondValue);
    
  }

  @Test
  public void testPrintERC() {
    String firstPrinted = erc.printERC();
    String secondPrinted = erc.printERC();
    assertEquals(firstPrinted, secondPrinted);
    assertTrue(firstPrinted.startsWith("0x"));
    assertEquals(10, firstPrinted.length());
  }

  @Test
  public void testClone() {
    ERC other = erc.clone();
    assertNotEquals(other, erc);
  }

  @Test
  public void testBit32ERC() {
    erc = new Bit32ERC();
    assertEquals("int", erc.getSignature());
    assertTrue(erc.getValue() instanceof Integer);
    assertEquals(0, erc.getArity());
    assertEquals("int", erc.getReturnType());
  }

}
