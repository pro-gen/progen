package progen.output.dataCollectors;

import java.util.ArrayList;
import java.util.List;

import progen.context.ProGenContext;
import progen.output.plugins.MacroPlugin;
import progen.output.plugins.NullPlugin;
import progen.output.plugins.Plugin;
import progen.output.plugins.UnknownPluginException;

/**
 * Clase que ofrece los métodos de acceso para actualizar la información 
 * relativa a un recolector de datos, así como de devolver el valor concreto
 * de la información almacenada.
 * 
 * @author jirsis
 * @since 2.0
 */
public class DataCollector {
	/** Representación de los plugins que componen un recolector de datos concreto. */
	private List<Plugin> plugins;
	/** Nombre por el que se hará referencia a un recolector concreto. */
	private String name;
	
	/**
	 * Constructor de la clase que espera una familia de propiedades definidas en los
	 * ficheros de configuración de ProGen.
	 * 
	 * @param proGenFamily La familia de propiedades que definen un DataCollector concreto.
	 */
	public DataCollector(String proGenFamily) {
		plugins = new ArrayList<Plugin>();
		name=ProGenContext.getMandatoryProperty(proGenFamily+".name");
		this.initDataCollector(proGenFamily);
	}
	
	/**
	 * Constructor que crea un DataCollector que no continen plugins.
	 */
	public DataCollector(){
		plugins=new ArrayList<Plugin>();
	}

	/**
	 * Método que inicializa y añade todos los plugins que componen un DataCollector concreto.
	 * Si no se especifica ningún plugin en los ficheros de configuración, por defecto 
	 * siempre estarán disponibles los plugins estadísticos básicos.
	 * 
	 * @param proGenProperty La propied
	 * 
	 * @see progen.output.plugins.StatisticalPlugin
	 */
	private void initDataCollector(String proGenProperty) {
		String[] pluginsEnableProperty;
		Plugin plugin;
		
		pluginsEnableProperty = ProGenContext.getOptionalProperty(
				proGenProperty+".plugins", "StatisticalPlugin").trim().split(",[ ]*");

		for (int i = 0; i < pluginsEnableProperty.length; i++) {
			try {
				plugin = (Plugin) Class.forName(
						"progen.output.plugins." + pluginsEnableProperty[i])
						.newInstance();
				plugin.initPlugin(proGenProperty);
				this.addPlugin(plugin);
			} catch (Exception e) {
				throw new UnknownPluginException(e.getMessage());
			}
		}

	}
	
	/**
	 * Forma de añadir plugins al recolector de datos. 
	 * @param plugin El plugin a añadir.
	 */
	private void addPlugin(Plugin plugin) {
		Plugin alreadyDefined = null;
		for (Plugin pluginDefined : plugins) {
			if (pluginDefined.getName().compareTo(plugin.getName()) == 0) {
				alreadyDefined = pluginDefined;
			}
		}
		if (alreadyDefined == null) {
			plugins.add(plugin);
		}
	}
	/**
	 * Devuelve la instancia del plugin que está definido con ese nombre.
	 * @param name El nombre del plugin a obtener.
	 * @return El plugin que se desea o <code>NullPlugin</code> en caso de no ser encontrado.
	 */
	public Plugin getPlugin(String name) {
		Plugin plugin = null;
		for (Plugin pluginDefined : plugins) {
			if (pluginDefined.getPlugin(name).getName().compareTo(name) == 0) {
				plugin = pluginDefined.getPlugin(name);
			}
		}
		if (plugin == null) {
			plugin = new NullPlugin();
		}
		return plugin;
	}

	/**
	 * Añade un nuevo valor al plugin determinado como parámetro.
	 * @param pluginName Plugin al que añadir el valor.
	 * @param value Valor a añadir.
	 */
	@SuppressWarnings("rawtypes")
	public void addValue(String pluginName, Comparable value) {
		Plugin plugin;
		for (Plugin pluginDefined : plugins) {
			plugin = pluginDefined.getPlugin(pluginName);
			plugin.addValue(value);
		}
	}
	
	/**
	 * Devuelve el conjunto de todos los plugins simples que están disponibles en el recolector de datos.
	 * @return El conunto de plugins que conforman el DataCollector.
	 */
	public List<Plugin> getPlugins(){
		List<Plugin> pluginsAvailable = new ArrayList<Plugin>();
		for(Plugin plugin : plugins){
			if(plugin instanceof MacroPlugin){
				pluginsAvailable.addAll(((MacroPlugin)plugin).getPlugins());
			}else{
				pluginsAvailable.add(plugin);
			}
		}
		return pluginsAvailable;
	}
	
	/**
	 * Devuelve el conjunto de los nombres de todos los plugins que forman el DataCollector.
	 * @return el conjunto de los nombres de todos los plugins que forman el DataCollector.
	 */
	public List<String> getPluginsNames(){
		List<String> names = new ArrayList<String>();
		for(Plugin plugin : plugins){
			names.add(plugin.getName());
		}
		return names;
	}
	
	/**
	 * Devuelve el nombre o etiqueta que identifica este DataCollector.
	 * @return el nombre que identifica este DataCollector.
	 */
	public String getName(){
		return name;
	}
}