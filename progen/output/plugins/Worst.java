package progen.output.plugins;

import java.util.List;

/**
 * Plugin que se encargará de comprobar cual de todos los valores que se vayan añadiendo es el peor
 * y quedándose una referencia de éste. 
 * @author jirsis
 * @since 2.0
 *
 */
public class Worst implements Plugin {
	/** El peor objeto**/
	@SuppressWarnings("unchecked")
	private Comparable theWorst;
	
	@SuppressWarnings("unchecked")
	public void addValue(Comparable value) {
		if(theWorst==null){
			theWorst=value;
		}else if(value.compareTo(theWorst)>0)
			theWorst=value;
	}

	public Object getValue() {
		return theWorst;
	}

	public void initialValue() {
		//do nothing
		
	}

	public String getName() {
		return "worst";
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
