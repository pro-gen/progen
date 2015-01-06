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

  @Override
  public void validate(Grammar gram) {
    final List<GrammarNonTerminalSymbol> symbolsChecked = new ArrayList<GrammarNonTerminalSymbol>();
    final List<GrammarNonTerminalSymbol> symbolsToCheck = new ArrayList<GrammarNonTerminalSymbol>(gram.getGrammarNonTerminalSymbols());
    GrammarNonTerminalSymbol symbol;
    int symbolsToCheckBefore = symbolsToCheck.size();
    int symbolsToCheckAfter = 0;
    int index;

    while (symbolsToCheckBefore != symbolsToCheckAfter) {
      symbolsToCheckBefore = symbolsToCheck.size();
      index = 0;
      for (int i = 0; i < symbolsToCheckBefore; i++) {
        symbol = symbolsToCheck.get(index);

        index=checkSymbolUsedByAnyProduction(gram, symbolsChecked, symbolsToCheck, symbol, index);
      }
      symbolsToCheckAfter = symbolsToCheck.size();
    }
    isGrammarOk(symbolsToCheck);
  }

  private int checkSymbolUsedByAnyProduction(Grammar gram, final List<GrammarNonTerminalSymbol> symbolsChecked, final List<GrammarNonTerminalSymbol> symbolsToCheck, GrammarNonTerminalSymbol symbol, int index) {
    int indexUsed=0;
    if (checkSymbol(symbolsChecked, gram.getProductions(symbol))) {
      symbolsChecked.add(symbol);
      symbolsToCheck.remove(symbol);
    } else {
      indexUsed=index+1;
    }
    return indexUsed;
  }

  private void isGrammarOk(final List<GrammarNonTerminalSymbol> symbolsToCheck) {
    final boolean grammarOK=symbolsToCheck.size() == 0;
    if (!grammarOK) {
      throw new GrammarNotValidException(GrammarNotValidExceptionEnum.SUPERFLUOUS_PRODUCTION_ERROR);
    }
  }

  private boolean checkSymbol(List<GrammarNonTerminalSymbol> symbolsChecked, List<Production> productions) {
    boolean isOK = false;

    for (Production p : productions) {
      if (p.getArgs().length == 0) {
        isOK = true;
      } else {
        for (GrammarNonTerminalSymbol arg : p.getArgs()) {
          if (symbolsChecked.contains(arg)) {
            isOK |= true;
          }
        }
      }
    }

    return isOK;
  }

}