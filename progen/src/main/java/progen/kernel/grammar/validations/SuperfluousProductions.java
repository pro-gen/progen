package progen.kernel.grammar.validations;

import java.util.ArrayList;
import java.util.List;

import progen.kernel.grammar.GrammarNonTerminalSymbol;
import progen.kernel.grammar.Grammar;
import progen.kernel.grammar.GrammarNotValidException;
import progen.kernel.grammar.Production;

/**
 * @author jirsis
 * 
 */
public class SuperfluousProductions implements Validation {

  /*
   * (non-Javadoc)
   * 
   * @see
   * progen.kernel.grammar.validations.Validation#validate(progen.kernel.grammar
   * .Grammar, progen.kernel.grammar.validations.Validation)
   */
  public void validate(Grammar gram) {
    boolean grammarOK = false;
    List<GrammarNonTerminalSymbol> symbolsChecked = new ArrayList<GrammarNonTerminalSymbol>();
    List<GrammarNonTerminalSymbol> symbolsToCheck = new ArrayList<GrammarNonTerminalSymbol>(gram.getGrammarNonTerminalSymbols());
    GrammarNonTerminalSymbol symbol;
    int symbolsToCheckBefore = symbolsToCheck.size();
    int symbolsToCheckAfter = 0;
    int index;

    while (symbolsToCheckBefore != symbolsToCheckAfter) {
      symbolsToCheckBefore = symbolsToCheck.size();
      index = 0;
      for (int i = 0; i < symbolsToCheckBefore; i++) {
        symbol = symbolsToCheck.get(index);

        if (checkSymbol(symbol, symbolsChecked, gram.getProductions(symbol))) {
          symbolsChecked.add(symbol);
          symbolsToCheck.remove(symbol);
        } else {
          index++;
        }
      }
      symbolsToCheckAfter = symbolsToCheck.size();
    }

    if (symbolsToCheck.size() == 0) {
      grammarOK = true;
    }

    if (!grammarOK) {
      throw new GrammarNotValidException(35);
    }
  }

  private boolean checkSymbol(GrammarNonTerminalSymbol symbol, List<GrammarNonTerminalSymbol> symbolsChecked, List<Production> productions) {
    boolean isOK = false;

    for (Production p : productions) {
      if (p.getArgs().length == 0) {
        isOK = true;
      } else {
        for (GrammarNonTerminalSymbol arg : p.getArgs()) {
          if (symbolsChecked.contains(arg)) {
            isOK = isOK || true;
          }
        }
      }
    }

    return isOK;
  }

}