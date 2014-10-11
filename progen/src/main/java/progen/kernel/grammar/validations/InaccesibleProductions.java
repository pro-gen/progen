package progen.kernel.grammar.validations;

import java.util.ArrayList;
import java.util.List;

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
        if (!checkProduction(toCheck, productionsChecked, productionsToCheck)) {
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

  private boolean checkProduction(Production production, List<Production> productionsChecked, List<Production> productionsToCheck) {
    boolean checkedOk = false;

    if (productionsChecked.contains(production)) {
      checkedOk = true;
      productionsToCheck.remove(production);
    } else if (isGeneretatedBy(production, productionsChecked)) {
      productionsChecked.add(production);
      productionsToCheck.remove(production);
      checkedOk = true;
    }
    return checkedOk;
  }

  private boolean isGeneretatedBy(Production production, List<Production> productionsChecked) {
    boolean isGenerated = false;
    for (Production checked : productionsChecked) {
      isGenerated |= checked.isSymbolGenerated(production.getLeft());
    }
    return isGenerated;
  }

}
