package progen.output.outputers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
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
public class StandardFileTest {

  private HistoricalData historicalData;
  
  private File outputDir;
  
  private Locale defaultLocale;
  
  @Before
  public void setUp() throws Exception {
    defaultLocale = Locale.getDefault();
    Locale.setDefault(Locale.US);
    final String outputDirTest = FileOutputTest.class.getClassLoader().getResource("progen/output/outputers/StandardFileTest.class").getFile();
    outputDir = new File(outputDirTest);
    mockContext();
  }
  
  @After
  public void tearDown(){
    FileUtils.cleanDir(outputDir, ".txt");
    Locale.setDefault(defaultLocale);
  }

  @Test
  public void testFile() {
    StandardFile output = new StandardFile();
    assertNotNull(output.getLiterals());
    assertNull(output.getWriter());
  }

  @Test
  public void testStatusWriter() {
    FileUtils.setSuffixTest("status.test");
    StandardFile output = new StandardFile();
    output.print();
    PrintWriter writer = output.getWriter();
    assertNotNull(writer);
    writer.write("I can not write this, because writer is closed");
    assertTrue(writer.checkError());
  }
  
  @Test
  public void testContentRegularPrint() {
    FileUtils.setSuffixTest("regular.test");
    new StandardFile().print();
    assertOutputEquals("StandardFileTest-regular.example", "StandardFileTest.class-standardOutput.txt");
  }
  
  @Test
  public void testContentWithADFs() {
    FileUtils.setSuffixTest("adfs.test");
    when(ProGenContext.getOptionalProperty("progen.total.ADF", 0)).thenReturn(2);
    new StandardFile().print();
    assertOutputEquals("StandardFileTest-adfs.example", "StandardFileTest.class-standardOutput.txt");
  }
  
  @Test
  public void testContentOverMaxGenerations() {
    FileUtils.setSuffixTest("over-max-generations.test");
    when(ProGenContext.getOptionalProperty("progen.max-generation", Integer.MAX_VALUE)).thenReturn(-1);
    new StandardFile().print();
    assertOutputEquals("StandardFileTest-empty.example", "StandardFileTest.class-standardOutput.txt");
  }
  
  @Test
  public void testNoBestGeneration(){
    FileUtils.setSuffixTest("only-table.test");
    when(historicalData.getCurrentDataCollector(any(String.class)).getPlugin("best")).thenReturn(mock(Plugin.class));
    new StandardFile().print();
    assertOutputEquals("StandardFileTest-only-table.example", "StandardFileTest.class-standardOutput.txt");
  }
  
  private void mockContext() {
    mockProGenContext();
    historicalData = mock(HistoricalData.class);
    Individual individualMock = mockIndividual();
    Plugin bestPluginMock = mockBestPlugin(individualMock);
    Plugin individualMeanPluginMock = mockMean();
    Plugin breedingPluginMock = mockBreedingPlugin(individualMeanPluginMock);
    Plugin worstPluginMock = mockWorstPlugin(individualMock);
    Plugin meanPlugin = mockMeanPlugin();
    Plugin totalPlugin = mockTotalPlugin();
    Plugin evaluationPluginMock = mockEvaluationPlugin(meanPlugin, totalPlugin);
    when(historicalData.getCurrentGeneration()).thenReturn(1);
    mockDataCollector(historicalData, bestPluginMock);
    mockCurrentDataCollector(historicalData, bestPluginMock, individualMeanPluginMock, breedingPluginMock, worstPluginMock, evaluationPluginMock);
    when(HistoricalData.makeInstance()).thenReturn(historicalData);
  }

  private void mockCurrentDataCollector(HistoricalData historicalDataMock, Plugin bestPluginMock, Plugin individualMeanPluginMock, Plugin breedingPluginMock, Plugin worstPluginMock, Plugin evaluationPluginMock) {
    when(historicalDataMock.getCurrentDataCollector(any(String.class))).thenReturn(mock(DataCollector.class));
    when(historicalDataMock.getCurrentDataCollector(any(String.class)).getPlugin("individualMean")).thenReturn(individualMeanPluginMock);
    when(historicalDataMock.getCurrentDataCollector(any(String.class)).getPlugin("best")).thenReturn(bestPluginMock);
    when(historicalDataMock.getCurrentDataCollector(any(String.class)).getPlugin("breeding")).thenReturn(breedingPluginMock);
    when(historicalDataMock.getCurrentDataCollector(any(String.class)).getPlugin("worst")).thenReturn(worstPluginMock );
    when(historicalDataMock.getCurrentDataCollector(any(String.class)).getPlugin("breeding")).thenReturn(evaluationPluginMock  );
    when(historicalDataMock.getCurrentDataCollector(any(String.class)).getPlugin("evaluation")).thenReturn(evaluationPluginMock  );
  }

  private void mockDataCollector(HistoricalData historicalDataMock, Plugin bestPluginMock) {
    when(historicalDataMock.getDataCollectorExperiment(any(String.class))).thenReturn(mock(DataCollector.class));
    when(historicalDataMock.getDataCollectorExperiment(any(String.class)).getPlugin("best")).thenReturn(bestPluginMock);
  }

