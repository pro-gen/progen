package progen.kernel.evolution.operators;

import java.util.ArrayList;
import java.util.List;

import progen.kernel.evolution.GenneticOperator;
import progen.kernel.grammar.Grammar;
import progen.kernel.population.Individual;
import progen.kernel.population.Population;
import progen.kernel.tree.IncompatibleOptionsInitTreeMethodException;
import progen.kernel.tree.InitializeTreeMethod;
import progen.kernel.tree.Node;
import progen.kernel.tree.Tree;

/**
 * Implementación del operador de mutación de árbol, consistente en seleccionar
 * un nodo de forma aleatoria y genererar un nuevo árbol compatible a partir de
 * ese nodo.
 * 
 * @author jirsis
 * @since 2.0
 */
public class TreeMutation extends GenneticOperator {

  @Override
  public List<Individual> evolve(Population population) {
    final List<Individual> individuals = getSelector().select(population, 1);
    final List<Individual> individualsMutate = new ArrayList<Individual>();

    final Individual individualMutate = individuals.get(0);

    final Object[] treesSet = individualMutate.getTrees().keySet().toArray();
    final String idTree = (String) (treesSet[(int) (Math.random() * treesSet.length)]);

    try {
      mutate(individualMutate.getTrees().get(idTree), individualMutate.getGrammars().get(idTree));
      individualsMutate.add(individualMutate);
    } catch (IncompatibleOptionsInitTreeMethodException e) {
      // the selected node is not possible to evolve, and ignore it
    }

    return individualsMutate;
  }

  /**
   * Muta un árbol concreto utilizando la misma gramática que se utilizó para
   * generarlo.
   * 
   * @param tree
   *          El árbol a mutar.
   * @param grammar
   *          La gramática que se utilizó para crearlo.
   */
  private void mutate(Tree tree, Grammar grammar) {
    final int nodeMutate = (int) (Math.random() * tree.getRoot().getTotalNodes());
    final Node node = tree.getNode(nodeMutate);
    node.clearNode();

    final InitializeTreeMethod init = tree.getInitializeTreeMethod();
    init.generate(grammar, node);
  }

}
