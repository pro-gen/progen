package progen.output.outputers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import progen.context.ProGenContext;
import progen.kernel.population.Individual;
import progen.kernel.tree.Node;
import progen.kernel.tree.Tree;
import progen.output.HistoricalData;
import progen.output.datacollectors.DataCollector;
import progen.output.plugins.Mean;
import progen.output.plugins.Plugin;
import file.FileUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ ProGenContext.class, HistoricalData.class })
public class StandardConsoleTest {
  
  private StandardConsole console;
  private ByteArrayOutputStream outputStream;
  
  private PrintStream defaultOutputStram;
  private Locale defaultLocale;

  @Before
  public void setUp() throws Exception {
    Locale.setDefault(Locale.US);
    mockSystemOut();
    mockProGenContext();
    mockHistoricalData();
    console=new StandardConsole();
    defaultLocale = Locale.getDefault();
  }
  
  @After
  public void tearDown() throws UnsupportedEncodingException{
    System.setOut(defaultOutputStram);
    Locale.setDefault(defaultLocale);
  }

  @Test
  public void testStandardConsole() throws UnsupportedEncodingException {
    console = new StandardConsole();
    assertNotNull(console.getLiterals());
    assertEquals("Raw", console.getLiterals().getString("raw"));
    InputStream expectedFile = this.getClass().getResourceAsStream("StandardConsoleTest-empty.example");
    String expectedSHA1 = FileUtils.sha1sum(expectedFile);
    InputStream stream = new ByteArrayInputStream(outputStream.toString(StandardCharsets.UTF_8.toString()).getBytes(StandardCharsets.UTF_8));
    String generatedSHA1=FileUtils.sha1sum(stream);
    assertEquals(expectedSHA1, generatedSHA1);
  }

  @Test
  public void testPrint() throws UnsupportedEncodingException {
    console.print();
    InputStream expectedFile = this.getClass().getResourceAsStream("StandardConsoleTest-regular.example");
    String expectedSHA1 = FileUtils.sha1sum(expectedFile);
    
    InputStream stream = new ByteArrayInputStream(outputStream.toString(StandardCharsets.UTF_8.toString()).getBytes(StandardCharsets.UTF_8));
    String generatedSHA1=FileUtils.sha1sum(stream);
    assertEquals(expectedSHA1, generatedSHA1);
  }
  
  private void mockSystemOut() {
    defaultOutputStram = System.out;
    outputStream = new ByteArrayOutputStream();
    PrintStream stream = new PrintStream(outputStream);
    System.setOut(stream);
  }

  private void mockHistoricalData() {
    mockStatic(HistoricalData.class);
    HistoricalData historicalData = mock(HistoricalData.class);
    when(HistoricalData.makeInstance()).thenReturn(historicalData);
    when(historicalData.getCurrentGeneration()).thenReturn(1);
    Node root = mockNode();
    Tree tree = mockTree(root);
    Map<String, Tree> mapTrees = mockMapTress(tree);
    Individual individual = mockIndividual(mapTrees);
    Plugin bestPlugin = mockBestPlugin(individual);
    Plugin individualMeanPlugin = mockIndividualMeanPlugin();
    Individual wostIindividual = mockWorstIndividual(mapTrees);
    Plugin worstPlugin = mockWostPlugin(wostIindividual );
    Plugin meanPlugin = mockMeanPlugin(mockMeanMap());
    Plugin breedingPlugin = mockBreedingPlugin(meanPlugin );
    Plugin evaluationPlugin = mockEvaluationPlugin(meanPlugin);
    DataCollector dataCollector = mockDataCollector(bestPlugin, individualMeanPlugin, worstPlugin, breedingPlugin, evaluationPlugin  );
    
    when(historicalData.getDataCollectorExperiment("ExperimentIndividualData")).thenReturn(dataCollector );
    when(historicalData.getCurrentDataCollector("ExperimentIndividualData")).thenReturn(dataCollector);
    when(historicalData.getCurrentDataCollector("PopulationData")).thenReturn(dataCollector);
    when(historicalData.getCurrentDataCollector("PopulationTimeData")).thenReturn(dataCollector);
  }

  private Plugin mockEvaluationPlugin(Plugin mean) {
    Plugin evaluation = mock(Plugin.class);
    when(evaluation.getPlugin("mean")).thenReturn(mean);
    when(evaluation.getPlugin("total")).thenReturn(mean);
    return evaluation;
  }

