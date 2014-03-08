package progen.kernel.evolution.selector;

import progen.kernel.error.Error;

public class TournamentSizeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6841406532040966608L;
	
	
	public TournamentSizeException() {
		super(Error.get(36).trim());
	}
}
