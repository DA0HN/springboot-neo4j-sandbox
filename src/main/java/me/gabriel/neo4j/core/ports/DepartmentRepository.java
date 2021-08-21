package me.gabriel.neo4j.core.ports;

import me.gabriel.neo4j.core.domain.Department;

/**
 * @author daohn
 * @since 19/08/2021
 */
public interface DepartmentRepository {

  Department create(Department student);

}
