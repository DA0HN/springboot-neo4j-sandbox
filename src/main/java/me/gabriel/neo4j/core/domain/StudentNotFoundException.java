package me.gabriel.neo4j.core.domain;

import me.gabriel.neo4j.configuration.Message;

/**
 * @author daohn
 * @since 28/08/2021
 */
public class StudentNotFoundException extends SandboxDomainException {
  public StudentNotFoundException(final String message) {
    super(message);
  }

  public StudentNotFoundException(final Message message, final Object... args) {
    super(message, args);
  }
}
