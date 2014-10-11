package progen;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import progen.context.MissingContextFileException;
import progen.context.ProGenContext;
import progen.kernel.population.Individual;
import progen.output.outputers.OutputStore;
import progen.roles.ProGenFactory;
import progen.roles.standalone.ClientLocal;
import progen.roles.standalone.StandaloneFactory;
import progen.userprogram.UserProgram;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ ProGenContext.class, UserProgram.class, ProGenFactory.class, OutputStore.class })
public class ProGenTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void testNoMasterFile() {
    String args[] = new String[0];
    exception.expect(ProGenException.class);
    exception.expectMessage("'master-file' is mandatory to execute.");
    ProGen.main(args);
    fail("exception must be thrown");
  }

  @Test
  public void testMasterFile() throws Exception {
    ByteArrayOutputStream systemOut = mockSystemOut();
    
    String args[] = { "master-file.txt" };
    mockStatic(ProGenContext.class, UserProgram.class, ProGenFactory.class, OutputStore.class);
    
    ProGenContext context = mock(ProGenContext.class);
    OutputStore outputStore = mock(OutputStore.class);
    StandaloneFactory factory = mock(StandaloneFactory.class);
    ClientLocal clientLocal = mock(ClientLocal.class);

    when(ProGenContext.makeInstance(any(String.class))).thenReturn(context);
    when(ProGenContext.getMandatoryProperty("progen.welcome")).thenReturn("ProGen exec TEST");
    doNothing().when(ProGenContext.class, "loadExtraConfiguration");
    when(ProGenFactory.makeInstance()).thenReturn(factory);
    when(factory.makeExecutionRole()).thenReturn(clientLocal);
    when(OutputStore.makeInstance()).thenReturn(outputStore);
    when(UserProgram.getUserProgram()).thenReturn(new UserProgramExample());

    ProGen.main(args);
    String output = systemOut.toString("UTF-8");
    assertTrue(output.startsWith("ProGen exec TEST\n\nEXECUTION TIME: "));
    
  }

  @Test
  public void testMissingContextFileException() throws UnsupportedEncodingException{
    ByteArrayOutputStream systemErr = mockSystemErr();

    String args [] = {"master-file.txt"};
    mockStatic(ProGenContext.class);
    
    when(ProGenContext.makeInstance(any(String.class))).thenThrow(new MissingContextFileException()).thenReturn(null);
    ProGen.main(args);
    
    assertEquals("File not found in the configuration files.(File not found.)\n", systemErr.toString("UTF-8"));
  }
  
  private ByteArrayOutputStream mockSystemOut() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    System.setOut(printStream);
    return outputStream;
  }
  
  private ByteArrayOutputStream mockSystemErr() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    System.setErr(printStream);
    return outputStream;
  }
  
  private class UserProgramExample extends UserProgram{

    @Override
    public double fitness(Individual individual) {
      return 1;
    }
    
  }

}
