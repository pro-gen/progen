package progen.kernel.functions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;

public class ERCTest {

  private ERC erc;
  
  @Before
  public void setup(){
    erc=new ERCSample("ercsample");
  }
  
  @Test
  public void newERCTest() {
    erc = new ERCSample("ERC");
    assertEquals("ERC", erc.getSignature());
    assertEquals("value", erc.getValue());
    assertEquals("", erc.getSymbol());
    assertEquals(0, erc.getArity());
    assertEquals("ERC", erc.getReturnType());
  }
  
  @Test
  public void evaluateTest(){
    Object value = erc.evaluate(null, null, null);
    assertSame("value", value);
  }
  
  @Test
  public void setValueTest(){
    Object firstValue = erc.getValue();
    erc.setValue("other value");
    Object secondValue = erc.getValue();
    assertEquals(firstValue, secondValue);
    assertEquals("value", firstValue);
    assertEquals("value", secondValue);
  }

  private class ERCSample extends ERC {
    private static final long serialVersionUID = 8530720375792237151L;

    public ERCSample(String signature) {
      super(signature);
    }

    @Override
    protected Object defineERC() {
      return "value";
    }

    @Override
    protected String printERC() {
      return "";
    }

    @Override
    public ERC clone() {
      return null;
    }

  }

}
