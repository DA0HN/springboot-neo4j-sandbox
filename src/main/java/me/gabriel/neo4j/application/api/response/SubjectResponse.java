package me.gabriel.neo4j.application.api.response;

import me.gabriel.neo4j.core.domain.Subject;

/**
 * @author daohn
 * @since 21/08/2021
 */
public record SubjectResponse(Long id, String name) {
  public static SubjectResponse from(Subject subject) {
    return new SubjectResponse(subject.getId(), subject.getName());
  }
}
