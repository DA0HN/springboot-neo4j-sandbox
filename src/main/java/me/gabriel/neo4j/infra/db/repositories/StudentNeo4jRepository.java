package me.gabriel.neo4j.infra.db.repositories;

import me.gabriel.neo4j.core.domain.Student;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author daohn
 * @since 19/08/2021
 */
@Repository
public interface StudentNeo4jRepository extends Neo4jRepository<Student, Long> {

  @Query("MATCH (department:Department)<-[belongs:BELONGS_TO]-(student:Student)-[isLearning:IS_LEARNING]->(subject:Subject) " +
         "WHERE toLower(student.name) CONTAINS toLower($name) " +
         "RETURN student, " +
         "collect(department), " +
         "collect(belongs), " +
         "collect(isLearning), " +
         "collect(subject)"
  )
  List<Student> findByName(String name);
}
