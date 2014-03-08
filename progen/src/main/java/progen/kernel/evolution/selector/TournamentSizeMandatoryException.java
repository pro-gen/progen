package progen.kernel.evolution.selector;

import progen.kernel.error.Error;

public class TournamentSizeMandatoryException extends RuntimeException {

	/** Para serialización */
	private static final long serialVersionUID = 6841406532040966608L;

	public TournamentSizeMandatoryException() {
		super(Error.get(37).trim());
	}
}
