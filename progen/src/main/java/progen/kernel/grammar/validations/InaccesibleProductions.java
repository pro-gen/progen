package progen.kernel.grammar.validations;

import java.util.List;
import java.util.ArrayList;

import progen.kernel.grammar.GrammarNonTerminalSymbol;
import progen.kernel.grammar.Grammar;
import progen.kernel.grammar.GrammarNotValidException;
import progen.kernel.grammar.Production;

/**
 * @author jirsis
 * 
 */
public class InaccesibleProductions implements Validation {

  private static final int ID_ERROR = 34;

  public void validate(Grammar gram) {
    boolean grammarOK = false;
    final GrammarNonTerminalSymbol axiom = gram.getAxiom();
    final List<Production> productionsToCheck = new ArrayList<Production>(gram.getProductions());
    final List<Production> productionsChecked = gram.getProductions(gram.getAxiom());
    Production toCheck;
    int index;
    int productionsToCheckBefore = productionsToCheck.size();
    int productionsToCheckAfter = 0;

    while (productionsToCheckBefore != productionsToCheckAfter) {

      index = 0;
      productionsToCheckBefore = productionsToCheck.size();
      for (int i = 0; i < productionsToCheckBefore; i++) {
        toCheck = productionsToCheck.get(index);
        if (!checkProduction(toCheck, productionsChecked, axiom, productionsToCheck)) {
          index++;
        }

      }
      productionsToCheckAfter = productionsToCheck.size();
    }

    if (productionsToCheck.size() == 0) {
      grammarOK = true;
    }
    if (!grammarOK) {
      throw new GrammarNotValidException(ID_ERROR);
    }
  }

  private boolean checkProduction(Production p, List<Production> productionsChecked, GrammarNonTerminalSymbol axiom, List<Production> productionsToCheck) {
    boolean ok = false;

    if (productionsChecked.contains(p)) {
      ok = true;
      productionsToCheck.remove(p);
    } else if (isGeneretatedBy(p, productionsChecked)) {
      productionsChecked.add(p);
      productionsToCheck.remove(p);
      ok = true;
    }
    return ok;
  }

  private boolean isGeneretatedBy(Production p, List<Production> productionsChecked) {
    boolean isGenerated = false;
    for (Production checked : productionsChecked) {
      isGenerated |= checked.isSymbolGenerated(p.getLeft());
    }
    return isGenerated;
  }

}
