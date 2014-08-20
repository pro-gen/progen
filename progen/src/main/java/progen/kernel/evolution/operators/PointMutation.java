package progen.kernel.evolution.operators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import progen.kernel.error.Warning;
import progen.kernel.evolution.GenneticOperator;
import progen.kernel.grammar.Grammar;
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
    final List<Integer> nodes = new ArrayList<Integer>();
    // se genera una lista aleatoria con los nodos a intentar mutar
    for (int i = 0; i < tree.getRoot().getTotalNodes(); i++)
      nodes.add(i);
    Collections.shuffle(nodes);

    // mientras no se haya mutado ya el arbol y queden nodos candidatos
    while (!mutate && nodes.size() > 0) {
      final int nodeMutate = nodes.remove(0);

      final Node node = tree.getNode(nodeMutate);
      final List<Production> productions = grammar.getProductionsCompatibleWithFunction(node.getSymbol(), node.getFunction());
      Production production = productions.remove((int) (Math.random() * productions.size()));
      /*
       * se busca una producción que tenga la misma aridad en la función y que
       * sea distinta a la que se uso para generar este nodo, mientras queden
       * producciones disponibles
       */
      while ((production.getFunction().compareTo(node.getFunction()) == 0 || production.getArgs().length != node.getFunction().getFunction().getArity()) && productions.size() > 0) {

        production = productions.remove((int) (Math.random() * productions.size()));
      }

      // si no se han eliminado todas las producciones disponibles, se muta el
      // nodo
      if (productions.size() > 0) {
        node.setFunction(production.getFunction());
        mutate = true;
      }
    }
    // no se ha podido mutar ningún nodo del árbol.
    if (!mutate) {
      Warning.show(1);
    }
  }

}
