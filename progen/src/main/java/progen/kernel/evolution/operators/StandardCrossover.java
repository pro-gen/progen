package progen.kernel.evolution.operators;

import java.util.ArrayList;
import java.util.List;

import progen.context.ProGenContext;
import progen.kernel.evolution.GenneticOperator;
import progen.kernel.functions.Function;
import progen.kernel.population.Individual;
import progen.kernel.population.Population;
import progen.kernel.tree.Node;
import progen.kernel.tree.Tree;

/**
 * Implementación del operador genético de cruce estándar. El funcionamiento de
 * este operador consiste en seleccionar dos individuos de la población pasada
 * como parámetro, en función del selector que esté configurado.
 * 
 * Una vez seleccionados, se elige un nodo compatible de los dos árboles y se
 * intercambian los dichos nodos y nodos los nodos hijos a estos.
 * 
 * Se entenderá como nodo compatible con otro, todo aquel que contenga una
 * función que devuelva el mismo tipo de valor de retorno, esto es así, para que
 * cuando se intercambien los nodos, los nodos resultantes sigan estando bien
 * formados y sean palabras generadas por la gramática original de cada árbol.
 * 
 * @author jirsis
 * @since 1.0
 */
public class StandardCrossover extends GenneticOperator {

  @Override
  public List<Individual> evolve(Population population) {
    List<Individual> individuals = getSelector().select(population, 2);
    List<Individual> individualsCrossover = new ArrayList<Individual>();
    if (individuals.size() != 2) {
      throw new SelectorSizeIncorrectValueException(2, individuals.size());
    } else {
      Individual mother = individuals.get(0);
      Individual father = individuals.get(1);
      Object[] treesSet = (Object[]) mother.getTrees().keySet().toArray();
      String key = (String) treesSet[(int) (Math.random() * treesSet.length)];
      Tree treeA = mother.getTrees().get(key);
      Tree treeB = father.getTrees().get(key);

      // A ver que te parece así:
      boolean validCross = false;
      int tries = 0;
      boolean giveUp = false;
      while (!validCross && !giveUp) {
        if (crossTree(treeA, treeB)) {
          validCross = checkTrees(treeA, treeB);
        }
        if (++tries > 50) {
          giveUp = true;
        }
      }

      if (validCross) {
        individualsCrossover.add(mother);
        individualsCrossover.add(father);
      }

    }
    return individualsCrossover;
  }

  /**
   * Forma de cruzar dos árboles distintos. Se devuelven los árboles cruzados
   * sobre los mismos parámetros, que serán modificados convenientemente.
   * 
   * @param treeA
   *          Primer árbol a cruzar.
   * @param treeB
   *          Segundo árbol a cruzar.
   * @return <code>true</code> si ha sido posible seleccionar dos nodos y cruzar
   *         los árboles, <code>false</code> en caso contrario.
   */
  private boolean crossTree(Tree treeA, Tree treeB) {
    int branchPos1;
    int branchPos2;
    List<Node> crossNodes;
    Node crossNode1;
    Node crossNode2;
    Node parent1;
    Node parent2;
    boolean treesCrossed = false;

    crossNodes = selectNodes(treeA, treeB);
    if (crossNodes.size() > 0) {
      crossNode1 = crossNodes.get(0);
      crossNode2 = crossNodes.get(1);
      // guardamos los padres de los nodos de corte
      parent1 = crossNode1.getParent();
      parent2 = crossNode2.getParent();

      // guardamos la rama en la que estaban los nodos de corte
      branchPos1 = getBranch(crossNode1);
      branchPos2 = getBranch(crossNode2);
      // se separan los nodos seleccionados del arbol del que formaban parte
      crossNode1 = crossNode1.branch();
      crossNode2 = crossNode2.branch();
      // se asigna a cada padre, el nodo seleccionado del arbol opuesto
      parent1.setBranch(crossNode2, branchPos1);
      parent2.setBranch(crossNode1, branchPos2);
      treesCrossed = true;
    }
    return treesCrossed;
  }

  /**
   * Devuelve la posición que ocupa un nodo entre todos los nodos que comparten
   * su mismo padre.
   * 
   * @param node
   *          El nodo del que se desea saber que posición tiene entre todos sus
   *          hermanos.
   * @return La posición entre los distintos nodos hermano.
   */
  private int getBranch(Node node) {
    int branchPos = 0;
    for (int i = 0; i < node.getParent().getBranches().size(); i++) {
      if (node.getParent().getBranches().get(i) == node) {
        branchPos = i;
      }
    }
    return branchPos;
  }

  /**
   * Forma de seleccionar dos nodos compatible a partir de dos árboles dados. Al
   * estar en un método separado, es suficiente con reimplementar este método en
   * los distintos tipos de operadores de árbol, para que funcione
   * correctamente.
   * 
   * @param treeA
   *          Uno de los árboles a cruzar.
   * @param treeB
   *          El otro árbol a cruzar.
   * @return Devuelve una lista, con dos elementos por lo general, que son dos
   *         nodos compatibles entre ellos y en los que se puede aplicar el
   *         operador de cruce.
   */
  protected List<Node> selectNodes(Tree treeA, Tree treeB) {
    List<Node> nodes = new ArrayList<Node>();
    Node crossNode1;
    Node crossNode2;
    Function function1;
    Function function2;
    // se buscan los nodos de corte, sin tener en cuenta el nodo raiz
    crossNode1 = treeA.getNode(1 + (int) (Math.random() * treeA.getRoot().getTotalNodes() - 1));
    crossNode2 = treeB.getNode(1 + (int) (Math.random() * treeB.getRoot().getTotalNodes() - 1));
    // se recuperan las funciones de cada nodo
    function1 = crossNode1.getFunction().getFunction();
    function2 = crossNode2.getFunction().getFunction();
    // si la funcion no devuelve el mismo tipo, se busca otro nodo que si
    // que lo cumpla
    while (!function1.getReturnType().equals(function2.getReturnType())) {
      crossNode2 = treeB.getNode(1 + (int) (Math.random() * treeB.getRoot().getTotalNodes() - 1));
      function2 = crossNode2.getFunction().getFunction();
    }

    nodes.add(crossNode1);
    nodes.add(crossNode2);

    return nodes;
  }

  @edu.umd.cs.findbugs.annotations.SuppressWarnings(value = "NS_DANGEROUS_NON_SHORT_CIRCUIT", justification = "It's mandatory to evaluate both trees")
  private boolean checkTrees(Tree treeA, Tree treeB) {
    return checkTreeSize(treeA) & checkTreeSize(treeB);
  }

  private boolean checkTreeSize(Tree t) {
    int maxNodes = ProGenContext.getOptionalProperty("progen.population.max-nodes", Integer.MAX_VALUE);
    return (t.getRoot().getTotalNodes() <= maxNodes);
  }

}
