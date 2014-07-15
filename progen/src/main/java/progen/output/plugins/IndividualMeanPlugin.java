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

  private static final String DASH_DEPTH_LITERAL = "-depth";
  private static final String DASH_NODES_LITERAL = "-nodes";
  private static final String ADF_LITERAL = "ADF";
  private static final String RPB_LITERAL = "RPB";
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
      trees.put(RPB_LITERAL + i + DASH_NODES_LITERAL, new Mean());
      trees.put(RPB_LITERAL + i + DASH_DEPTH_LITERAL, new Mean());
    }
    for (int i = 0; i < ProGenContext.getOptionalProperty("progen.total.ADF", 0); i++) {
      trees.put(ADF_LITERAL + i + DASH_NODES_LITERAL, new Mean());
      trees.put(ADF_LITERAL + i + DASH_DEPTH_LITERAL, new Mean());
    }
  }

  @Override
  public void addValue(Comparable<?> value) {
    if (!(value instanceof Individual)) {
      throw new ClassCastException(value.toString());
    }

    final Individual individual = (Individual) value;
    raw.addValue(individual.getRawFitness());
    adjusted.addValue(individual.getAdjustedFitness());
    for (int i = 0; i < individual.getTotalRPB(); i++) {
      trees.get(RPB_LITERAL + i + DASH_NODES_LITERAL).addValue(individual.getTrees().get(RPB_LITERAL + i).getRoot().getTotalNodes());
      trees.get(RPB_LITERAL + i + DASH_DEPTH_LITERAL).addValue(individual.getTrees().get(RPB_LITERAL + i).getRoot().getMaximunDepth());
    }
    for (int i = 0; i < individual.getTotalADF(); i++) {
      trees.get(ADF_LITERAL + i + DASH_NODES_LITERAL).addValue(individual.getTrees().get(ADF_LITERAL + i).getRoot().getTotalNodes());
      trees.get(ADF_LITERAL + i + DASH_DEPTH_LITERAL).addValue(individual.getTrees().get(ADF_LITERAL + i).getRoot().getMaximunDepth());
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
    final HashMap<String, Mean> value = new HashMap<String, Mean>();
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
