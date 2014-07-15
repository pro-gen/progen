package progen.roles;

import progen.kernel.error.Error;

public class UnknownRoleImplementationException extends RuntimeException {

  private static final int ID_ERROR = 38;
  private static final long serialVersionUID = 1842918295160613392L;

  public UnknownRoleImplementationException(String msg) {
    super(Error.get(ID_ERROR) + "[" + msg + "]");
  }

}
