package progen.output.plugins;

import java.util.ArrayList;
import java.util.List;

/**
 * Plugin que calcula la varianza de todos los elementos introducidos.
 * 
 * @author jirsis
 * @since 2.0
 * 
 */
public class Variance implements Plugin {
  /** Referencia al plugin que calcula la media de la colección de elementos. */
  private Mean mean;
  /** Colección de elementos de los que se calculará la varianza. */
  private List<Double> elements;

  /**
   * Constructor genérico del plugin que recibe un plugin especializado en
   * calcular la media de los elementos. De esta forma, se evita tener que
   * calcular múltiples veces la media.
   */
  public Variance() {
    this.mean = new Mean();
    elements = new ArrayList<Double>();
  }

  @SuppressWarnings("rawtypes")
  public void addValue(Comparable value) {
    elements.add(Double.valueOf(value.toString()));

  }

  public Object getValue() {
    double variance = 0;
    for (Double element : elements) {
      variance += Math.pow(element - ((Double) mean.getValue()).doubleValue(), 2);
    }
    return variance / elements.size();
  }

  public void initialValue() {
    elements = new ArrayList<Double>();
  }

  public String getName() {
    return "variance";
  }

  public int getPriority() {
    return 1;
  }

  public void initPlugin(String propertyFamily) {
    // do nothing
  }

  public Plugin getPlugin(String name) {
    Plugin plugin;
    if (getName().compareTo(name) == 0) {
      plugin = this;
    } else {
      plugin = new NullPlugin();
    }
    return plugin;
  }

  public void checkDependeces(List<Plugin> pluginsCollection) {
    Plugin alreadyDefined = null;
    // check mean is defined
    for (Plugin plugin : pluginsCollection) {
      if (plugin.getName().compareTo(mean.getName()) == 0) {
        alreadyDefined = plugin;
      }
    }
    if (alreadyDefined == null) {
      pluginsCollection.add(mean);
    } else {
      mean = (Mean) alreadyDefined;
    }
  }

}