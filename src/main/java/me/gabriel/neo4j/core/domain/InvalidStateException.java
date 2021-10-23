package me.gabriel.neo4j.core.domain;

import me.gabriel.neo4j.configuration.Messages;

public class InvalidStateException extends SandboxDomainException {

  public InvalidStateException(final String message) {
    super(message);
  }

  public InvalidStateException(final Messages messages, final Object... args) {
    super(messages, args);
  }
}
