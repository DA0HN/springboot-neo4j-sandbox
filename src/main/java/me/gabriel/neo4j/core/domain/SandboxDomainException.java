package me.gabriel.neo4j.core.domain;

import me.gabriel.neo4j.configuration.Message;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class SandboxDomainException extends RuntimeException {

  SandboxDomainException(final String message) {
    super(message);
  }

  public SandboxDomainException(final Message message, final Object... args) {
    super(format(message, args));
  }


  protected static String format(final Message key, final Object... args) {
    final var bundle = ResourceBundle.getBundle("messages", Locale.getDefault());
    final var message = bundle.getString(key.messageAsKey());
    return MessageFormat.format(message, args);
  }

}
