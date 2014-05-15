package progen.experimenter;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import progen.context.ProGenContext;

public class ExperimentFactoryTest {

  private Experimenter experimenter;

  @Before
  public void setUp() throws Exception {
    ProGenContext.makeInstance();
  }

  @After
  public void tearDown() throws Exception {
    experimenter.deleteDirectory(new File(ProGenContext.getMandatoryProperty("progen.output.dir")));
    ProGenContext.clearContext();
  }

  @Test@Ignore
  public void testMakeInstance() {
    experimenter = ExperimenterFactory.makeInstance();
    assertTrue(experimenter instanceof SimpleExperimenter);
  }

  @Test@Ignore
  public void testSimpleExperimenter() {
    ProGenContext.setProperty("progen.experimenter", "off");
    experimenter = ExperimenterFactory.makeInstance();
    assertTrue(experimenter instanceof SimpleExperimenter);
  }

  @Test@Ignore
  public void testMultipleExperimenter() {
    ProGenContext.setProperty("progen.experimenter", "on");
    experimenter = ExperimenterFactory.makeInstance();
    assertTrue(experimenter instanceof MultipleExperimenter);
  }

}
