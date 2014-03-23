package progen.experimenter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import progen.context.ProGenContext;
import progen.experimenter.Experimenter;
import progen.experimenter.ExperimenterFactory;
import progen.experimenter.MultipleExperimenter;
import progen.kernel.population.Individual;
import progen.kernel.population.Population;
import progen.output.HistoricalData;
import progen.userprogram.UserProgram;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

public class MultipleExperimenterTest {

  private Experimenter experimenter;

  @Before
  public void setUp() {
    ProGenContext.makeInstance("test/progen/experimenter/multiple/master.txt");
    ProGenContext.setProperty("progen.outputers", "StandardFile, BestIndividualFile");
    experimenter = new MultipleExperimenter();
  }

  @After
  public void tearDown() {
    experimenter.deleteDirectory(new File(ProGenContext.getMandatoryProperty("progen.output.dir")));
    ProGenContext.clearContext();
  }

  /**
   * Comprueba que al inicializar el experimenter, se hayan creado las carpetas
   * correspondientes, así como se hayan copiado los ficheros mínimos.
   */
  @Test
  public void testConstructExperimenterDefault() {
    String outputDir = ProGenContext.getMandatoryProperty("progen.output.dir");
    String masterFile = ProGenContext.getMandatoryProperty("progen.masterfile");
    String experimentFile = ProGenContext.getMandatoryProperty("progen.experiment.name") + ".txt";

    masterFile = masterFile.substring(masterFile.lastIndexOf(File.separator) + 1);

    checkDir(new File(outputDir));
    checkFile(new File(outputDir + File.separator + masterFile));
    checkFile(new File(outputDir + File.separator + experimentFile));
  }

  private void checkDir(File dir) {
    assertTrue(dir.exists());
    assertTrue(dir.isDirectory());
    assertTrue(dir.canWrite());
  }

  private void checkFile(File file) {
    assertTrue(file.exists());
    assertTrue(file.isFile());
    assertTrue(file.canWrite());
  }

  @Test
  public void testConstructExperimenterUserDefinedRelativeOutputPath() {
    ProGenContext.setProperty("progen.output.dir", "../relativeOutputMultiple");
    experimenter = new MultipleExperimenter();
    testConstructExperimenterDefault();
  }

  @Test
  public void testConstructExperimenterUserDefinedAbsoluteOutputPath() {
    String outputPath = System.getProperty("java.io.tmpdir") + File.separator + "ProGenOut";
    ProGenContext.setProperty("progen.output.dir", outputPath);
    experimenter = new MultipleExperimenter();
    testConstructExperimenterDefault();
  }

  @Test
  public void testMakeMultipleExperimenterON() {
    Experimenter multiple = ExperimenterFactory.makeInstance();
    assertTrue("No es una instancia de MultipleExperimenter",
        multiple instanceof MultipleExperimenter);
  }

  @Test
  public void testMakeMultipleExperimenterOFF() {
    ProGenContext.clearContext();
    ProGenContext.makeInstance("test/progen/experimenter/simple/master.txt");
    ProGenContext.setProperty("progen.experimenter", "off");
    Experimenter multiple = ExperimenterFactory.makeInstance();
    assertFalse("No es una instancia de MultipleExperimenter",
        multiple instanceof MultipleExperimenter);
  }

  @Test
  public void testDefineValue() {
    List<String> propertiesBefore = ProGenContext.getFamilyOptions("");
    List<String> propertiesAfter;
    experimenter.defineValues();
    propertiesAfter = ProGenContext.getFamilyOptions("");
    assertEquals(propertiesBefore.size() + 2, propertiesAfter.size());
  }

  @Test
  public void testIsDoneSimple() {
    testIsDone(5 * 3, 135.0);
  }

  @Test
  public void testIsDoneRepetitions() {
    int repetitions = 2;
    ProGenContext.setProperty("progen.repetitions.experimenter", repetitions + "");
    experimenter = new MultipleExperimenter();

    testIsDone(5 * 3 * repetitions, 135.0 * repetitions);
  }

  private void testIsDone(int totalIterations, double resultExpected) {
    int iterations = 0;
    double resultCalculated = 0.0;
    double list = 0.0;
    double loop = 0.0;

    HistoricalData historical = HistoricalData.makeInstance();
    UserProgram userProgram = UserProgram.getUserProgram();
    Population population = new Population();
    Individual individual = population.getIndividual(0);
    individual.calculate(userProgram);
    while (!experimenter.isDone()) {
      iterations++;

      experimenter.defineValues();

      list = ProGenContext.getOptionalProperty("progen.list", list);
      loop = ProGenContext.getOptionalProperty("progen.loop", loop);

      resultCalculated += list * loop;

      historical.getDataCollectorExperiment("ExperimentIndividualData").addValue("statistical", individual);
      historical.getCurrentDataCollector("ExperimentIndividualData").addValue("statistical", individual);
      historical.getCurrentDataCollector("PopulationData").addValue("individualMean", individual);

      experimenter.generateOutputs();
      experimenter.updateValues();

      historical.newExperiment();

    }

    assertEquals(iterations, totalIterations);
    assertEquals(resultCalculated, resultExpected, 0.1);

    File outputDir = new File(ProGenContext.getMandatoryProperty("progen.output.dir"));
    int totalDir = 0;
    for (File dir : outputDir.listFiles()) {
      if (dir.isDirectory()) {
        totalDir++;
      }
    }
    assertTrue("totalDir!=totalIterations; " + totalDir + "!=" + totalIterations,
        totalDir == totalIterations);

  }

  @Test
  public void gphashTest() {
    ProGenContext.setProperty("progen.experimenter.loop", "1|2");
    ProGenContext.setProperty("progen.experimenter.list", "a|b");

    experimenter = new MultipleExperimenter();
    int totalIterations = 0;
    while (!experimenter.isDone()) {
      totalIterations++;
      experimenter.defineValues();
      experimenter.updateValues();
    }
    assertEquals(4, totalIterations);
  }
}
