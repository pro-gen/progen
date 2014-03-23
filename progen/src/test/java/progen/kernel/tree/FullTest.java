package progen.kernel.tree;

import org.junit.Before;
import org.junit.Test;
import progen.context.ProGenContext;
import progen.kernel.grammar.Grammar;
import progen.kernel.tree.Full;
import progen.kernel.tree.IncompatibleOptionsInitTreeMethodException;
import progen.kernel.tree.Node;

import static org.junit.Assert.assertTrue;

public class FullTest {

  /**
   * Gramática que generará los árboles.
   */
  private Grammar grammar;
  /**
   * Nodo raíz del árbol.
   */
  private Node node;
  /**
   * Inicializador de árboles de tipo FULL.
   */
  private Full full;
  /**
   * Profundidad minima del árbol.
   */
  private int minDepth;
  /**
   * Profundidad máxima del árbol.
   */
  private int maxDepth;
  /**
   * Número máximo de nodos
   */
  private int maxNodes;

  /**
   * Método de inicialización del test.
   *
   * @throws Exception
   */
  @Before
  public void setUp() throws Exception {
    minDepth = 3;
    maxDepth = 5;
    ProGenContext.makeInstance();
    ProGenContext.setProperty("progen.functionSet0", "DoublePlus, DoubleX");
    ProGenContext.setProperty("progen.functionSet0.return", "double");
    ProGenContext.setProperty("progen.total.RPB", "1");
    ProGenContext.setProperty("progen.RPB0.functionSet", "0");
    ProGenContext.setProperty("progen.population.init-depth-interval", minDepth + "," + maxDepth);
    ProGenContext.setProperty("progen.population.max-nodes", Integer.MAX_VALUE + "");
    ProGenContext.setProperty("progen.activeSearch", "true");
    grammar = Grammar.makeInstance("RPB0");
  }

  /**
   * Comprueba que se definan todas las propiedades que necesita el método de forma obligatoria.
   * Con esto se asegura que el resto de pruebas pueden funcionar.
   */
  @Test
  public void testFull() {
    full = new Full();
    assertTrue(true);
  }


  @Test
  public void testGenerate() {
    full = new Full();
    node = new Node(grammar.getAxiom());
    full.generate(grammar, node);
    assertTrue(node.getMaximunDepth() >= minDepth);
    assertTrue(node.getMaximunDepth() <= maxDepth);
  }

  @Test
  public void testGenerateMinimunDepth() {
    node = new Node(grammar.getAxiom());
    //DEPTH = 5
    ProGenContext.setProperty("progen.population.init-depth-interval", maxDepth + "");
    full = new Full();
    full.generate(grammar, node);
    assertTrue(node.getMaximunDepth() <= maxDepth);
    assertTrue(node.getMaximunDepth() >= minDepth);

    maxDepth = 10;
    ProGenContext.setProperty("progen.population.init-depth-interval", maxDepth + "");
    node = new Node(grammar.getAxiom());
    full = new Full();
    full.generate(grammar, node);
    assertTrue(node.getMaximunDepth() <= maxDepth);
    assertTrue(node.getMaximunDepth() >= minDepth);
  }

  @Test
  public void testGenerateMaximunNodes() {
    maxNodes = 30;
    ProGenContext.setProperty("progen.population.max-nodes", maxNodes + "");
    node = new Node(grammar.getAxiom());
    full = new Full();
    full.generate(grammar, node);
    assertTrue(node.getMaximunDepth() <= maxDepth);
    assertTrue(node.getMaximunDepth() >= minDepth);
    assertTrue(node.getTotalNodes() <= maxNodes);
  }

  /**
   * Comprueba si es capaz de generar un árbol con un único nodo que contendrá un DoubleX,
   * con una profundidad maxima y minima de 1.
   */
  @Test
  public void testGenerateMinimunTree() {
    maxNodes = 1;
    maxDepth = 0;
    ProGenContext.setProperty("progen.population.max-nodes", maxNodes + "");
    ProGenContext.setProperty("progen.population.init-depth-interval", maxDepth + "");
    node = new Node(grammar.getAxiom());
    full = new Full();
    full.generate(grammar, node);
    assertTrue(node.getMaximunDepth() <= maxDepth);
    assertTrue(node.getTotalNodes() <= maxNodes);
  }

