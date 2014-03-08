/**
 * 
 */
package progen.output.plugins;

import java.util.List;

/**
 * Plugin que calcula la suma total de todos los elementos que se añadan.
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
	public Total(){
		value=0;
	}

	/*
	 * (non-Javadoc)
	 * @see progen.output.plugins.Plugin#addValue(java.lang.Comparable)
	 */
	@SuppressWarnings("unchecked")
	public void addValue(Comparable value) {
		this.value+=new Double(value.toString());

	}

	/* (non-Javadoc)
	 * @see progen.output.plugins.Plugin#checkDependeces(java.util.List)
	 */
	public void checkDependeces(List<Plugin> pluginCollection) {
		// do nothing, because is independent plugin

	}

	/* (non-Javadoc)
	 * @see progen.output.plugins.Plugin#getName()
	 */
	public String getName() {		
		return "total";
	}

	/* (non-Javadoc)
	 * @see progen.output.plugins.Plugin#getPlugin(java.lang.String)
	 */
	public Plugin getPlugin(String name) {
		Plugin plugin;
		if(name.equals(getName())){
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
		return value;
	}

	/* (non-Javadoc)
	 * @see progen.output.plugins.Plugin#initPlugin(java.lang.String)
	 */
	public void initPlugin(String propertyFamily) {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see progen.output.plugins.Plugin#initialValue()
	 */
	public void initialValue() {
		value=0;
	}


	
}
