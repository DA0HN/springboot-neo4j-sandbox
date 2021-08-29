package me.gabriel.neo4j.core.domain;

/**
 * @author daohn
 * @since 28/08/2021
 */
public class StudentNotFoundException extends RuntimeException {
  public StudentNotFoundException(String message) {
    super(message);
  }
}
