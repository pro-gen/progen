/**
 * 
 */
package progen.output.plugins;

import java.util.List;

/**
 * Plugin que calcula la suma total de todos los elementos que se añadan.
 * 
 * @author jirsis
 * @since 2.0
 * 
 */
public class Total implements Plugin {

  /** Valor del que se va a calcular el total. */
  private double value;

  /**
   * Constructor que inicializa las variables a su valor por defecto.
   */
  public Total() {
    value = 0;
  }

  @SuppressWarnings("rawtypes")
  public void addValue(Comparable value) {
    this.value += Double.valueOf(value.toString());

  }

  public void checkDependeces(List<Plugin> pluginCollection) {
    // do nothing, because is independent plugin

  }

  public String getName() {
    return "total";
  }

  public Plugin getPlugin(String name) {
    Plugin plugin;
    if (name.equals(getName())) {
      plugin = this;
    } else {
      plugin = new NullPlugin();
    }
    return plugin;
  }

  public int getPriority() {
    return 0;
  }

  public Object getValue() {
    return value;
  }

  public void initPlugin(String propertyFamily) {
    // do nothing
  }

  public void initialValue() {
    value = 0;
  }

}