  private Plugin mockMeanPlugin(Map<String, Mean> meanMap) {
    Plugin plugin = mock(Plugin.class);
    when(plugin.getValue()).thenReturn(meanMap);
    return plugin;
  }
  
  private Plugin mockBreedingPlugin(Plugin mean) {
    Plugin plugin = mock(Plugin.class);
    when(plugin.getPlugin("mean")).thenReturn(mean);
    when(plugin.getPlugin("total")).thenReturn(mean);
    return plugin;
  }

  private Individual mockWorstIndividual(Map<String, Tree> mockTree) {
    Individual individual = mock(Individual.class);
    when(individual.getTrees()).thenReturn(mockTree);
    when(individual.getRawFitness()).thenReturn(1.0);
    when(individual.getAdjustedFitness()).thenReturn(2.0);
    return individual;
  }

  private Plugin mockWostPlugin(Individual individual) {
    Plugin worst = mock(Plugin.class);
    
    when(worst.getValue()).thenReturn(individual);
    return worst;
  }

  private Plugin mockIndividualMeanPlugin() {
    Plugin individualMean = mock(Plugin.class);
    Map<String, Mean> meanMap = mockMeanMap();
    when(individualMean.getValue()).thenReturn(meanMap );
    return individualMean;
  }

  private Map<String, Mean> mockMeanMap() {
    Map<String, Mean> meanMap = mock(Map.class);
    Mean meanMock = mockMean();
    when(meanMap.get("raw")).thenReturn(meanMock);
    when(meanMap.get("adjusted")).thenReturn(meanMock);
    when(meanMap.get("RPB0-nodes")).thenReturn(meanMock);
    when(meanMap.get("RPB0-depth")).thenReturn(meanMock);
    when(meanMap.get("ADF0-nodes")).thenReturn(meanMock);
    when(meanMap.get("ADF0-depth")).thenReturn(meanMock);
    when(meanMap.get("ADF1-nodes")).thenReturn(meanMock);
    when(meanMap.get("ADF1-depth")).thenReturn(meanMock);
    when(meanMap.toString()).thenReturn("1").thenReturn("2");
    return meanMap;
  }

  private Mean mockMean() {
    Mean meanMock = mock(Mean.class);
    when(meanMock.getValue())
      .thenReturn(2.0)
      .thenReturn(3.0)
      .thenReturn("5")
      .thenReturn("8")
      .thenReturn("13")
      .thenReturn("21")
      .thenReturn("34")
      .thenReturn("55");
    return meanMock;
  }

  private DataCollector mockDataCollector(Plugin best, Plugin individualMean, Plugin worst, Plugin breeding, Plugin evaluation) {
    DataCollector dataCollector = mock(DataCollector.class);
    when(dataCollector.getPlugin("best")).thenReturn(best);
    when(dataCollector.getPlugin("individualMean")).thenReturn(individualMean);
    when(dataCollector.getPlugin("worst")).thenReturn(worst);
    when(dataCollector.getPlugin("breeding")).thenReturn(breeding);
    when(dataCollector.getPlugin("evaluation")).thenReturn(evaluation);
    return dataCollector;
  }

  private Plugin mockBestPlugin(Individual individual) {
    Plugin plugin = mock(Plugin.class);
    when(plugin.getValue()).thenReturn(individual);
    return plugin;
  }

  private Individual mockIndividual(Map<String, Tree> map) {
    Individual individual = mock(Individual.class);
    when(individual.getTrees()).thenReturn(map);
    return individual;
  }

  private Map<String, Tree> mockMapTress(Tree tree) {
    Map<String, Tree> trees = mock(Map.class);
    when(trees.get(any(String.class))).thenReturn(tree);
    return trees;
  }

  private Tree mockTree(Node root) {
    Tree tree = mock(Tree.class);
    when(tree.getRoot()).thenReturn(root);
    return tree;
  }

  private Node mockNode() {
    Node node = mock(Node.class);
    when(node.getTotalNodes()).thenReturn(42);
    when(node.getMaximunDepth()).thenReturn(42);
    return node;
  }

  private void mockProGenContext() {
    mockStatic(ProGenContext.class);
    when(ProGenContext.getMandatoryProperty("progen.total.RPB")).thenReturn("1");
    when(ProGenContext.getOptionalProperty("progen.total.ADF", 0)).thenReturn(2);
    when(ProGenContext.getOptionalProperty("progen.max-generation", Integer.MAX_VALUE)).thenReturn(2);
  }

  

}
