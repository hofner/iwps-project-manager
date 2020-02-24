package com.ericsson.internal.dtra.projectmanagement.service.exception;

public class ForbiddenWorkflowActionException extends RuntimeException {

  private static final long serialVersionUID = -3187283988112642076L;

  public ForbiddenWorkflowActionException(Throwable cause) {
    super(cause);
  }

  public ForbiddenWorkflowActionException(String msg, Throwable cause) {
    super(msg, cause);
  }

  public ForbiddenWorkflowActionException(String msg) {
    super(msg);
  }
}
