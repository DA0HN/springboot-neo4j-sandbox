package me.gabriel.neo4j.utils;

import me.gabriel.neo4j.application.api.request.DepartmentCreateRequest;
import me.gabriel.neo4j.application.api.request.StudentCreateRequest;
import me.gabriel.neo4j.application.api.request.SubjectCreateRequest;
import me.gabriel.neo4j.core.domain.Department;
import me.gabriel.neo4j.core.domain.IsLearning;
import me.gabriel.neo4j.core.domain.Student;
import me.gabriel.neo4j.core.domain.Subject;

import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

public class StudentFactory {
  public StudentFactory() {
  }

  public StudentCreateRequest createStudentRequest() {
    return new StudentCreateRequest(
      "name",
      1999,
      "country",
      asList(
        new SubjectCreateRequest("name 1", 1L),
        new SubjectCreateRequest("name 2", 2L),
        new SubjectCreateRequest("name 3", 3L)
      ),
      new DepartmentCreateRequest("department")
    );
  }

  public Student student() {
    return new Student(
      1L,
      "name",
      "country",
      1999,
      this.department(),
      this.isLearningList()
    );
  }

  public Department department() {
    return new Department(1L, "department");
  }

  public Subject[] subjectList() {
    return new Subject[]{
      new Subject(1L, "name 1"),
      new Subject(2L, "name 1"),
      new Subject(3L, "name 1"),
    };
  }

  public List<IsLearning> isLearningList() {

    var subjects = this.subjectList();

    return Arrays.asList(
      new IsLearning(1L, 1L, subjects[0]),
      new IsLearning(2L, 2L, subjects[1]),
      new IsLearning(3L, 3L, subjects[2])
    );
  }
}
