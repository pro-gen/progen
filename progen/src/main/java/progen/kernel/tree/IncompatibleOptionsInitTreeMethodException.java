/**
 * 
 */
package progen.kernel.tree;

import progen.kernel.error.Error;

/**
 * Excepción que se lanzará si los parámetros que definen el método de
 * inicialización no permiten generar un árbol compatible.
 * 
 * @author jirsis
 * @since 2.0
 */
public class IncompatibleOptionsInitTreeMethodException extends RuntimeException {

  /** Para serialización */
  private static final long serialVersionUID = 8232941914904559013L;

  /**
   * Constructor que recibe los parámetros que definen el método de
   * inicialización.
   * 
   * @param maxNodes
   *          Número máximo de nodos.
   * @param minDepth
   *          Profundidad mínima del árbol.
   * @param maxDepth
   *          Profundidad máxima del árbol.
   */
  public IncompatibleOptionsInitTreeMethodException(int maxNodes, int minDepth, int maxDepth) {
    super(Error.get(27) + "[maxNodes: " + maxNodes + ", depth-interval: " + minDepth + ", " + maxDepth + "]");
  }

}
