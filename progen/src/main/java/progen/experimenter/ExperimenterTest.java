package progen.experimenter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import progen.context.ProGenContext;
@RunWith(PowerMockRunner.class)
@PrepareForTest({ ProGenContext.class })

public class ExperimenterTest {
  
  private Experimenter experimenter;
  

  @Before
  public void setUp() throws Exception {
    mockProGenContext();
    experimenter=new ExperimenterDefault();
  }
  
  @After
  public void tearDown(){
    experimenter.deleteDirectory(new File("target/output/ExperimenterTest"));
  }

  private void mockProGenContext() {
      mockStatic(ProGenContext.class);
      when(ProGenContext.getMandatoryProperty("progen.experiment.name")).thenReturn("target");
      when(ProGenContext.getOptionalProperty(eq("progen.output.dir"), any(String.class))).thenReturn("target/output/ExperimenterTest/");
      when(ProGenContext.getMandatoryProperty(eq("progen.output.dir"))).thenReturn("target/output/ExperimenterTest/");
      when(ProGenContext.getMandatoryProperty("progen.masterfile")).thenReturn("src/test/resources/userexperiment-test.txt");
      File srcDir = new File ("src/test/resources");
      when(ProGenContext.getMandatoryProperty("progen.experiment.file.absolute")).thenReturn(srcDir.getAbsolutePath()+"/userexperiment-test.txt");
      when(ProGenContext.getOptionalProperty(eq("progen.optional.files"), any(String.class))).thenReturn("");
  }

  @Test
  public void testExperimenter() {
    File copied =new File("target/output/ExperimenterTest/userexperiment-test.txt"); 
    assertTrue(copied.exists());
    assertEquals(18, copied.length());
  }

  @Test
  public void testDeleteDirectory() {
    File dirToDelete = new File("target/output/ExperimenterTest/toDelete");
    dirToDelete.mkdirs();
    assertTrue(dirToDelete.exists());
    experimenter.deleteDirectory(dirToDelete);
    assertFalse(dirToDelete.exists());
  }

  @Test
  public void testGenerateOutputs() {
    File outputs = new File("target/output/ExperimenterTest/testGenerateOutputs");
    assertFalse(outputs.exists());
    experimenter.generateOutputs();
    assertTrue(outputs.exists());
    assertTrue(new File(outputs.getAbsolutePath()+"/result.log").exists());
  }
  
  
  private class ExperimenterDefault extends Experimenter{

    @Override
    public boolean isDone() {
      return true;
    }

    @Override
    public void defineValues() {
      // do nothing
    }

    @Override
    public void updateValues() {
      // do nothing      
    }

    @Override
    public String defineExperimentDir() {
      return "testGenerateOutputs";
    }

    @Override
    public void generateResults() {
      File result = new File("target/output/ExperimenterTest/"+defineExperimentDir()+"/result.log");
      try {
        result.createNewFile();
      } catch (IOException e) {
       fail("IOException ocurred when generateResults");
      }
      
    }

    @Override
    public String finishMessage() {
      return "FINISH MESSAGE";
    }
    
  }

}
