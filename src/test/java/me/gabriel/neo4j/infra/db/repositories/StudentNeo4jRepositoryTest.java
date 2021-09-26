package me.gabriel.neo4j.infra.db.repositories;

import me.gabriel.neo4j.core.domain.Student;
import me.gabriel.neo4j.utils.data.StudentFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataNeo4jTest
class StudentNeo4jRepositoryTest {

  private static final int SIZE_STUDENT_LIST = 10;
  @Autowired
  private StudentNeo4jRepository studentNeo4jRepository;

  @BeforeEach
  void setUp() {
    final var studentsToSave = StudentFactory.studentList(SIZE_STUDENT_LIST);
    this.studentNeo4jRepository.saveAll(studentsToSave);
  }

  @Test
  void whenDatabaseHasStudentsStoredShouldReturnAllStudents() {
    List<Student> students = this.studentNeo4jRepository.findAll();

    assertThat(students)
      .isNotNull()
      .isNotEmpty()
      .hasSize(SIZE_STUDENT_LIST);
  }
}
