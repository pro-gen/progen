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
import progen.kernel.grammar.UndefinedFunctionSetException;
import progen.kernel.population.Individual;
import progen.output.outputers.OutputStore;
import progen.roles.ProGenFactory;
import progen.roles.standalone.ClientLocal;
import progen.roles.standalone.StandaloneFactory;
import progen.userprogram.UserProgram;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ ProGenContext.class, UserProgram.class, ProGenFactory.class, OutputStore.class })
public class ProGenTest {

  private static final String UTF_8_ENCODING = "UTF-8";
  private static final String MASTER_FILE_FILENAME = "master-file.txt";
  @Rule
  public ExpectedException exception = ExpectedException.none();
  

  @Test
  public void testNoMasterFile() {
    final String [] args = new String[0];
    exception.expect(ProGenException.class);
    exception.expectMessage("'master-file' is mandatory to execute.");
    ProGen.main(args);
    fail("exception must be thrown");
  }

  @Test
  public void testMasterFile() throws Exception {
    final ByteArrayOutputStream systemOut = mockSystemOut();
    
    final String [] args = { MASTER_FILE_FILENAME };
    mockStatic(ProGenContext.class, UserProgram.class, ProGenFactory.class, OutputStore.class);
    
    final ProGenContext context = mock(ProGenContext.class);
    final OutputStore outputStore = mock(OutputStore.class);
    final StandaloneFactory factory = mock(StandaloneFactory.class);
    final ClientLocal clientLocal = mock(ClientLocal.class);

    when(ProGenContext.makeInstance(any(String.class))).thenReturn(context);
    when(ProGenContext.getMandatoryProperty("progen.welcome")).thenReturn("ProGen exec TEST");
    doNothing().when(ProGenContext.class, "loadExtraConfiguration");
    when(ProGenFactory.makeInstance()).thenReturn(factory);
    when(factory.makeExecutionRole()).thenReturn(clientLocal);
    when(OutputStore.makeInstance()).thenReturn(outputStore);
    when(UserProgram.getUserProgram()).thenReturn(new UserProgramExample());

    ProGen.main(args);
    final String output = systemOut.toString(UTF_8_ENCODING);
    assertTrue(output.startsWith("ProGen exec TEST\n\nEXECUTION TIME: "));
    
  }

  @Test
  public void testMissingContextFileException() throws UnsupportedEncodingException{
    final ByteArrayOutputStream systemErr = mockProGenContextException(new MissingContextFileException());
    ProGen.main(new String []{MASTER_FILE_FILENAME});
    assertEquals("File not found in the configuration files.(File not found.)\n", systemErr.toString(UTF_8_ENCODING));
  }
  
  @Test
  public void testUndefinedFunctionSetException() throws UnsupportedEncodingException{
    final ByteArrayOutputStream systemErr = mockProGenContextException(new UndefinedFunctionSetException("message"));
    ProGen.main(new String []{MASTER_FILE_FILENAME});
    assertEquals("message\n", systemErr.toString(UTF_8_ENCODING));
  }
  
  @Test
  public void testNumberFormatException() throws UnsupportedEncodingException{
    final ByteArrayOutputStream systemErr = mockProGenContextException(new NumberFormatException());
    ProGen.main(new String []{MASTER_FILE_FILENAME});
    assertEquals("null\n", systemErr.toString(UTF_8_ENCODING));
  }
  
  private ByteArrayOutputStream mockProGenContextException(Exception e) {
    mockStatic(ProGenContext.class);
    final ByteArrayOutputStream systemErr = mockSystemErr();
    when(ProGenContext.makeInstance(any(String.class))).thenThrow(e).thenReturn(null);
    return systemErr;
  }

  private ByteArrayOutputStream mockSystemOut() {
    final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    final PrintStream printStream = new PrintStream(outputStream);
    System.setOut(printStream);
    return outputStream;
  }
  
  private ByteArrayOutputStream mockSystemErr() {
    final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    final PrintStream printStream = new PrintStream(outputStream);
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
