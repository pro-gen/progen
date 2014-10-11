package progen.output.plugins;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import progen.context.ProGenContext;

/**
 * Clase que calcula las distintas variables estadísticas de un valor
 * determinado. Las variables estadísticas que calcula este plugin, vendrán
 * definidas en el fichero de configuración.
 * 
 * @author jirsis
 * @since 2.0
 * 
 */
public class StatisticalPlugin implements MacroPlugin {

  /** Conjunto de plugins que definen un plugin estadístico. */
  private List<Plugin> plugins;

  /**
   * Constructor por defecto.
   */
  public StatisticalPlugin() {
    plugins = new ArrayList<Plugin>();
  }

  @SuppressWarnings("rawtypes")
  public void addValue(Comparable value) {
    for (Plugin plugin : plugins) {
      plugin.addValue(value);
    }

  }

  /**
   * Devuelve una colección de valores de todos los resultados que devuelven los
   * plugins que contiene.
   * 
   * @return La colección de valores de los plugins que contiene.
   */
  public Map<String, Object> getValue() {
    final HashMap<String, Object> values = new HashMap<String, Object>();
    for (Plugin plugin : plugins) {
      values.put(plugin.getName(), plugin.getValue());
    }
    return values;
  }

  public void initialValue() {
    for (Plugin plugin : plugins) {
      plugin.initialValue();
    }
  }

  public void addPlugin(List<Plugin> pluginsCollection, Plugin plugin) {
    Plugin alreadyDefined = null;
    // if variance, needs mean
    if (plugin instanceof Variance) {
      plugin.checkDependeces(pluginsCollection);
    }

    for (Plugin pluginDefined : plugins) {
      if (pluginDefined.getName().compareTo(plugin.getName()) == 0) {
        alreadyDefined = pluginDefined;
      }
    }

    if (alreadyDefined == null) {
      plugins.add(plugin);
    }

  }

  public String getName() {
    return "statistical";
  }

  public int getPriority() {
    int priority = 0;
    for (Plugin plugin : plugins) {
      priority = Math.max(0, plugin.getPriority());
    }
    return priority;
  }

  public void initPlugin(String propertyFamily) {
    final String [] pluginsName = ProGenContext.getOptionalProperty(propertyFamily + "." + getName() + ".enable", "Best, Worst, Mean, Median").trim().split(",[ ]*");
    Plugin plugin;
    for (int i = 0; i < pluginsName.length; i++) {
      try {
        plugin = (Plugin) Class.forName("progen.output.plugins." + pluginsName[i]).newInstance();
        this.addPlugin(plugins, plugin);
      } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
        throw new UnknownPluginException(getName() + ": " + pluginsName[i], e);
      }
    }
  }

  public Plugin getPlugin(String name) {
    Plugin plugin = null;
    if (getName().compareTo(name) == 0) {
      plugin = this;
    } else {
      for (Plugin pluginFound : plugins) {
        if (pluginFound.getName().compareTo(name) == 0) {
          plugin = pluginFound;
        }
      }
    }
    if (plugin == null) {
      plugin = new NullPlugin();
    }

    return plugin;
  }

  public void checkDependeces(List<Plugin> pluginCollection) {
    // do nothing

  }

  public List<Plugin> getPlugins() {
    return plugins;
  }
}
