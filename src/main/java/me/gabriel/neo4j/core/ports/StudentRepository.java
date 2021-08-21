package me.gabriel.neo4j.core.ports;

import me.gabriel.neo4j.core.domain.Student;

/**
 * @author daohn
 * @since 19/08/2021
 */
public interface StudentRepository {

  Student create(Student student);

}
