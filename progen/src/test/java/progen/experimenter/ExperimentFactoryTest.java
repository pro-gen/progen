package test.progen.experimenter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import progen.context.ProGenContext;
import progen.experimenter.Experimenter;
import progen.experimenter.ExperimenterFactory;
import progen.experimenter.MultipleExperimenter;
import progen.experimenter.SimpleExperimenter;

import java.io.File;

import static org.junit.Assert.assertTrue;

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

  @Test
  public void testMakeInstance() {
    experimenter = ExperimenterFactory.makeInstance();
    assertTrue(experimenter instanceof SimpleExperimenter);
  }

  @Test
  public void testSimpleExperimenter() {
    ProGenContext.setProperty("progen.experimenter", "off");
    experimenter = ExperimenterFactory.makeInstance();
    assertTrue(experimenter instanceof SimpleExperimenter);
  }

  @Test
  public void testMultipleExperimenter() {
    ProGenContext.setProperty("progen.experimenter", "on");
    experimenter = ExperimenterFactory.makeInstance();
    assertTrue(experimenter instanceof MultipleExperimenter);
  }

}
