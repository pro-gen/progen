package progen.kernel.evolution.selector;

import progen.kernel.error.Error;

public class TournamentSizeMandatoryException extends RuntimeException {

  private static final int ID_ERROR = 37;
  private static final long serialVersionUID = 6841406532040966608L;

  public TournamentSizeMandatoryException() {
    super(getMessageException());
  }
  public TournamentSizeMandatoryException(Exception originalException) {
    super(getMessageException(), originalException);
  }
  private static String getMessageException() {
    return Error.get(ID_ERROR).trim();
  }
}
