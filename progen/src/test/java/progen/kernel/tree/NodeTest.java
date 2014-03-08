package test.progen.kernel.tree;

import org.junit.Before;
import org.junit.Test;
import progen.kernel.functions.Function;
import progen.kernel.functions.NullFunction;
import progen.kernel.grammar.GrammarNonTerminalSymbol;
import progen.kernel.grammar.GrammarTerminalSymbol;
import progen.kernel.grammar.Production;
import progen.kernel.tree.Node;

import static org.junit.Assert.*;

/**
 * Clase de test que comprueba el correcto funcionamiento de los métodos de Nodo
 *
 * @author jirsis
 * @see progen.kernel.tree.Node
 * @since 2.0
 */
public class NodeTest {

  /**
   * Referencia al nodo raíz del árbol.
   */
  private Node root;
  /**
   * Referencia a un nodo hoja.
   */
  private Node leaf1;
  /**
   * Referencia a otro nodo hoja.
   */
  private Node leaf2;
  /**
   * Referencia a un nodo que está en medio del árbol.
   */
  private Node mediumNode;

  /**
   * Método de definición de un estado inicial concreto.
   * En este caso, se crea un árbol sencillo de la siguiente forma:
   * <p/>
   * 0-- 1-- 2
   * |   |-- 3
   * |-- 4-- 5
   * |-- 6-- 7
   * |-- 8-- 9
   * |   |-- 10
   * |-- 11
   * <p/>
   * Los nodos especiales son:
   * 0 -> root
   * 7 -> leaf1
   * 10 -> leaf2
   * 4 -> mediumNode
   *
   * @throws Exception
   */
  @Before
  public void setUp() throws Exception {
    root = new Node(new GrammarNonTerminalSymbol("0", "-"));
    new Node(new GrammarNonTerminalSymbol("1", "-"), root);
    new Node(new GrammarNonTerminalSymbol("2", "-"), root.getBranches().get(0));
    new Node(new GrammarNonTerminalSymbol("3", "-"), root.getBranches().get(0));
    mediumNode = new Node(new GrammarNonTerminalSymbol("4", "-"), root);
    new Node(new GrammarNonTerminalSymbol("5", "-"), root.getBranches().get(1));
    new Node(new GrammarNonTerminalSymbol("6", "-"), root);
    leaf1 = new Node(new GrammarNonTerminalSymbol("7", "-"), root.getBranches().get(2));
    new Node(new GrammarNonTerminalSymbol("8", "-"), root.getBranches().get(2));
    new Node(new GrammarNonTerminalSymbol("9", "-"), root.getBranches().get(2).getBranches().get(1));
    leaf2 = new Node(new GrammarNonTerminalSymbol("10", "-"), root.getBranches().get(2).getBranches().get(1));
    new Node(new GrammarNonTerminalSymbol("11", "-"), root.getBranches().get(2));
  }

  /**
   * Comprueba que el número total de nodos que cuelgan de cada uno de los
   * nodos especiales es lo esperado.
   */
  @Test
  public void testGetTotalNodes() {
    assertTrue(root.getTotalNodes() == 12);
    assertTrue(mediumNode.getTotalNodes() == 2);
    assertTrue(leaf1.getTotalNodes() == 1);
    assertTrue(leaf2.getTotalNodes() == 1);
  }

  /**
   * Comprueba que el nodo padre de cada nodo especial, es el que corresponde según
   * el esquema.
   */
  @Test
  public void testGetParent() {
    assertTrue(root.getParent() == null);
    assertTrue(mediumNode.getParent() == root);
    assertTrue(leaf1.getParent() == root.getBranches().get(2));
    assertTrue(leaf2.getParent().getParent() == leaf1.getParent());
  }

  /**
   * Comprueba que la función que contienen los nodos especiales es la que se espera.
   */
  @Test
  public void testGetFunction() {
    assertTrue(leaf1.getFunction().getFunction() instanceof NullFunction);
    assertTrue(leaf2.getFunction().getFunction() instanceof NullFunction);
    assertTrue(root.getFunction().getFunction() instanceof NullFunction);
    assertTrue(mediumNode.getFunction().getFunction() instanceof NullFunction);
  }

  /**
   * Comprueba que el símbolo que se almacena en cada nodo es el que corresponde.
   */
  @Test
  public void testGetSymbol() {
    assertTrue(root.getSymbol().toString().equals("0"));
    assertTrue(mediumNode.getSymbol().toString().equals("4"));
    assertTrue(leaf1.getSymbol().toString().equals("7"));
    assertTrue(leaf2.getSymbol().toString().equals("10"));
  }

  /**
   * Comprueba que cada nodo especial tiene una cantida de hijos acorde
   * al esquema planteado en la inicialización.
   */
  @Test
  public void testGetBranches() {
    assertTrue(root.getBranches().size() == 3);
    assertTrue(mediumNode.getBranches().size() == 1);
    assertTrue(leaf1.getBranches().size() == 0);
    assertTrue(leaf2.getBranches().size() == 0);
  }

  /**
   * Comprueba que cada nodo está a la profunidad que corresponde.
   */
  @Test
  public void testGetDepth() {
    assertTrue(root.getDepth() == 0);
    assertTrue(leaf1.getDepth() == 2);
    assertTrue(leaf2.getDepth() == 3);
    assertTrue(mediumNode.getDepth() == 1);
  }

