package com.ericsson.internal.dtra.projectmanagement.service.exception;

public class IllegalRequiredFieldAccessException extends RuntimeException {

  private static final long serialVersionUID = 6322565479212018015L;

  public IllegalRequiredFieldAccessException(Throwable cause) {
    super(cause);
  }

  public IllegalRequiredFieldAccessException(String msg, Throwable cause) {
    super(msg, cause);
  }

  public IllegalRequiredFieldAccessException(String msg) {
    super(msg);
  }

}