  private Plugin mockEvaluationPlugin(Plugin meanPlugin, Plugin totalPlugin) {
    Plugin evaluationPluginMock = mock(Plugin.class);
    when(evaluationPluginMock.getPlugin("mean")).thenReturn(meanPlugin );
    when(evaluationPluginMock.getPlugin("total")).thenReturn(totalPlugin);
    return evaluationPluginMock;
  }

  private Plugin mockTotalPlugin() {
    Plugin totalPlugin = mock(Plugin.class);
    when(totalPlugin.getValue()).thenReturn("100");
    return totalPlugin;
  }

  private Plugin mockMeanPlugin() {
    Plugin meanPlugin = mock(Plugin.class);
    when(meanPlugin.getValue()).thenReturn("1.3");
    return meanPlugin;
  }

  private Plugin mockWorstPlugin(Individual individualMock) {
    Plugin worstPluginMock = mock(Plugin.class);
    when(worstPluginMock.getValue()).thenReturn(individualMock);
    return worstPluginMock;
  }

  private Plugin mockBestPlugin(Individual individualMock) {
    Plugin best = mockWorstPlugin(individualMock);
    Individual individual = mockIndividual();
    when(individual.getTotalADF()).thenReturn(2);
    when(individual.getTotalRPB()).thenReturn(1);
    when(best.getValue()).thenReturn(individual );
    return best; 
  }

  private Plugin mockBreedingPlugin(Plugin individualMeanPluginMock) {
    Plugin breedingPluginMock = mockEvaluationPlugin(individualMeanPluginMock, individualMeanPluginMock);
    return breedingPluginMock;
  }

  private Plugin mockMean() {
    Plugin individualMeanPluginMock = mock(Plugin.class);
    Mean meanValueMock = mock(Mean.class);
    Map<String, Mean> meanMock = mockMeanValues(meanValueMock);
    when(meanMock.get("raw")).thenReturn(meanValueMock );
    when(meanMock.get("adjusted")).thenReturn(meanValueMock );
    when(meanMock.get("RPB0-nodes")).thenReturn(meanValueMock);
    when(meanMock.get("RPB0-depth")).thenReturn(meanValueMock);
    when(meanMock.get("ADF0-nodes")).thenReturn(meanValueMock);
    when(meanMock.get("ADF0-depth")).thenReturn(meanValueMock);
    when(meanMock.get("ADF1-nodes")).thenReturn(meanValueMock);
    when(meanMock.get("ADF1-depth")).thenReturn(meanValueMock);
    when(individualMeanPluginMock.getValue()).thenReturn(meanMock);
    return individualMeanPluginMock;
  }

  private Map<String, Mean> mockMeanValues(Mean meanValueMock) {
    Map<String, Mean> meanMock = mock(Map.class);
    when(meanValueMock.getValue())
      .thenReturn(42.3)
      .thenReturn(0.1)
      .thenReturn("3")
      .thenReturn("5")
      .thenReturn("8")
      .thenReturn("13")
      .thenReturn("21")
      .thenReturn("34")
      .thenReturn("55")
      .thenReturn("89");
    return meanMock;
  }

  private Individual mockIndividual() {
    Individual individualMock = mock(Individual.class);
    Node nodeMock=mockNode();
    Map<String, Tree> treesMock = mockTree(nodeMock);
    when(individualMock.getTrees()).thenReturn(treesMock );
    
    when(individualMock.getRawFitness()).thenReturn(1.23);
    when(individualMock.getAdjustedFitness()).thenReturn(0.23);
    return individualMock;
  }

  private Map<String, Tree> mockTree(Node nodeMock) {
    Tree treeMock=mock(Tree.class);
    Map<String, Tree> treesMapMock = mock(Map.class);
    when(treeMock.getRoot()).thenReturn(nodeMock);
    when(treeMock.toString()).thenReturn("(is a sample tree)");
    when(treesMapMock.get(any())).thenReturn(treeMock);
    return treesMapMock;
  }

  private Node mockNode() {
    Node node = mock(Node.class);
    when(node.toString()).thenReturn("(this is a simple example)");
    when(node.getTotalNodes()).thenReturn(3);
    when(node.getMaximunDepth()).thenReturn(5);

    return node;
  }

  private void mockProGenContext() {
    mockStatic(ProGenContext.class, HistoricalData.class);
    
    when(ProGenContext.getMandatoryProperty("progen.output.dir")).thenReturn(String.format("%s-",outputDir.getAbsolutePath()));
    when(ProGenContext.getMandatoryProperty("progen.output.experiment")).thenReturn("");
    when(ProGenContext.getMandatoryProperty("progen.total.RPB")).thenReturn("1");
    when(ProGenContext.getOptionalProperty("progen.max-generation", Integer.MAX_VALUE)).thenReturn(2);
  }
  
  private void assertOutputEquals(String expectedFileName, String generatedFileName) {
    InputStream expectedStream = this.getClass().getResourceAsStream(expectedFileName);
    String expectedSHA1 = FileUtils.sha1sum(expectedStream);
    InputStream generatedStream = this.getClass().getResourceAsStream(generatedFileName);
    String generatedSHA1 = FileUtils.sha1sum(generatedStream); 
    assertEquals(expectedSHA1, generatedSHA1);
  }
}
