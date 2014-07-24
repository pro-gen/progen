package progen.kernel.tree;

import java.io.Serializable;
import java.util.Map;

import progen.context.ProGenContext;
import progen.context.UnknownPropertyException;
import progen.kernel.grammar.Grammar;
import progen.userprogram.UserProgram;

/**
 * Clase que representa un árbol y los distintos métodos de acceso a él.
 * 
 * @author jirsis
 * 
 */
public class Tree implements Cloneable, Serializable {
  private static final String HALF_HALF_LITERAL = "half&half";

  private static final long serialVersionUID = 5043645528729444176L;

  /** Nodo raíz del árbol. */
  private Node root;

  /** Método de inicialización del árbol. */
  private InitializeTreeMethod initMethod;

  /**
   * Constructor de copia que recibe por parámetro otro árbol del que copiar los
   * datos.
   * 
   * @param tree
   *          El árbol a copiar.
   */
  public Tree(Tree tree) {
    this.initMethod = tree.initMethod;
    this.root = tree.root.clone();
  }

  /**
   * Constructor de la clase. Se configura el método de inicialización
   * definiendo en el fichero de configuración del usuario la propiedad
   * <code>progen.population.init-mode</code>, admitiendo como valores válidos:
   * <ul>
   * <li>full</li>
   * <li>grow</li>
   * <li>half&half</li>
   * </ul>
   * 
   */
  public Tree() {
    final String init = ProGenContext.getOptionalProperty("progen.population.init-mode", HALF_HALF_LITERAL);
    if ("full".equals(init)) {
      initMethod = new Full();
    } else if ("grow".equals(init)) {
      initMethod = new Grow();
    } else if (HALF_HALF_LITERAL.equals(init)) {
      initMethod = new HalfAndHalf();
    } else {
      throw new UnknownPropertyException(init + "? progen.population.init-method=grow|full|half&half");
    }
    root = null;
  }

  /**
   * Forma de generar un árbol a partir de una gramática pasada por parámetro.
   * 
   * @param grammar
   *          de la que se usarán las producciones para generar el árbol.
   */
  public void generate(Grammar grammar) {
    root = new Node(grammar.getAxiom());
    initMethod.generate(grammar, root);
  }

  /**
   * Devuelve el nodo raíz del árbol.
   * 
   * @return <code>Node</code> raíz del árbol.
   */
  public Node getRoot() {
    return getNode(0);
  }

  /**
   * Representación del árbol como si fuera un programa de Lisp
   * 
   * @return <code>String</code> con la representación del árbol con formato de
   *         Lisp
   */
  public String toString() {
    return root.toString();
  }

  /**
   * Evalúa el árbol de tal forma que se devuelve el valor después de ejecutar
   * todo el programa almacenado en este árbol.
   * 
   * @param userProgram
   *          Definición concreta del dominio del problema que necesitará el
   *          árbol para ser evaluado.
   * @param returnAddr
   *          Dirección de retorno de las llamadas a ADFs.
   * @return <code>Object</code> con el valor de la ejecución del programa.
   */
  public Object evaluate(UserProgram userProgram, Map<String, Object> returnAddr) {
    return root.evaluate(userProgram, returnAddr);
  }

  /**
   * Devuelve el nodo que está en la posición en preorden indicada como
   * parámetro.
   * 
   * @param position
   *          La posición dentro del árbol.
   * @return El nodo que está en la posición solicitada o <code>null</code> la
   *         posición está fuera de los rangos válidos.
   */
  public Node getNode(int position) {
    Node node = null;
    if (root != null) {
      node = root.getNode(position);
    }
    return node;
  }

  @Override
  public Tree clone() {
    try {
      super.clone();
    } catch (CloneNotSupportedException e) {
      // ignore this exception
    }
    return new Tree(this);
  }

  /**
   * Devuelve el método que se utilizó para generar este árbol.
   * 
   * @return El método de inicialización del árbol.
   */
  public InitializeTreeMethod getInitializeTreeMethod() {
    return initMethod;
  }
}
