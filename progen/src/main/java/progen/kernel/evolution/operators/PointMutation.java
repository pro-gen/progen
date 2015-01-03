package progen.kernel.evolution.operators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import progen.kernel.error.Warning;
import progen.kernel.evolution.GenneticOperator;
import progen.kernel.grammar.Grammar;
import progen.kernel.grammar.GrammarTerminalSymbol;
import progen.kernel.grammar.Production;
import progen.kernel.population.Individual;
import progen.kernel.population.Population;
import progen.kernel.tree.Node;
import progen.kernel.tree.Tree;

/**
 * Clase que implementa el operador genético de mutación de punto.
 * 
 * Este operador selecciona un individuo en función del selector que tenga
 * configurado y selecciona de forma aleatoria un árbol de todos los que definen
 * al individuo.
 * 
 * Una vez se tiene un árbol concreto, se selecciona un nodo de entre todos los
 * posibles y se busca colocar una función que sea compatible con la que esté en
 * ese nodo de entre todas las posibles que se puedan generar a partir de la
 * gramática original.
 * 
 * Cuando una función es compatible con otra, se entiende una función tal que
 * tenga la misma signatura (número de argumentos necesarios) y mismo valor de
 * retorno.
 * 
 * @author jirsis
 * @since 1.0
 */
public class PointMutation extends GenneticOperator {

  @Override
  public List<Individual> evolve(Population population) {
    final List<Individual> individuals = getSelector().select(population, 1);
    final List<Individual> individualsMutate = new ArrayList<Individual>();

    final Individual individualMutate = individuals.get(0);

    final Object[] treesSet = individualMutate.getTrees().keySet().toArray();
    final String idTree = (String) (treesSet[(int) (Math.random() * treesSet.length)]);
    mutate(individualMutate.getTrees().get(idTree), individualMutate.getGrammars().get(idTree));

    individualsMutate.add(individualMutate);
    return individualsMutate;
  }

  /**
   * Selecciona un nodo cualquiera del árbol pasado por parámetro y cambia la
   * función que está en ese nodo por otra función compatible, en función de las
   * disponibles en la gramática que generó ese árbol.
   * 
   * @param tree
   *          El árbol a mutar.
   * @param grammar
   *          La gramática para obtener la nueva función.
   */
  private void mutate(Tree tree, Grammar grammar) {
    boolean mutate = false;
    
    final List<Integer> nodes =generateRandomNodeList(tree);

    while (!mutate && hasMoreCandidateNodes(nodes)) {
      final int nodeMutate = nodes.remove(0);

      final Node node = tree.getNode(nodeMutate);
      final List<Production> productions = grammar.getProductionsCompatibleWithFunction(node.getSymbol(), node.getFunction());
      final Production production = getProductionCompatible(node, productions);

      if (hasMoreProductionsAvailable(productions)) {
        node.setFunction(production.getFunction());
        mutate = true;
      }
    }
    
    warningIfNotMutation(mutate);
  }

  private Production getProductionCompatible(final Node node, final List<Production> productions) {
    Production production = productions.remove((int) (Math.random() * productions.size()));
    while (productionCompatibility(node, productions, production)) {
      production = productions.remove((int) (Math.random() * productions.size()));
    }
    return production;
  }

  private void warningIfNotMutation(boolean mutate) {
    if (!mutate) {
      Warning.show(1);
    }
  }

  private boolean hasMoreCandidateNodes(final List<Integer> nodes) {
    return nodes.size() > 0;
  }

  private boolean productionCompatibility(final Node node, final List<Production> productions, Production production) {
    return (nodeFunctionEqualsProductionFunction(node.getFunction(), production.getFunction()) || productionHasSameNodeArity(node, production)) && hasMoreProductionsAvailable(productions);
  }

  private boolean hasMoreProductionsAvailable(final List<Production> productions) {
    return productions.size() > 0;
  }

  private boolean productionHasSameNodeArity(final Node node, Production production) {
    return production.getArgs().length != node.getFunction().getFunction().getArity();
  }

  private boolean nodeFunctionEqualsProductionFunction(final GrammarTerminalSymbol nodeFunction, GrammarTerminalSymbol productionFunction) {
    return productionFunction.compareTo(nodeFunction) == 0;
  }

  private List<Integer> generateRandomNodeList(Tree tree) {
    final List<Integer> nodes = new ArrayList<Integer>();
    for (int i = 0; i < tree.getRoot().getTotalNodes(); i++){
      nodes.add(i);
    }
    Collections.shuffle(nodes);
    return nodes;
  }
  
}
