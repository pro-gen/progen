package progen.output.plugins;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import progen.context.ProGenContext;
import progen.kernel.population.Individual;

/**
 * Clase que representa los valores medios de un individuo.
 * 
 * @author jirsis
 * @since 2.0
 */
public class IndividualMeanPlugin implements Plugin {

  /** Valor medio del rawFitness./ */
  private Mean raw;
  /** Valor medio del adjustedFitness. */
  private Mean adjusted;
  /** Valores medios de todos los árboles que componen un individuo. */
  private Map<String, Mean> trees;

  /**
   * Constructor por defecto de la clase.
   */
  public IndividualMeanPlugin() {
    raw = new Mean();
    adjusted = new Mean();
    trees = new HashMap<String, Mean>();
    for (int i = 0; i < Integer.parseInt(ProGenContext.getMandatoryProperty("progen.total.RPB")); i++) {
      trees.put("RPB" + i + "-nodes", new Mean());
      trees.put("RPB" + i + "-depth", new Mean());
    }
    for (int i = 0; i < ProGenContext.getOptionalProperty("progen.total.ADF", 0); i++) {
      trees.put("ADF" + i + "-nodes", new Mean());
      trees.put("ADF" + i + "-depth", new Mean());
    }
  }
  
  @Override
  public void addValue(Comparable<?> value) {
    if(!(value instanceof Individual)){
      throw new ClassCastException(value.toString());
    }
    
    Individual individual = (Individual) value;
    raw.addValue(individual.getRawFitness());
    adjusted.addValue(individual.getAdjustedFitness());
    for (int i = 0; i < individual.getTotalRPB(); i++) {
      trees.get("RPB" + i + "-nodes").addValue(individual.getTrees().get("RPB" + i).getRoot().getTotalNodes());
      trees.get("RPB" + i + "-depth").addValue(individual.getTrees().get("RPB" + i).getRoot().getMaximunDepth());
    }
    for (int i = 0; i < individual.getTotalADF(); i++) {
      trees.get("ADF" + i + "-nodes").addValue(individual.getTrees().get("ADF" + i).getRoot().getTotalNodes());
      trees.get("ADF" + i + "-depth").addValue(individual.getTrees().get("ADF" + i).getRoot().getMaximunDepth());
    }
  }

  public void checkDependeces(List<Plugin> pluginCollection) {
    // do nothing
  }

  public String getName() {
    return "individualMean";
  }

  public Plugin getPlugin(String name) {
    Plugin plugin;
    if (name.compareTo(getName()) == 0) {
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
    HashMap<String, Mean> value = new HashMap<String, Mean>();
    value.putAll(trees);
    value.put("raw", raw);
    value.put("adjusted", adjusted);
    return value;
  }

  public void initPlugin(String propertyFamily) {
    // do nothing
  }

  public void initialValue() {
    // do nothing
  }

}
