package progen.output.plugins;

import java.util.List;

/**
 * 
 * Plugin que termina devolviendo el valor medio de todos los que se pasaron por
 * parámetro.
 * 
 * @author jirsis
 * @since 2.0
 * 
 */
public class Mean implements Plugin {

	/** Cantidad de muestras introducidas hasta el momento */
	private int count;
	/** Suma total de las muestras hasta el momento */
	private double absoluteSum;

	/**
	 * Constructor por defecto.
	 */
	public Mean() {
		initialValue();
	}

	@SuppressWarnings("rawtypes")
	public void addValue(Comparable value) {
		count++;
		absoluteSum += new Double(value.toString());
	}

	public Object getValue() {
		return absoluteSum / count;
	}

	public void initialValue() {
		count = 0;
		absoluteSum = 0;
	}

	public String getName() {
		return "mean";
	}

	public int getPriority() {
		return 0;
	}
	
	public void initPlugin(String propertyFamily) {
		//do nothing
	}
	
	public Plugin getPlugin(String name){
		Plugin plugin;
		if(getName().compareTo(name)==0){
			plugin=this;
		}else{
			plugin=new NullPlugin();
		}
		return plugin;
	}

	public void checkDependeces(List<Plugin> pluginCollection) {
		// do nothing
	}

}
