package me.gabriel.neo4j.core.ports;

import me.gabriel.neo4j.core.domain.Department;

import java.util.Optional;

/**
 * @author daohn
 * @since 19/08/2021
 */
public interface DepartmentRepository {

  Department create(Department department);

  Optional<Department> findByName(String name);
}
