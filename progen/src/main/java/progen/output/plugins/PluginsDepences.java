package progen.output.plugins;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Clase encargada de establecer las dependencias que existen entre dos plugins,
 * ordenándolos de menor a mayor, o lo que es lo mismo, de menos dependencias a
 * más.
 * 
 * @author jirsis
 * @since 2.0
 * 
 */
public class PluginsDepences implements Comparator<Plugin>, Serializable {

  /** Para serialización */
  private static final long serialVersionUID = -636476408340119205L;

  public int compare(Plugin plugin1, Plugin plugin2) {
    return plugin1.getPriority() - plugin2.getPriority();
  }

}
