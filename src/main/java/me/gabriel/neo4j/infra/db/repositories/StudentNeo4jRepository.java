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

  /**
   * Para o SDN 6 é necessária a utilização do 'collect()'
   * @see
   * <a href="https://docs.spring.io/spring-data/neo4j/docs/current/reference/html/#custom-queries.for-relationships.one.record">
   * neo4j docs - custom queries for relationships one records
   * </a>
   * @see
   * <a href="https://github.com/spring-projects/spring-data-neo4j/issues/2362">
   *  ISSUE 2362 - Spring Data Neo4j
   * </a>
   *
   * @param name Nome que será utilizado na consulta
   * @return Uma lista de estudantes que contém a palavra desejada
   */
  @Query("MATCH (department:Department)<-[belongs:BELONGS_TO]-(student:Student)-[isLearning:IS_LEARNING]->(subject:Subject) " +
         "WHERE toLower(student.name) CONTAINS toLower($name) " +
         "RETURN student, " +
         "collect(department), " +
         "collect(belongs), " +
         "collect(isLearning), " +
         "collect(subject)"
  )
  List<Student> findAllStudentsByPartialName(String name);
}
