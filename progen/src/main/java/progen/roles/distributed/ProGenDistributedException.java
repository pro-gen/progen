package progen.roles.distributed;

import progen.kernel.error.Error;

public class ProGenDistributedException extends RuntimeException {

  private static final long serialVersionUID = 8992108251561026696L;

  public ProGenDistributedException() {
    super(Error.get(40));
  }

  public ProGenDistributedException(String msg) {
    super(Error.get(40) + " [" + msg + "]");
  }

  public ProGenDistributedException(int id, String msg) {
    super(Error.get(id) + " [" + msg + "]");
  }

}
