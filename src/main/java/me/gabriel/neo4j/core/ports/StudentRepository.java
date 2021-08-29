package me.gabriel.neo4j.core.ports;

import me.gabriel.neo4j.core.domain.Student;

import java.util.Optional;

/**
 * @author daohn
 * @since 19/08/2021
 */
public interface StudentRepository {

  Student create(Student student);

  Optional<Student> findById(Long studentId);
}
