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
  
  private static final String BIT32_ERC_PATTERN = "0x[\\da-f]{8}";
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
    assertTrue(firstPrinted, Pattern.matches(BIT32_ERC_PATTERN, firstPrinted));
    assertTrue(secondPrinted, Pattern.matches(BIT32_ERC_PATTERN, secondPrinted));
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
    final Bit32ERC shortERC = spy(new Bit32ERC());
    when(shortERC.getValue()).thenReturn(1);
    final String printedValue = shortERC.printERC();
    assertTrue(printedValue, Pattern.matches(BIT32_ERC_PATTERN, printedValue));
    
  }
}
