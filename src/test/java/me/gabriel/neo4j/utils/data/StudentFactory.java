package me.gabriel.neo4j.utils.data;

import me.gabriel.neo4j.application.api.request.DepartmentCreateRequest;
import me.gabriel.neo4j.application.api.request.StudentCreateRequest;
import me.gabriel.neo4j.application.api.request.SubjectCreateRequest;
import me.gabriel.neo4j.core.domain.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static me.gabriel.neo4j.utils.data.DepartmentFactory.department;
import static me.gabriel.neo4j.utils.data.DummyData.country;
import static me.gabriel.neo4j.utils.data.DummyData.departmentName;
import static me.gabriel.neo4j.utils.data.DummyData.id;
import static me.gabriel.neo4j.utils.data.DummyData.marksPercent;
import static me.gabriel.neo4j.utils.data.DummyData.name;
import static me.gabriel.neo4j.utils.data.DummyData.subjectName;
import static me.gabriel.neo4j.utils.data.DummyData.year;
import static me.gabriel.neo4j.utils.data.IsLearningFactory.isLearningListRandom;

public class StudentFactory {

  public static StudentCreateRequest createStudentRequest() {
    return new StudentCreateRequest(
      name(),
      year(),
      country(),
      asList(
        new SubjectCreateRequest(subjectName(), marksPercent()),
        new SubjectCreateRequest(subjectName(), marksPercent()),
        new SubjectCreateRequest(subjectName(), marksPercent())
      ),
      new DepartmentCreateRequest(departmentName())
    );
  }

  public static List<Student> studentList(int size) {
    return IntStream.range(1, size)
      .mapToObj(i -> student())
      .collect(Collectors.toCollection(ArrayList::new));
  }

  public static Student student() {
    return new Student(
      name(),
      country(),
      year(),
      department(),
      isLearningListRandom()
    );
  }

  public static Student studentWithId() {
    var student = student();
    student.setId(id());
    return student;
  }
}
