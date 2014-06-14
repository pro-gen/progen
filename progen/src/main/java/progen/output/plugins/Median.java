package progen.output.plugins;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Plugin que calcula que elemento de todos los añadidos está justo en el medio.
 * 
 * @author jirsis
 * @since 2.0
 * 
 */
public class Median implements Plugin {

  /** Elementos de los que se calculará la mediana. */
  @SuppressWarnings("rawtypes")
  private List<Comparable> medianCollection;

  /**
   * Constructor por defecto.
   */
  public Median() {
    initialValue();
  }

  @SuppressWarnings("rawtypes")
  public void addValue(Comparable value) {
    medianCollection.add(value);

  }

  /**
   * Devuelve el elemento que está en la posición de la mitad de la colección, o
   * en la inmediatamente inferior en caso de que el número de elementos sea
   * impar.
   */
  @SuppressWarnings("unchecked")
  public Object getValue() {
    Collections.sort(medianCollection);
    Object value = null;
    if (medianCollection.size() > 0) {
      value = medianCollection.get((int) (medianCollection.size() / 2 - 1));
    }
    return value;
  }

  @SuppressWarnings("rawtypes")
  public void initialValue() {
    medianCollection = new ArrayList<Comparable>();
  }

  public String getName() {
    return "median";
  }

  public int getPriority() {
    return 0;
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

  public void checkDependeces(List<Plugin> pluginCollection) {
    // do nothing
  }

}
