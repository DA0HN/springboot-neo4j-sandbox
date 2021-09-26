package me.gabriel.neo4j.infra.db.repositories;

import me.gabriel.neo4j.core.domain.Department;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author daohn
 * @since 19/08/2021
 */
@Repository
public interface DepartmentNeo4jRepository extends Neo4jRepository<Department, Long> {

  @Query("MATCH (department:Department) WHERE toLower(department.name)=toLower($name) RETURN department")
  Optional<Department> findByName(String name);

}
