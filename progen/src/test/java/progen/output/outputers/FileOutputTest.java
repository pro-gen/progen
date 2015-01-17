package progen.output.outputers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.io.File;
import java.io.PrintWriter;
import java.util.ResourceBundle;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import progen.context.ProGenContext;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ ProGenContext.class })
public class FileOutputTest {

  private FileOutput fileOutput;
  
  private File outputDir;
  
  
  @Before
  public void setUp() throws Exception {
    String outputDirTest = FileOutputTest.class.getClassLoader().getResource("progen/output/outputers/FileOutputTest.class").getFile();
    outputDir = new File(outputDirTest);
    mockProGenContext();
    fileOutput = new FileOutputDefault("default");
  }

  private void mockProGenContext() {
    mockStatic(ProGenContext.class);
    when(ProGenContext.getMandatoryProperty("progen.output.dir")).thenReturn(outputDir.getAbsolutePath());
    when(ProGenContext.getMandatoryProperty("progen.output.experiment")).thenReturn("");
  }
  
  @After
  public void tearDown(){
    for(String fileName: outputDir.getParentFile().list()){
      if(fileName.endsWith(".test")){
        File fileToDelete = new File(outputDir.getParentFile()+File.separator+fileName);
        fileToDelete.delete();
      }
    }
  }

  @Test
  public void testFileOutput() {
    fileOutput = new FileOutputDefault("fileOutput");
    assertNotNull(fileOutput.getLiterals());
    assertNull(fileOutput.getWriter());
  }

  @Test
  public void testClose() {
    fileOutput = new FileOutputDefault("close");
    fileOutput.init();
    PrintWriter writer = fileOutput.getWriter();
    writer.write("I write this, because the writer is open");
    assertFalse(writer.checkError());
    fileOutput.close();
    assertNotNull(fileOutput.getWriter());
    writer.write("I can not write this, because the writer is closed");
    assertTrue(writer.checkError());
  }

  @Test
  public void testInit() {
    fileOutput = new FileOutputDefault("initt");
    assertNotNull(fileOutput.getLiterals());
    assertNull(fileOutput.getWriter());
    fileOutput.init();
    assertNotNull(fileOutput.getWriter());
    boolean createdFile=true;
    for(String fileName : outputDir.getParentFile().list()){
      createdFile|=fileName.endsWith("FileOutputTest.class-init.test");
    }
    assertTrue(createdFile);
  }

  @Test
  public void testGetWriter() {
    PrintWriter writer = fileOutput.getWriter();
    assertNull(writer);
    fileOutput.init();
    writer = fileOutput.getWriter();
    assertNotNull(writer);
  }

  @Test
  public void testGetLiterals() {
    ResourceBundle literals = fileOutput.getLiterals();
    assertNotNull(literals);
    assertEquals("Raw", literals.getString("raw"));
  }
  
  private class FileOutputDefault extends FileOutput {
    
    public FileOutputDefault(String suffix){
      super(String.format("-%s.test", suffix), false);
    }
    
    @Override
    public void print() {
      //do nothing
    }
    
  }
}
