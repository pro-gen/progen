package progen.kernel.grammar.validations;

import progen.kernel.grammar.GrammarNonTerminalSymbol;
import progen.kernel.grammar.Grammar;
import progen.kernel.grammar.GrammarNotValidException;

/**
 * Check if the grammar have any production like Ax :: = xxx
 * 
 * @author jirsis
 * 
 */
public class GrammarNonTerminalSymbolProduction implements Validation {

  private static final int ID_ERROR = 33;

  public void validate(Grammar gram) {
    boolean grammarOK = true;

    for (GrammarNonTerminalSymbol nonTerminal : gram.getGrammarNonTerminalSymbols()) {
      grammarOK &= gram.getProductions(nonTerminal).size() > 0;
    }

    if (!grammarOK) {
      throw new GrammarNotValidException(ID_ERROR);
    }
  }

}
