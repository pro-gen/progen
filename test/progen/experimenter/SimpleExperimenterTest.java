package test.progen.experimenter;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import progen.context.ProGenContext;
import progen.experimenter.Experimenter;
import progen.experimenter.ExperimenterFactory;
import progen.experimenter.SimpleExperimenter;
import progen.kernel.population.Individual;
import progen.kernel.population.Population;
import progen.output.HistoricalData;
import progen.userprogram.UserProgram;

public class SimpleExperimenterTest {

    private Experimenter experimenter;

    @Before
    public void setUp(){
	ProGenContext.makeInstance("test/progen/experimenter/simple/master.txt");
	ProGenContext.setProperty("progen.outputers", "StandardFile, BestIndividualFile");
	experimenter = new SimpleExperimenter();
    }
    
    @After
    public void tearDown(){
	experimenter.deleteDirectory(new File(ProGenContext.getMandatoryProperty("progen.output.dir")));
	ProGenContext.clearContext();
    }
    
    /**
     * Comprueba que al inicializar el experimenter, se hayan creado las carpetas
     * correspondientes, así como se hayan copiado los ficheros mínimos.
     */
    @Test
    public void testConstructExperimenterDefault(){
	String outputDir = ProGenContext.getMandatoryProperty("progen.output.dir");
	String masterFile = ProGenContext.getMandatoryProperty("progen.masterfile");
	String experimentFile = ProGenContext.getMandatoryProperty("progen.experiment.name")+".txt";
	
	masterFile = masterFile.substring(masterFile.lastIndexOf(File.separator)+1);
	
	checkDir(new File(outputDir));
	checkFile(new File(outputDir+File.separator+masterFile));
	checkFile(new File(outputDir+File.separator+experimentFile));
    }
    
    private void checkDir(File dir){
	assertTrue(dir.exists());
	assertTrue(dir.isDirectory());
	assertTrue(dir.canWrite());
    }
    
    private void checkFile(File file){
	assertTrue(file.exists());
	assertTrue(file.isFile());
	assertTrue(file.canWrite());
    }
    
    @Test
    public void testConstructExperimenterUserDefinedRelativeOutputPath(){
	ProGenContext.setProperty("progen.output.dir", "../relativeOutputSimple");
	experimenter = new SimpleExperimenter();
	testConstructExperimenterDefault();
    }
    
    @Test
    public void testConstructExperimenterUserDefinedAbsoluteOutputPath(){
	String outputPath = System.getProperty("java.io.tmpdir")+File.separator+"ProGenOut";
	ProGenContext.setProperty("progen.output.dir", outputPath);
	experimenter = new SimpleExperimenter();
	testConstructExperimenterDefault();
    }

    /**
     * Comprueba que si no se define explícitamente, ExperimentFactory devuelve
     * una instancia de SimpleExperimenter
     */
    @Test
    public void testMakeSimpleExperimenterDefault() {
	experimenter = ExperimenterFactory.makeInstance();
	assertTrue("No es una instancia de SimpleExperimenter",
		experimenter instanceof SimpleExperimenter);
    }

    /**
     * Comprueba que si se define explícitamente, ExperimentFactory devuelve
     * una instancia de SimpleExperimenter
     */
    @Test
    public void testMakeSimpleExperimenterOff() {
	ProGenContext.setProperty("progen.experimenter", "off");
	experimenter = ExperimenterFactory.makeInstance();
	assertTrue("No es una instancia de SimpleExperimenter",
		experimenter instanceof SimpleExperimenter);
    }
    
    /**
     * Comprueba que al ser un experimenter simple, únicamente se ejecuta una
     * vez.
     */
    @Test
    public void testIsDone() {
	int loopIteration = -1;
	while (!experimenter.isDone()){
	    loopIteration++;
	    experimenter.updateValues();
	} 
	
	assertTrue(loopIteration == 0);
    }

    /**
     * Genera una serie de resultados en la carpeta que define ProGen por defecto
     * user.bin/../outputs/<nombreExperimento>/results
     */
    @Test
    public void testGenerateResults() {
	File outputDir;
	boolean masterFile = false;
	HistoricalData historical = HistoricalData.makeInstance();
	UserProgram userProgram = UserProgram.getUserProgram();
	Population population = new Population();
	Individual individual = population.getIndividual(0);
	individual.calculate(userProgram);
	
	historical.getDataCollectorExperiment("ExperimentIndividualData").addValue("statistical", individual);
	historical.getCurrentDataCollector("ExperimentIndividualData").addValue("statistical", individual);
	historical.getCurrentDataCollector("PopulationData").addValue("individualMean", individual);
	
	experimenter.generateOutputs();
	
	outputDir = new File(
		ProGenContext.getMandatoryProperty("progen.output.dir"));
	assertTrue(outputDir.exists());
	assertTrue(outputDir.isDirectory());
	assertTrue(outputDir.canRead());
	assertTrue(outputDir.canWrite());
	for (File file : outputDir.listFiles()) {
	    masterFile = masterFile
		    || ProGenContext.getMandatoryProperty("progen.masterfile")
			    .contains(file.getName());
	}
	assertTrue(masterFile == true);
    }
}
