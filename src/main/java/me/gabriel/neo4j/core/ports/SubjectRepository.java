package me.gabriel.neo4j.core.ports;

import me.gabriel.neo4j.core.domain.Subject;

import java.util.List;
import java.util.Optional;

/**
 * @author daohn
 * @since 21/08/2021
 */
public interface SubjectRepository {

  Subject create(Subject subject);
  List<Subject> createAll(List<Subject> subjects);

  Optional<Subject> findByName(String name);
}