  @Test
  public void testGenerateTree3_3_15() {
    maxNodes = 15;
    maxDepth = 3;
    ProGenContext.setProperty("progen.population.max-nodes", maxNodes + "");
    ProGenContext.setProperty("progen.population.init-depth-interval", maxDepth + "");
    node = new Node(grammar.getAxiom());
    full = new Full();
    full.generate(grammar, node);
    assertTrue(node.getMaximunDepth() <= maxDepth);
    assertTrue(node.getMaximunDepth() >= minDepth);
    assertTrue(node.getTotalNodes() <= maxNodes);
  }

  @Test(expected = IncompatibleOptionsInitTreeMethodException.class)
  public void testNoGenerate() {
    maxNodes = 8;
    ProGenContext.setProperty("progen.population.max-nodes", maxNodes + "");
    node = new Node(grammar.getAxiom());
    full = new Full();
    full.generate(grammar, node);
    assertTrue(node.getMaximunDepth() <= maxDepth);
    assertTrue(node.getMaximunDepth() >= minDepth);
    assertTrue(node.getTotalNodes() <= maxNodes);
  }

  @Test
  public void testGpHashDyn() {
    minDepth = 2;
    maxDepth = 10;
    maxNodes = 40;
    ProGenContext.setProperty("progen.total.RPB", "1");
    ProGenContext.setProperty("progen.RPB0.functionSet", "0");
    ProGenContext.setProperty("progen.functionSet0", "BitAnd, BitOr, BitXor, BitNot, BitMult, BitSum, BitVrotd, A0, Hval, Bit32ERC");
    ProGenContext.setProperty("progen.functionSet0.return", "Integer");
    ProGenContext.setProperty("progen.population.init-depth-interval", minDepth + "," + maxDepth);
    ProGenContext.setProperty("progen.population.max-nodes", maxNodes + "");
    ProGenContext.setProperty("progen.experiment.file", "app.gpHashDyn.gpHashDyn.txt");

    grammar = Grammar.makeInstance("RPB0");
    node = new Node(grammar.getAxiom());
    full = new Full();
    full.generate(grammar, node);
    assertTrue(node.getMaximunDepth() <= maxDepth);
    assertTrue(node.getMaximunDepth() >= minDepth);
    assertTrue(node.getTotalNodes() <= maxNodes);
  }

  @Test(timeout = 2000000)
  public void testSimulatePopulation() {
    int size = 1000;
    for (int i = 0; i < size; i++) {
      testGpHashDyn();
    }
  }

  //	@Test(timeout=20000)
  public void testComplexGrammar() {
    minDepth = 5;
    maxDepth = 16;
    maxNodes = 10000;
    ProGenContext.setProperty("progen.total.RPB", "1");
    ProGenContext.setProperty("progen.RPB0.functionSet", "0");
    ProGenContext.setProperty("progen.functionSet0", "DoublePlus, DoubleX, DoubleMinus, DoubleMult, DoubleY");
    ProGenContext.setProperty("progen.functionSet0.return", "double");
    ProGenContext.setProperty("progen.population.init-depth-interval", minDepth + "," + maxDepth);
    ProGenContext.setProperty("progen.population.max-nodes", maxNodes + "");

    grammar = Grammar.makeInstance("RPB0");
    node = new Node(grammar.getAxiom());
    full = new Full();
    full.generate(grammar, node);
    assertTrue(node.getMaximunDepth() <= maxDepth);
    assertTrue(node.getMaximunDepth() >= minDepth);
    assertTrue(node.getTotalNodes() <= maxNodes);
  }

  @Test
  public void testGenerateMinimunDepthAttempts() {
    ProGenContext.setProperty("progen.activeSearch", "false");
    testGenerateMinimunDepth();
  }

  @Test
  public void testMixedFunctionTrees() {
    ProGenContext.setProperty("progen.functionSet0", "DoublePlus, DoubleX, DoubleMinus, DoubleMult, DoubleY, DoubleEquals");
    ProGenContext.setProperty("progen.functionSet0.return", "boolean");
    ProGenContext.setProperty("progen.population.init-depth-interval", "2");

    grammar = Grammar.makeInstance("RPB0");
    node = new Node(grammar.getAxiom());
    full = new Full();
    full.generate(grammar, node);
    assertTrue(node.getMaximunDepth() <= maxDepth);
    assertTrue(node.getMaximunDepth() >= minDepth);
  }
}
