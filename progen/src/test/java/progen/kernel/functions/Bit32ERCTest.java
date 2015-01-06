package progen.kernel.functions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

public class Bit32ERCTest {
  
  private Bit32ERC erc;
  
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
    assertTrue(firstPrinted, firstPrinted.startsWith("0x"));
    assertEquals(firstPrinted, 10, firstPrinted.length());
    assertTrue(firstPrinted, Pattern.matches("0x[\\da-f]{8}", firstPrinted));
    assertTrue(secondPrinted, Pattern.matches("0x[\\da-f]{8}", secondPrinted));
  }

  @Test
  public void testClone() {
    ERC other = erc.clone();
    assertNotEquals(other, erc);
    assertTrue(other instanceof Bit32ERC);
  }

  @Test
  public void testBit32ERC() {
    erc = new Bit32ERC();
    assertEquals("int", erc.getSignature());
    assertTrue(erc.getValue() instanceof Integer);
    assertEquals(0, erc.getArity());
    assertEquals("int", erc.getReturnType());
  }

  @Test
  public void printShortValue(){
    Bit32ERC shortERC = spy(new Bit32ERC());
    when(shortERC.getValue()).thenReturn(1);
    final String printedValue = shortERC.printERC();
    assertTrue(printedValue, Pattern.matches("0x[\\da-f]{8}", printedValue));
    
  }
}
