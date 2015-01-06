package progen.output.outputers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Test;

public class ConsoleOutputTest {
  
  private ConsoleOutput output;

  @Before
  public void setUp() throws Exception {
    output=new ConsoleOuputDefault();
  }

  @Test
  public void testConsoleOutput() {
    output = new ConsoleOuputDefault();
    assertNotNull(output);
  }

  @Test
  public void testClose() {
    output.close();
  }

  @Test
  public void testInit() {
    output.init();
  }

  @Test
  public void testGetLiterals() {
    ResourceBundle literals = output.getLiterals();
    assertNotNull(literals);
    assertEquals("raw", "Raw", literals.getString("raw"));
  }

  private class ConsoleOuputDefault extends ConsoleOutput{

    @Override
    public void print() {
      //do nothing
    }
  }
}
