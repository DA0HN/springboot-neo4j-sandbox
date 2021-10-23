package me.gabriel.neo4j.core.domain;

import me.gabriel.neo4j.configuration.Messages;

/**
 * @author daohn
 * @since 28/08/2021
 */
public class StudentNotFoundException extends SandboxDomainException {
  public StudentNotFoundException(final String message) {
    super(message);
  }

  public StudentNotFoundException(final Messages messages, final Object... args) {
    super(messages, args);
  }
}
