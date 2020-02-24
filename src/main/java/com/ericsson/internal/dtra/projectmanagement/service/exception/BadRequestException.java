package com.ericsson.internal.dtra.projectmanagement.service.exception;

public class BadRequestException extends RuntimeException {

  private static final long serialVersionUID = 7395284659207813889L;

  public BadRequestException(Throwable cause) {
    super(cause);
  }

  public BadRequestException(String msg, Throwable cause) {
    super(msg, cause);
  }

  public BadRequestException(String msg) {
    super(msg);
  }
}
