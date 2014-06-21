/**
 * 
 */
package progen.output.outputers;

import java.util.ArrayList;
import java.util.List;

import progen.context.ProGenContext;

/**
 * Clase que almacena todas las salidas disponibles en una ejecución concreta de
 * ProGen. Está implementado con un Singleton, de tal forma que únicamente
 * existe una instancia del almacén en toda la ejecución.
 * 
 * @author jirsis
 * @since 2.0
 * 
 */
public class OutputStore {

  /** Conjunto de salidas disponibles. */
  private List<Outputer> outputers;

  /**
   * Almacén que representa la única instancia del almacén a lo largo de la
   * ejecución.
   */
  private static OutputStore store;

  /**
   * Constructor privado de la clase. Únicamente se ejecutará desde el método de
   * crearción si no existe una instancia previa.
   */
  private OutputStore() {
    outputers = new ArrayList<Outputer>();
  }

  /**
   * Método de creación de la instancia única. En caso de existir una
   * previamente, se devolverá una referencia a ésta.
   * 
   * @return La instancia única del almaacen de salidas.
   */
  public synchronized static OutputStore makeInstance() {
    if (store == null) {
      store = new OutputStore();
      store.init();
    }
    return store;
  }

  /**
   * Método que carga en memoria todas las salidas disponibles en la ejecución
   * de ProGen.
   * 
   * Las salidas disponibles se definen con la propiedad
   * <code> progen.outputers </code> y serparadas por comas (,).
   */
  public void init() {
    String outputersName[] = ProGenContext.getOptionalProperty("progen.outputers", "NullOutputer").split(",[ ]*");
    try {
      for (String outputClass : outputersName) {
        Outputer output = (Outputer) Class.forName("progen.output.outputers." + outputClass).newInstance();

        outputers.add(output);
      }
    } catch (InstantiationException e) {
      throw new UnknowOutputerException(e.getMessage());
    } catch (IllegalAccessException e) {
      throw new UnknowOutputerException(e.getMessage());
    } catch (ClassNotFoundException e) {
      throw new UnknowOutputerException(e.getMessage());
    }

  }

  /**
   * Ejecuta el método correspondiente de todas las salidas disponibles.
   */
  public void print() {
    for (Outputer out : outputers) {
      out.print();
    }
  }

  /**
   * Ejecuta el método correspondiente de cierre en todas las salidas
   * disponibles.
   */
  public void close() {
    for (Outputer out : outputers) {
      out.close();
    }
  }
}
