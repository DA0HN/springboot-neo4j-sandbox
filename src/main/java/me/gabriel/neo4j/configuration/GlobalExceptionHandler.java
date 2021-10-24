package me.gabriel.neo4j.configuration;

import me.gabriel.neo4j.application.api.response.ErrorResponse;
import me.gabriel.neo4j.core.domain.InvalidStateException;
import me.gabriel.neo4j.core.domain.SandboxDomainException;
import me.gabriel.neo4j.core.domain.StudentNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  private final Logger log = LoggerFactory.getLogger(this.getClass());


  @ExceptionHandler(InvalidStateException.class)
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  public ResponseEntity<ErrorResponse> handleInvalidStateException(
    final InvalidStateException exception,
    final WebRequest webRequest
  ) {
    this.log.error("Failed to process element");
    return this.buildErrorResponse(
      exception.getMessage(),
      HttpStatus.UNPROCESSABLE_ENTITY,
      webRequest
    );
  }

  private ResponseEntity<ErrorResponse> buildErrorResponse(
    final String message,
    final HttpStatus httpStatus,
    final WebRequest webRequest
  ) {
    return ResponseEntity.status(httpStatus).body(
      new ErrorResponse(
        httpStatus.value(),
        message,
        webRequest.getContextPath()
      )
    );
  }

  @ExceptionHandler(StudentNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<ErrorResponse> handleStudentNotFoundException(
    final StudentNotFoundException exception,
    final WebRequest webRequest
  ) {
    this.log.error("Failed to fetch element");
    return this.buildErrorResponse(
      exception.getMessage(),
      HttpStatus.NOT_FOUND,
      webRequest
    );
  }

  @ExceptionHandler(SandboxDomainException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<ErrorResponse> handleSandboxDomainException(
    final SandboxDomainException exception,
    final WebRequest webRequest
  ) {
    this.log.error("A domain exception occurred", exception);
    return this.buildErrorResponse(
      exception.getMessage(),
      HttpStatus.INTERNAL_SERVER_ERROR,
      webRequest
    );
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<ErrorResponse> handleAllUncaughtException(
    final Exception exception,
    final WebRequest webRequest
  ) {
    this.log.error("Unknown error occurred", exception);
    return this.buildErrorResponse(
      exception.getMessage(),
      HttpStatus.INTERNAL_SERVER_ERROR,
      webRequest
    );
  }

  //  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  //  protected ResponseEntity<Object> handleMethodArgumentNotValid(
  //    final MethodArgumentNotValidException exception,
  //    final WebRequest webRequest
  //  ) {
  //  }

  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  @Override protected ResponseEntity<Object> handleMethodArgumentNotValid(
    final MethodArgumentNotValidException ex,
    final HttpHeaders headers,
    final HttpStatus status,
    final WebRequest request
  ) {
    final var errorResponse = new ErrorResponse(
      HttpStatus.UNPROCESSABLE_ENTITY.value(),
      "Validation error. Check 'errors' field for details.",
      request.getContextPath()
    );

    for(final var fieldError : ex.getBindingResult().getFieldErrors()) {
      errorResponse.addValidationError(
        fieldError.getField(),
        fieldError.getDefaultMessage()
      );
    }
    return ResponseEntity.unprocessableEntity().body(errorResponse);
  }
}
