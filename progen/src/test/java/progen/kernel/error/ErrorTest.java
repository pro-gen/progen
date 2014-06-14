package progen.kernel.error;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Test;

public class ErrorTest {

  @After
  public void tearDown(){
    Error.destroyMe();
  }

  @Test
  public void testGet() {
    String errorDesc = Error.get(0);
    assertNotNull(errorDesc);
    assertEquals("'master-file' is mandatory to execute.", errorDesc);
  }

  @Test
  public void testMakeInstance(){
    Error.makeInstance();
    assertTrue(true);
  }

  @Test
  public void testPrintln(){
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    PrintStream printer = new PrintStream(output);
    System.setErr(printer);
    Error.println(0);
    assertEquals("ERROR: 'master-file' is mandatory to execute.\n", new String(output.toByteArray()));
  }
}
