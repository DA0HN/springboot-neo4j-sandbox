package me.gabriel.neo4j.core.domain;

import me.gabriel.neo4j.configuration.Messages;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class SandboxDomainException extends RuntimeException {

  SandboxDomainException(final String message) {
    super(message);
  }

  public SandboxDomainException(final Messages messages, final Object... args) {
    super(format(messages, args));
  }


  protected static String format(final Messages key, final Object... args) {
    final var bundle = ResourceBundle.getBundle("messages", Locale.getDefault());
    final var message = bundle.getString(key.messageAsKey());
    return MessageFormat.format(message, args);
  }

}
