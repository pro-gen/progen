package test.progen.output.plugins;

import junit.framework.TestCase;
import progen.output.plugins.Plugin;
import progen.output.plugins.Variance;

import java.util.ArrayList;
import java.util.List;

public class VarianceTest extends TestCase {

  private Variance variance;
  private Plugin mean;

  protected void setUp() throws Exception {
    super.setUp();
    variance = new Variance();
    List<Plugin> plugins = new ArrayList<Plugin>();
    variance.checkDependeces(plugins);
    mean = plugins.get(0);
    //calculamos la varianza de un dado de 6 caras
    for (int i = 1; i < 7; i++) {
      mean.addValue(new Double(i));
      variance.addValue(new Double(i));
    }
  }

  public void testGetValue() {
    assertTrue((Double) variance.getValue() == 2.9166666666666665);
  }

}
