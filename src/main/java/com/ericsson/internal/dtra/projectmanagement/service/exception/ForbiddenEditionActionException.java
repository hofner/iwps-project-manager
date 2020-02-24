package com.ericsson.internal.dtra.projectmanagement.service.exception;

public class ForbiddenEditionActionException extends RuntimeException {

  private static final long serialVersionUID = 5095512537597407988L;

  public ForbiddenEditionActionException(Throwable cause) {
    super(cause);
  }

  public ForbiddenEditionActionException(String msg, Throwable cause) {
    super(msg, cause);
  }

  public ForbiddenEditionActionException(String msg) {
    super(msg);
  }
}
