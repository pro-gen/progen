package progen.output.plugins;

import java.util.List;

/**
 * Interfaz que define un método extra para añadir los plugins que calculará.
 * 
 * @author jirsis
 * @since 2.0
 * 
 */
public interface MacroPlugin extends Plugin {
  /**
   * Forma de añadir los distintos plugins que estarán habilitados. También
   * recibe como parámetro la colección de plugins que ya están añadidos al
   * macroPlugin, de tal forma que si un plugin concreto necesita algún otro,
   * pueda añadirlo si no existe ya en la colección.
   * 
   * @param pluginsCollection
   *          Colección de plugins que ya están añadidos.
   * @param plugin
   *          A añadir a la colección.
   */
  void addPlugin(List<Plugin> pluginsCollection, Plugin plugin);

  /**
   * Devuelve el conjunto de Plugins que componen el macro plugin.
   * 
   * @return el conjunto de plugins que componen el macro plugin.
   */
  List<Plugin> getPlugins();
}
