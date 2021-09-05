package me.gabriel.neo4j.infra.db.adapters;

import lombok.AllArgsConstructor;
import me.gabriel.neo4j.core.domain.Student;
import me.gabriel.neo4j.core.ports.StudentRepository;
import me.gabriel.neo4j.infra.db.repositories.StudentNeo4jRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @author daohn
 * @since 19/08/2021
 */
@Component
@AllArgsConstructor
public class StudentRepositoryAdapter implements StudentRepository {

  private final StudentNeo4jRepository repository;

  @Override public Student create(Student student) {
    return this.repository.save(student);
  }

  @Override public Optional<Student> findById(Long studentId) {
    return this.repository.findById(studentId);
  }

  @Override public List<Student> findByName(String studentName) {
    return this.repository.findByName(studentName);
  }
}
