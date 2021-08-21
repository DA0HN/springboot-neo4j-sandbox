package me.gabriel.neo4j.application.api.response;

import me.gabriel.neo4j.core.domain.Subject;

/**
 * @author daohn
 * @since 21/08/2021
 */
public record SubjectCreateResponse(Long id, String name) {
  public static SubjectCreateResponse from(Subject subject) {
    return new SubjectCreateResponse(subject.getId(), subject.getName());
  }
}
