package com.ericsson.internal.dtra.projectmanagement.web.exception;

public class ErrorInfo {
  public final String url;
  public final String cause;

  public ErrorInfo(final String url, final Exception ex) {
    this.url = url;
    this.cause = ex.getLocalizedMessage();
  }
}
