package progen.roles.distributed;

import progen.kernel.error.Error;

public class ProGenDistributedException extends RuntimeException {

  private static final String BLANK_SPACE_SYMBOL = " ";
  private static final String SQUARE_LEFT_BRACKET_SYMBOL = "[";
  private static final String SQUARE_RIGHT_BRACKET_SYMBOL = "]";
  private static final int ID_ERROR = 40;
  private static final long serialVersionUID = 8992108251561026696L;

  public ProGenDistributedException() {
    super(Error.get(ID_ERROR));
  }

  public ProGenDistributedException(Exception originalException) {
    super(Error.get(ID_ERROR), originalException);
  }

  public ProGenDistributedException(String msg) {
    super(getMessage(ID_ERROR, msg));
  }

  public ProGenDistributedException(String msg, Exception originalException) {
    super(getMessage(ID_ERROR, msg), originalException);
  }

  public ProGenDistributedException(int idException, String msg) {
    super(getMessage(idException, msg));
  }

  private static String getMessage(int idError, String msg) {
    return Error.get(idError) + BLANK_SPACE_SYMBOL + SQUARE_LEFT_BRACKET_SYMBOL + msg + SQUARE_RIGHT_BRACKET_SYMBOL;
  }

}
