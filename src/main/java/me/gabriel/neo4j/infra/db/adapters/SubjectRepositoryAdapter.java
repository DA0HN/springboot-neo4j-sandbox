package me.gabriel.neo4j.infra.db.adapters;

import lombok.AllArgsConstructor;
import me.gabriel.neo4j.core.domain.Subject;
import me.gabriel.neo4j.core.ports.SubjectRepository;
import me.gabriel.neo4j.infra.db.repositories.SubjectNeo4jRespository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @author daohn
 * @since 21/08/2021
 */
@Component
@AllArgsConstructor
public class SubjectRepositoryAdapter implements SubjectRepository {

  private final SubjectNeo4jRespository repository;

  @Override public Subject create(Subject subject) {
    return this.repository.save(subject);
  }

  @Override public List<Subject> createAll(List<Subject> subjects) {
    return this.repository.saveAll(subjects);
  }

  @Override public Optional<Subject> findByName(String name) {
    return this.repository.findByName(name);
  }
}
