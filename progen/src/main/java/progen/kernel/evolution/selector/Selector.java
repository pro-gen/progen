package progen.kernel.evolution.selector;

import java.util.List;
import java.util.Map;

import progen.kernel.population.Individual;
import progen.kernel.population.Population;

/**
 * Clase abstracta que define el comportamiento general de todos los selectores
 * que estén disponibles en ProGen.
 * 
 * @author jirsis
 * @since 2.0
 * 
 */
public abstract class Selector {

  /**
   * Método de factoría para generar una instancia concreta de un selector
   * pasado por parámetro.
   * 
   * @param name
   *          Nombre del selector a instanciar.
   * @param params
   *          Parámetros del selector.
   * @return Instancia nueva del selector.
   */
  public static Selector makeSelector(String name, Map<String, String> params) {
    Selector selector = null;
    try {
      selector = (Selector) Class.forName("progen.kernel.evolution.selector." + name).newInstance();
      selector.setParams(params);
    } catch (InstantiationException e) {
      throw new UnknownSelectorException(name, e);
    } catch (IllegalAccessException e) {
      throw new UnknownSelectorException(name, e);
    } catch (ClassNotFoundException e) {
      throw new UnknownSelectorException(name, e);
    }
    return selector;
  }

  /**
   * Forma de definir los parámetros que pueda tener un selector concreto. Por
   * defecto, se ignorarán todos, y será responsabilidad del selector concreto
   * procesarlos según necesite algún valor de configuración extra.
   * 
   * @param params
   *          Los parámetros específicos del selector.
   */
  public void setParams(Map<String, String> params) {
    // do nothing, by default
  }

  /**
   * Se seleccionan de la población pasada por parámetro la cantidad de
   * individuos definidos según los parámetros. Es importante clonar los
   * individuos seleccionados, antes de añadirlos a la lista de retorno del
   * método para evitar efectos no deseados en las generaciones posteriores,
   * durante la ejecución del experimento.
   * 
   * @param pop
   *          La población de la que seleccionar los individuos.
   * @param howMany
   *          Cantidad de individuos que serán seleccionados al aplicar el
   *          selector.
   * @return La colección de individuos seleccionados.
   */
  public abstract List<Individual> select(Population pop, int howMany);

}
