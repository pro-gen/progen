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

    /*
     * (non-Javadoc)
     * 
     * @see
     * progen.kernel.grammar.validations.Validation#validate(progen.kernel.grammar
     * .Grammar, progen.kernel.grammar.validations.Validation)
     */
    public void validate(Grammar gram) {
	boolean grammarOK = false;
	GrammarNonTerminalSymbol axiom = gram.getAxiom();
	List<Production> productionsToCheck = new ArrayList<Production>(
		gram.getProductions());
	List<Production> productionsChecked = gram.getProductions(gram
		.getAxiom());
	Production toCheck;
	int index;
	int productionsToCheckBefore = productionsToCheck.size();
	int productionsToCheckAfter = 0;

	while (productionsToCheckBefore != productionsToCheckAfter) {

	    index = 0;
	    productionsToCheckBefore = productionsToCheck.size();
	    for (int i = 0; i < productionsToCheckBefore; i++) {
		toCheck = productionsToCheck.get(index);
		if (!checkProduction(toCheck, productionsChecked, axiom,
			productionsToCheck)) {
		    index++;
		}

	    }
	    productionsToCheckAfter = productionsToCheck.size();
	}

	if (productionsToCheck.size() == 0) {
	    grammarOK = true;
	}
	if(!grammarOK){
	    throw new GrammarNotValidException(34);
	}
    }

    private boolean checkProduction(Production p,
	    List<Production> productionsChecked,
	    GrammarNonTerminalSymbol axiom, List<Production> productionsToCheck) {
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

    private boolean isGeneretatedBy(Production p,
	    List<Production> productionsChecked) {
	boolean isGenerated = false;
	for (Production checked : productionsChecked) {
	    isGenerated |= checked.isSymbolGenerated(p.getLeft());
	}
	return isGenerated;
    }

}
