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

  private StandardFile output;
  
  private File outputDir;
  
  @Before
  public void setUp() throws Exception {
    final String outputDirTest = FileOutputTest.class.getClassLoader().getResource("progen/output/outputers/StandardFileTest.class").getFile();
    outputDir = new File(outputDirTest);
    mockContext();
    output = new StandardFile();
  }

  private void mockContext() {
    mockProGenContext();
    
    HistoricalData historicalDataMock = mock(HistoricalData.class);
    
    mockDataCollector(historicalDataMock);
    Individual individualMock = mockIndividual();
    
    Plugin bestPluginMock = mock(Plugin.class);
    when(bestPluginMock.getValue()).thenReturn(individualMock);
    
    
    when(historicalDataMock.getDataCollectorExperiment(any(String.class)).getPlugin("best")).thenReturn(bestPluginMock);    
    when(historicalDataMock.getCurrentDataCollector(any(String.class)).getPlugin("best")).thenReturn(bestPluginMock);
    
    Plugin individualMeanPluginMock = mock(Plugin.class);
    
    Map<String, Mean> meanMock = mock(Map.class);
    Mean meanValueMock = mock(Mean.class);
    when(meanValueMock.getValue())
      .thenReturn(42.3)
      .thenReturn(0.1)
      .thenReturn("12")
      .thenReturn("5")
      .thenReturn("8")
      .thenReturn("13")
      ;
    when(meanMock.get("raw")).thenReturn(meanValueMock );
    when(meanMock.get("adjusted")).thenReturn(meanValueMock );
    when(meanMock.get("RPB0-nodes")).thenReturn(meanValueMock);
    when(meanMock.get("RPB0-depth")).thenReturn(meanValueMock);
    when(individualMeanPluginMock.getValue()).thenReturn(meanMock);
    
    
    when(historicalDataMock.getCurrentDataCollector(any(String.class)).getPlugin("individualMean")).thenReturn(individualMeanPluginMock);
    Plugin worstPluginMock = mock(Plugin.class);
    Plugin breedingPluginMock = mock(Plugin.class);
    when(breedingPluginMock.getPlugin("mean")).thenReturn(individualMeanPluginMock);
    when(breedingPluginMock.getPlugin("total")).thenReturn(individualMeanPluginMock);
    when(historicalDataMock.getCurrentDataCollector(any(String.class)).getPlugin("breeding")).thenReturn(breedingPluginMock);
    
    when(worstPluginMock.getValue()).thenReturn(individualMock);
    when(historicalDataMock.getCurrentDataCollector(any(String.class)).getPlugin("worst")).thenReturn(worstPluginMock );
    Plugin evaluationPluginMock = mock(Plugin.class);
    Plugin meanPlugin = mock(Plugin.class);
    Plugin totalPlugin = mock(Plugin.class);
    when(meanPlugin.getValue()).thenReturn("1.3");
    when(totalPlugin.getValue()).thenReturn("100");
    when(evaluationPluginMock.getPlugin("mean")).thenReturn(meanPlugin );
    when(evaluationPluginMock.getPlugin("total")).thenReturn(totalPlugin);
    
    when(historicalDataMock.getCurrentDataCollector(any(String.class)).getPlugin("breeding")).thenReturn(evaluationPluginMock  );
    when(historicalDataMock.getCurrentDataCollector(any(String.class)).getPlugin("evaluation")).thenReturn(evaluationPluginMock  );
    
    when(HistoricalData.makeInstance()).thenReturn(historicalDataMock);
  }

  private Individual mockIndividual() {
    Individual individualMock = mock(Individual.class);
    Node nodeMock=mock(Node.class);
    
    Map<String, Tree> treesMock = mock(Map.class);
    Tree treeMock=mock(Tree.class);
    
    
    when(nodeMock.getTotalNodes()).thenReturn(3);
    when(nodeMock.getMaximunDepth()).thenReturn(5);
    when(treeMock.getRoot()).thenReturn(nodeMock);
    when(treesMock.get(any())).thenReturn(treeMock);
    when(individualMock.getTrees()).thenReturn(treesMock );
    when(individualMock.getRawFitness()).thenReturn(1.23);
    when(individualMock.getAdjustedFitness()).thenReturn(0.23);
    return individualMock;
  }

  private void mockDataCollector(HistoricalData historicalData) {
    DataCollector dataCollectorMock = mock(DataCollector.class);
    when(historicalData.getDataCollectorExperiment(any(String.class))).thenReturn(dataCollectorMock);
    when(historicalData.getCurrentDataCollector(any(String.class))).thenReturn(dataCollectorMock);
    when(historicalData.getCurrentGeneration()).thenReturn(1);
  }

  private void mockProGenContext() {
    mockStatic(ProGenContext.class, HistoricalData.class);
    when(ProGenContext.getMandatoryProperty("progen.output.dir")).thenReturn(String.format("%s-",outputDir.getAbsolutePath()));
    when(ProGenContext.getMandatoryProperty("progen.output.experiment")).thenReturn("");
    when(ProGenContext.getMandatoryProperty("progen.total.RPB")).thenReturn("1");
    when(ProGenContext.getMandatoryProperty("progen.total.ADF")).thenReturn("0");
    when(ProGenContext.getOptionalProperty("progen.max-generation", Integer.MAX_VALUE)).thenReturn(2);
  }
  
  @After
  public void tearDown(){
    FileUtils.cleanDir(outputDir, ".txt");
  }

  @Test
  public void testStandardFile() {
    assertNotNull(output.getLiterals());
    assertNull(output.getWriter());
  }

  @Test
  public void testPrint() {
    output.print();
    PrintWriter writer = output.getWriter();
    assertNotNull(writer);
    writer.write("I can not write this, because writer is closed");
    assertTrue(writer.checkError());
    assertOutputEquals("StandardFileTest.class-standardOutput.example", "StandardFileTest.class-standardOutput.test");
  }

  private void assertOutputEquals(String expectedFileName, String generatedFileName) {
    InputStream expectedStream = this.getClass().getResourceAsStream(expectedFileName);
    String expectedSHA1 = FileUtils.sha1sum(expectedStream);
    InputStream generatedStream = this.getClass().getResourceAsStream(expectedFileName);
    String generatedSHA1 = FileUtils.sha1sum(generatedStream); 
    assertEquals(expectedSHA1, generatedSHA1);
  }
}
