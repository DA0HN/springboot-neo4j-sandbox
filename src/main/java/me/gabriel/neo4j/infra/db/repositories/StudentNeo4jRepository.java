package me.gabriel.neo4j.infra.db.repositories;

import me.gabriel.neo4j.core.domain.Student;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

/**
 * @author daohn
 * @since 19/08/2021
 */
@Repository
public interface StudentNeo4jRepository extends Neo4jRepository<Student, Long> {
}
