package me.gabriel.neo4j.core.domain;

import me.gabriel.neo4j.configuration.Message;

public class InvalidStateException extends SandboxDomainException {

  public InvalidStateException(final String message) {
    super(message);
  }

  public InvalidStateException(final Message message, final Object... args) {
    super(message, args);
  }
}
