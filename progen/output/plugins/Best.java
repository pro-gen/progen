package progen.output.plugins;

import java.util.List;

/**
 * Plugin que se encargará de comprobar cual de todos los valores que se vayan añadiendo es el mejor
 * y quedándose una referencia de éste.
 * 
 * @author jirsis
 * @since 2.0
 *
 */
public class Best implements Plugin {
	
	/** El mejor objeto**/
	@SuppressWarnings("rawtypes")
	private Comparable theBest;
	
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void addValue(Comparable value) {
		if(theBest==null){
			theBest=value;
		}else if(value.compareTo(theBest)<0){
			theBest=value;
		}
	}

	public Object getValue() {
		return theBest;
	}

	public void initialValue() {
		//do nothing
		
	}

	public String getName() {
		return "best";
	}

	public int getPriority() {
		return 0;
	}

	public void initPlugin(String propertyFamily) {
		//do nothing
		
	}

	public Plugin getPlugin(String name) {
		Plugin plugin;
		if(name.compareTo(getName())==0){
			plugin=this;
		}else{
			plugin = new NullPlugin();
		}
		return plugin;
	}

	public void checkDependeces(List<Plugin> pluginCollection) {
		// do nothing
	}

}
