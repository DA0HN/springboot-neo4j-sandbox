package me.gabriel.neo4j.infra.db.repositories;

import me.gabriel.neo4j.core.domain.Subject;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author daohn
 * @since 21/08/2021
 */
@Repository
public interface SubjectNeo4jRespository extends Neo4jRepository<Subject, Long> {

  @Query("""
           MATCH (subject:Subject) WHERE toLower(subject.name)=toLower($name) RETURN subject
         """)
  Optional<Subject> findByName(String name);
}
