package progen.kernel.grammar.validations;

import progen.kernel.grammar.Grammar;
import progen.kernel.grammar.GrammarNotValidException;

/**
 * @author jirsis
 *
 */
public interface Validation {
	public void validate(Grammar gram) throws GrammarNotValidException;
}
