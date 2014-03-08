package progen.output.plugins;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import progen.context.ProGenContext;

/**
 * Plugin que calcula la diferencia entre tiempos determinados.
 * 
 * @author jirsis
 * @since 2.0
 *
 */
public class TimePlugin implements MacroPlugin {
	
	/** Colección de plugins que componen el macro plugin de cáculo de tiempo. */
	private List<Plugin> plugins;
	
	/** Representación del nombre del plugin. */
	private String name;
	
	/**
	 * Constructor que recibe como parámetro el nombre del tiempo que calculará.
	 * @param name El nombre del tiempo que calculará.
	 */
	public TimePlugin(String name){
		this.name=name;
		plugins=new ArrayList<Plugin>();
	}
	
	/*
	 * (non-Javadoc)
	 * @see progen.output.plugins.Plugin#addValue(java.lang.Comparable)
	 */
	@SuppressWarnings("unchecked")
	public void addValue(Comparable value) {
		for(Plugin plugin: plugins){
			plugin.addValue(value);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see progen.output.plugins.Plugin#getValue()
	 */
	public HashMap<String, Object> getValue() {
		HashMap<String, Object> values=new HashMap<String, Object>();
		for(Plugin plugin: plugins){
			values.put(plugin.getName(), plugin.getValue());
		}
		return values;
	}

	/*
	 * (non-Javadoc)
	 * @see progen.output.plugins.Plugin#initialValue()
	 */
	public void initialValue() {
		for(Plugin plugin: plugins){
			plugin.initialValue();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see progen.output.plugins.Plugin#getName()
	 */
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * @see progen.output.plugins.Plugin#getPriority()
	 */
	public int getPriority() {
		int priority=0;
		for(Plugin plugin : plugins){
			priority=Math.max(0, plugin.getPriority());
		}
		return priority;
	}
	
	/*
	 * (non-Javadoc)
	 * @see progen.output.plugins.Plugin#initPlugin(java.lang.String)
	 */
	public void initPlugin(String propertyFamily) {
		String pluginsName [] = ProGenContext.getOptionalProperty(propertyFamily+"."+getName()+".enable", "Mean, Total").trim().split(",[ ]*");
		Plugin plugin;
		for(int i=0;i<pluginsName.length;i++){
				try {
					plugin = (Plugin)Class.forName("progen.output.plugins."+pluginsName[i]).newInstance();
					this.addPlugin(plugins, plugin);
				} catch (InstantiationException e) {
					throw new UnknownPluginException(getName()+": "+pluginsName[i]);
				} catch (IllegalAccessException e) {
					throw new UnknownPluginException(getName()+": "+pluginsName[i]);
				} catch (ClassNotFoundException e) {
					throw new UnknownPluginException(getName()+": "+pluginsName[i]);
				}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see progen.output.plugins.Plugin#getPlugin(java.lang.String)
	 */
	public Plugin getPlugin(String name){
		Plugin plugin=null;
		if(getName().compareTo(name)==0){
			plugin=this;
		}else{
			for(Plugin pluginFound : plugins){
				if(pluginFound.getName().compareTo(name)==0){
					plugin=pluginFound;
				}
			}
		}
		if(plugin==null){
			plugin=new NullPlugin();
		}
			
		return plugin;
	}
	
	/*
	 * (non-Javadoc)
	 * @see progen.output.plugins.Plugin#checkDependeces(java.util.List)
	 */
	public void checkDependeces(List<Plugin> pluginCollection) {
		// do nothing
	}

	/*
	 * (non-Javadoc)
	 * @see progen.output.plugins.MacroPlugin#addPlugin(java.util.List, progen.output.plugins.Plugin)
	 */
	public void addPlugin(List<Plugin> pluginsCollection, Plugin plugin) {
		Plugin alreadyDefined=null;
		for(Plugin pluginDefined: plugins){
			if(pluginDefined.getName().compareTo(plugin.getName())==0){
				alreadyDefined=pluginDefined;
			}
		}
		
		if(alreadyDefined==null){
			plugins.add(plugin);
		}		
	}

	/*
	 * (non-Javadoc)
	 * @see progen.output.plugins.MacroPlugin#getPlugins()
	 */
	public List<Plugin> getPlugins() {
		return plugins;
	}

}
