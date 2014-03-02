package progen.output.plugins;

import java.util.List;

/**
 * Plugin que realmente no hace nada.
 * @author jirsis
 * @since 2.0
 */
public class NullPlugin implements Plugin {

	@SuppressWarnings("unchecked")
	public void addValue(Comparable value) {
		
	}

	public Object getValue() {
		return "NA";
	}

	public void initialValue() {
		
	}

	public String getName() {
		return "null";
	}

	public int getPriority() {
		return 0;
	}

	public void initPlugin(String propertyFamily) {
		//do nothing
	}
	
	public Plugin getPlugin(String name){
		return this;
	}

	public void checkDependeces(List<Plugin> pluginCollection) {
		// do nothing
	}

}
