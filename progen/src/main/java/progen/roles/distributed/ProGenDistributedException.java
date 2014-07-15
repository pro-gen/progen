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

  public ProGenDistributedException(String msg) {
    super(Error.get(ID_ERROR) + BLANK_SPACE_SYMBOL+SQUARE_LEFT_BRACKET_SYMBOL + msg + SQUARE_RIGHT_BRACKET_SYMBOL);
  }

  public ProGenDistributedException(int id, String msg) {
    super(Error.get(id) + BLANK_SPACE_SYMBOL+ SQUARE_LEFT_BRACKET_SYMBOL + msg + SQUARE_RIGHT_BRACKET_SYMBOL);
  }

}