  /**
   * Comprueba que al especificar una producción nueva, el nodo se adapta, y redefine
   * los elementos que lo componen. En este caso, la producción en la que se
   * basa el cambio es Ax -> T
   */
  @Test
  public void testSetProduction() {
    Function function = new NullFunction("T");
    GrammarNonTerminalSymbol[] args = new GrammarNonTerminalSymbol[0];
    Production production = new Production(new GrammarNonTerminalSymbol("Ax", "Ax"),
        new GrammarTerminalSymbol(function), args);
    root.setProduction(production);
    assertTrue(root.getFunction().getFunction() instanceof NullFunction);
    assertTrue(root.getFunction().getFunction() == function);
  }

  /**
   * Comprueba que se limpia un nodo concreto de forma correcta.
   */
  @Test
  public void testClearNode() {
    root.clearNode();
    assertTrue(root.getDepth() == 0);
    assertTrue(root.getTotalNodes() == 1);
    assertTrue(root.getBranches().size() == 0);
    assertTrue(root.getFunction().getFunction() instanceof NullFunction);
  }

  /**
   * Comprueba que únicamente son nodos hoja, leaf1 y leaf2.
   */
  @Test
  public void testIsLeaf() {
    assertTrue(leaf1.isLeaf());
    assertTrue(leaf2.isLeaf());
    assertFalse(root.isLeaf());
    assertFalse(mediumNode.isLeaf());
  }

  /**
   * Comprueba que el único nodo raíz es el nodo especial marcado como tal.
   */
  @Test
  public void testIsRoot() {
    assertTrue(root.isRoot());
    assertFalse(leaf1.isRoot());
    assertFalse(leaf2.isRoot());
    assertFalse(mediumNode.isRoot());
    for (int i = 1; i < root.getTotalNodes(); i++) {
      assertFalse(root.getNode(i).isRoot());
    }
  }

  /**
   * Comprueba que devuelve null cuando se solicita un nodo
   * fuera de los rangos permitidos (según el esquema, 12, 13 y -1)
   * y cuando se recuperan los nodos especiales a partir de la posición
   * dentro del esquema, se recuperan correctamente.
   */
  @Test
  public void testGetNode() {
    assertNull(root.getNode(12));
    assertNull(root.getNode(13));
    assertNull(root.getNode(-1));
    assertTrue(root.getNode(0) == root);
    assertTrue(root.getNode(4) == mediumNode);
    assertTrue(root.getNode(7) == leaf1);
    assertTrue(root.getNode(10) == leaf2);
  }

  /**
   * Comprueba que cuando se crea una poda un nodo, se comporta como un árbol nuevo.
   */
  @Test
  public void testBranch() {
    int totalNodesAfter = root.getTotalNodes();
    Node branch = mediumNode.branch();
    assertTrue(branch.isRoot());
    assertTrue(root.getTotalNodes() + branch.getTotalNodes() == totalNodesAfter);
  }

  /**
   * Comprueba que al definir un nuevo padre en un nodo, éste se
   * añade correctamente y se actualiza el resto del árbol.
   */
  @Test
  public void testSetParent() {
    Node newRoot = new Node(new GrammarNonTerminalSymbol("-1", "-"));
    newRoot.setBranch(root, 0);
    assertTrue(root.getParent() == newRoot);
    assertTrue(newRoot.getTotalNodes() == root.getTotalNodes() + 1);
    assertTrue(newRoot.getDepth() == 0);
    assertTrue(root.getDepth() == 1);
    assertTrue(leaf1.getDepth() == 3);
  }

  /**
   * Comprueba que la profundidad máxima de un nodo a partir de otro, corresponde
   * con el esquema planteado.
   */
  @Test
  public void testGetMaximunDepth() {
    assertTrue(root.getMaximunDepth() == 3);
    assertTrue(leaf1.getMaximunDepth() == 2);
    assertTrue(leaf2.getMaximunDepth() == 3);
    assertTrue(mediumNode.getMaximunDepth() == 2);
  }

  /**
   * Comprueba que al clonar los distintos nodos especiales, se obtiene un
   * nodo similar, pero siendo una instancia diferente. Todos los nodos
   * hijos también serán objetos distintos.
   */
  @Test
  public void testClone() {
    Node rootClone;
    testNodes(leaf1, leaf1.clone());
    testNodes(leaf2, leaf2.clone());
    testNodes(mediumNode, mediumNode.clone());
    rootClone = root.clone();
    testNodes(root, rootClone);
    for (int i = 0; i < root.getTotalNodes(); i++) {
      testNodes(root.getNode(i), rootClone.getNode(i));
    }
  }

  /**
   * Comprueba que los atributos de un nodo clonado de otro
   * son similares, pero la referencia del objeto como tal es
   * diferente.
   *
   * @param a El nodo original.
   * @param b El nodo clon.
   */
  private void testNodes(Node a, Node b) {
    assertTrue(a.getDepth() == b.getDepth());
    assertTrue(a.getMaximunDepth() == b.getMaximunDepth());
    assertTrue(a.getFunction() == b.getFunction());
    assertTrue(a.getSymbol() == b.getSymbol());
    assertFalse(a == b);
    assertTrue(a.toString().equals(b.toString()));

  }
}
