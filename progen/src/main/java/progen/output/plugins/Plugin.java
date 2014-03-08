package progen.output.plugins;

import java.util.List;

/**
 * Interfaz que representa la cabeza en la jeraquía de Plugins. 
 * Se definen todos los métodos que tendrán que implementar los distintos tipos de plugins.
 * 
 * @author jirsis
 * @since 2.0
 */
public interface Plugin {
	/**
	 * Función que se utilizará para ir calculando los valores que terminará ofreciendo el plugin.
	 * @param value El valor a añadir.
	 */
	@SuppressWarnings("unchecked")
	public void addValue(Comparable value);
	
	/**
	 * Devuelve el valor que se ha calculado en el plugin.
	 * @return el valor calculado.
	 */
	public Object getValue();
	
	/**
	 * Define los parámetros iniciales con los que empezará a calcular el plugin.
	 */
	public void initialValue();
	
	/**
	 * Devuelve el orden relativo en el que tiene que ser calculado, es decir, 
	 * si es un plugin que no depende del cálculo de ningún otro, devolverá 0,
	 * sino, tendrá que devolver un numero mayor, en función de los plugins
	 * de los que dependa. 
	 * Por ejemplo, el plugin Mean, es independiente, por lo que devolverá 0,
	 * sin embargo, Variance depende de éste, por lo tanto devolverá un valor 
	 * mayor o igual que 1, si hubiera algún plugin que dependiera de Variance,
	 * éste último, tendría que devolver un valor mayor que el devuelto por Variance.
	 * @return el orden relativo para calcular el valor del plugin.
	 */
	public int getPriority();
	
	/**
	 * Devuelve el nombre único del plugin.
	 * @return La cadena con el nombre único del plugin.
	 */
	public String getName();
	
	
	/**
	 * Forma de inicializar los distintos parámetros del plugin, en función
	 * de la configuración definida. Recibe una cadena que identifica
	 * la familia de propiedades que hacen referencia a este plugin. 
	 * @param propertyFamily La famila de propiedades que hacen referencia
	 * al plugin.
	 */
	public void initPlugin(String propertyFamily);
	
	/**
	 * Devuelve la referencia a un plugin del tipo solicitado como 
	 * parámetro o un NullPlugin, en caso de no conocer el plugin.
	 * @param name El nombre del plugin a buscar.
	 * @return La instancia del plugin solicitado o NullPlugin en otro caso.
	 */
	public Plugin getPlugin(String name);
	
	/**
	 * Encargado de comprobar que ya existen los plugins necesarios para poder calcular el 
	 * actual. En caso de que falte alguno, será necesario añadirlo a la colección.
	 * @param pluginCollection Colección de plugins disponibles.
	 */
	public void checkDependeces(List<Plugin> pluginCollection);

}
