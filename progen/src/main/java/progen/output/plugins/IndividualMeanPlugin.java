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
	public IndividualMeanPlugin(){
		raw=new Mean();
		adjusted=new Mean();
		trees = new HashMap<String, Mean>();
		for(int i=0;i<Integer.parseInt(ProGenContext.getMandatoryProperty("progen.total.RPB"));i++){
			trees.put("RPB"+i+"-nodes", new Mean());
			trees.put("RPB"+i+"-depth", new Mean());
		}
		for(int i=0;i<ProGenContext.getOptionalProperty("progen.total.ADF", 0);i++){
			trees.put("ADF"+i+"-nodes", new Mean());
			trees.put("ADF"+i+"-depth", new Mean());
		}
	}
	/* (non-Javadoc)
	 * @see progen.output.plugins.Plugin#addValue(java.lang.Comparable)
	 */
	@SuppressWarnings("rawtypes")
	public void addValue(Comparable value) {
		Individual individual=(Individual)value;
		raw.addValue(individual.getRawFitness());
		adjusted.addValue(individual.getAdjustedFitness());
		for(int i=0;i<individual.getTotalRPB();i++){
			trees.get("RPB"+i+"-nodes").addValue(individual.getTrees().get("RPB"+i).getRoot().getTotalNodes());
			trees.get("RPB"+i+"-depth").addValue(individual.getTrees().get("RPB"+i).getRoot().getMaximunDepth());
		}
		for(int i=0;i<individual.getTotalADF();i++){
			trees.get("ADF"+i+"-nodes").addValue(individual.getTrees().get("ADF"+i).getRoot().getTotalNodes());
			trees.get("ADF"+i+"-depth").addValue(individual.getTrees().get("ADF"+i).getRoot().getMaximunDepth());
		}
	}

	/* (non-Javadoc)
	 * @see progen.output.plugins.Plugin#checkDependeces(java.util.List)
	 */
	public void checkDependeces(List<Plugin> pluginCollection) {
		//do nothing
	}

	/* (non-Javadoc)
	 * @see progen.output.plugins.Plugin#getName()
	 */
	public String getName() {
		return "individualMean";
	}

	/* (non-Javadoc)
	 * @see progen.output.plugins.Plugin#getPlugin(java.lang.String)
	 */
	public Plugin getPlugin(String name) {
		Plugin plugin;
		if(name.compareTo(getName())==0){
			plugin=this;
		}else{
			plugin=new NullPlugin();
		}
		return plugin;
		
	}

	/* (non-Javadoc)
	 * @see progen.output.plugins.Plugin#getPriority()
	 */
	public int getPriority() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see progen.output.plugins.Plugin#getValue()
	 */
	public Object getValue() {
		HashMap<String, Mean> value=new HashMap<String, Mean>();
		value.putAll(trees);
		value.put("raw", raw);
		value.put("adjusted", adjusted);
		return value;
	}

	/* (non-Javadoc)
	 * @see progen.output.plugins.Plugin#initPlugin(java.lang.String)
	 */
	public void initPlugin(String propertyFamily) {
		//do nothing
	}

	/* (non-Javadoc)
	 * @see progen.output.plugins.Plugin#initialValue()
	 */
	public void initialValue() {
		//do nothing
	}

}
