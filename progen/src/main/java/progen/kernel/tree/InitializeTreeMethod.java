package progen.kernel.tree;

import java.io.Serializable;

import progen.kernel.grammar.Grammar;

/**
 * Interfaz que define el método que tendrán que implementar las distintas
 * clases que sean métodos de inicialización de árboles.
 * 
 * @author jirsis
 * 
 */
public interface InitializeTreeMethod extends Serializable{
  /**
   * Se creará un árbol completo, que colgará del nodo pasado como parámetro a
   * partir de la gramática que se proporciona.
   * 
   * @param grammar
   *          Gramática que se utilizará para generar un árbol concreto.
   * @param root
   *          Nodo raíz del árbol generado.
   */
  public void generate(Grammar grammar, Node root);

  /**
   * Forma de actualizar la profundidad máxima que aceptará para que existan
   * nodos. La actualización se realizará a partir del fichero de configuración,
   * en el caso de existir la propiedad correspondiente.
   */
  public void updateMaximunDepth();

  /**
   * Forma de actualizar la profundidad mínima que aceptará para que existan
   * nodos. La actualización se realizará a partir del fichero de configuración,
   * en el caso de existir la propiedad correspondiente.
   */
  public void updateMinimunDepth();

  /**
   * Forma de actualizar la cantidad de nodos que se aceptarán para generar el
   * árbol. La actualización se realizará a partir del fichero de configuración,
   * en el caso de existir la propiedad correspondiente.
   */
  public void updateMaximunNodes();
}
