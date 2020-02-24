package com.ericsson.internal.dtra.projectmanagement.web.exception;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ericsson.internal.dtra.projectmanagement.service.exception.BadRequestException;
import com.ericsson.internal.dtra.projectmanagement.service.exception.ForbiddenWorkflowActionException;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(BadRequestException.class)
  @ResponseBody
  public ErrorInfo handleBadRequestException(final HttpServletRequest req, final Exception ex) {
    LOGGER.error("A bad request application error occurred", ex);
    return new ErrorInfo(req.getRequestURL().toString(), ex);
  }

  @ResponseStatus(HttpStatus.FORBIDDEN)
  @ExceptionHandler(ForbiddenWorkflowActionException.class)
  @ResponseBody
  public ErrorInfo handleUnauthorizedWorkflowException(final HttpServletRequest req, final Exception ex) {
    LOGGER.error("An unauthorized workflow action was attempted by user: " + req.getUserPrincipal().getName(), ex);
    return new ErrorInfo(req.getRequestURL().toString(), ex);
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(DataRetrievalFailureException.class)
  @ResponseBody
  public ErrorInfo handleItemNotFound(final HttpServletRequest req, final Exception ex) {
    LOGGER.error("An item not found error occurred", ex);
    return new ErrorInfo(req.getRequestURL().toString(), ex);
  }

  @ResponseStatus(HttpStatus.CONFLICT)
  @ExceptionHandler(DataIntegrityViolationException.class)
  @ResponseBody
  public ErrorInfo handleDataIntegrityViolation(final HttpServletRequest req, final Exception ex) {
    LOGGER.error("A conflict application error occurred", ex);
    return new ErrorInfo(req.getRequestURL().toString(), ex);
  }

}
