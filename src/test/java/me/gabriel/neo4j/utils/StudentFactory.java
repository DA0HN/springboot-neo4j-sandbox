package me.gabriel.neo4j.utils;

import me.gabriel.neo4j.application.api.request.CreateDepartmentRequest;
import me.gabriel.neo4j.application.api.request.CreateStudentRequest;
import me.gabriel.neo4j.application.api.request.CreateSubjectRequest;
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

  public CreateStudentRequest createStudentRequest() {
    return new CreateStudentRequest(
      "name",
      1999,
      "country",
      asList(
        new CreateSubjectRequest("name 1", 1L),
        new CreateSubjectRequest("name 2", 2L),
        new CreateSubjectRequest("name 3", 3L)
      ),
      new CreateDepartmentRequest("department")
    );
  }

  public Student student() {
    return new Student(
      1L,
      "name",
      "country",
      1999,
      department(),
      isLearningList()
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

    var subjects = subjectList();

    return Arrays.asList(
      new IsLearning(1L, 1L, subjects[0]),
      new IsLearning(2L, 2L, subjects[1]),
      new IsLearning(3L, 3L, subjects[2])
    );
  }
}
