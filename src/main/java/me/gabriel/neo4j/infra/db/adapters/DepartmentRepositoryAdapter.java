package me.gabriel.neo4j.infra.db.adapters;

import lombok.AllArgsConstructor;
import me.gabriel.neo4j.core.domain.Department;
import me.gabriel.neo4j.core.ports.DepartmentRepository;
import me.gabriel.neo4j.infra.db.repositories.DepartmentNeo4jRepository;
import org.springframework.stereotype.Component;

/**
 * @author daohn
 * @since 19/08/2021
 */
@Component
@AllArgsConstructor
public class DepartmentRepositoryAdapter implements DepartmentRepository {

  private final DepartmentNeo4jRepository repository;

  @Override public Department create(Department department) {
    return this.repository.save(department);
  }
}
