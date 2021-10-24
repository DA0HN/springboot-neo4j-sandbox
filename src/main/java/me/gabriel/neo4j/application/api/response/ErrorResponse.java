package me.gabriel.neo4j.application.api.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

@Getter
@Setter
@RequiredArgsConstructor
public class ErrorResponse {

  private final int status;
  private final String message;
  private final String path;
  private List<ValidationError> errors;


  public void addValidationError(final String field, final String message) {
    if(Objects.isNull(this.errors)) {
      this.errors = new ArrayList<>();
    }
    this.errors.add(new ValidationError(field, this.format(message)));
  }

  private String format(final String message) {
    final var bundle = ResourceBundle.getBundle("messages", Locale.getDefault());
    return bundle.getString(message);
  }

  @Getter
  @Setter
  @RequiredArgsConstructor
  private static class ValidationError {
    private final String field;
    private final String message;
  }
}
