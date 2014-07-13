/**
 * 
 */
package progen.kernel.hypergp;

import java.util.ArrayList;
import java.util.List;

import progen.kernel.functions.Function;
import progen.kernel.grammar.Grammar;
import progen.kernel.grammar.GrammarTerminalSymbol;
import progen.kernel.grammar.Production;

/**
 * Gramática específica para la evaluación de HiperGP.
 * 
 * @author jirsis
 * @since v2.0
 */
public class HGPGrammar extends Grammar {

  private static final long serialVersionUID = 8359953232850164115L;

  /**
   * Constructor por defecto en el que se especifica el identificador del árbol,
   * para cargar la gramática correspondiente.
   * 
   * @param idTree
   */
  public HGPGrammar(String idTree) {
    super(idTree);
    transformGrammar();
  }

  /**
   * Convierte una gramática normal, en una en la que todos los operadores están
   * agrupados para generar formas de árbol, en función de su signatura.
   */
  @edu.umd.cs.findbugs.annotations.SuppressWarnings(value="BC_UNCONFIRMED_CAST_OF_RETURN_VALUE", justification="Inherit code")
  private void transformGrammar() {
    Production productionHGP;
    Function wildCard;
    int totalProductions = super.getProductions().size();
    List<Production> productions = new ArrayList<Production>((ArrayList<Production>) super.getProductions());

    for (int i = 0; i < totalProductions; i++) {
      Production p = super.getProductions().get(0);
      wildCard = getWildCard(p, productions);
      productionHGP = new Production(p.getLeft(), new GrammarTerminalSymbol(wildCard), p.getArgs());
      if (!super.getProductions().contains(productionHGP)) {
        super.getProductions().add(productionHGP);
      }
      super.getProductions().remove(0);
    }
  }

  private Function getWildCard(Production production, List<Production> productions) {
    WildCard wildCard = new WildCard(production.getFunction().getFunction());

    for (Production pAux : productions) {
      if (!pAux.equals(production)) { // si no son la misma produccion, se
                                      // comparan todos los elementos
        if (wildCard.getSignature().equals(pAux.getFunction().getFunction().getSignature())) {
          wildCard.addFunction(pAux.getFunction().getFunction());
        }
      }
    }
    return wildCard;
  }

}
