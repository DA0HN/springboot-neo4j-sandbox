package me.gabriel.neo4j.utils;

import me.gabriel.neo4j.application.api.request.DepartmentCreateRequest;
import me.gabriel.neo4j.application.api.request.StudentCreateRequest;
import me.gabriel.neo4j.application.api.request.SubjectCreateRequest;
import me.gabriel.neo4j.core.domain.Department;
import me.gabriel.neo4j.core.domain.IsLearning;
import me.gabriel.neo4j.core.domain.Student;
import me.gabriel.neo4j.core.domain.Subject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static me.gabriel.neo4j.utils.DummyData.*;

@SuppressWarnings("LawOfDemeter")
public class StudentFactory {

  public StudentFactory() {
  }

  public StudentCreateRequest createStudentRequest() {
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

  public Student student() {
    return new Student(
      name(),
      country(),
      year(),
      this.departmentWithId(),
      this.isLearningList()
    );
  }

  public Student studentWithId() {
    var student = this.student();
    student.setId(id());
    return student;
  }

  public Department department() {
    return new Department(departmentName());
  }

  public Department departmentWithId() {
    Department department = this.department();
    department.setId(id());
    return department;
  }

  public Subject subject() {
    return new Subject(subjectName());
  }

  public Subject subjectWithId() {
    Subject subject = this.subject();
    subject.setId(id());
    return subject;
  }

  public List<Subject> subjectListRandom() {
    return IntStream.range(0, size())
      .mapToObj(i -> this.subject())
      .collect(Collectors.toCollection(ArrayList::new));
  }

  public List<Subject> subjectListRandomWithId() {
    return IntStream.range(0, size())
      .mapToObj(i -> this.subjectWithId())
      .collect(Collectors.toCollection(ArrayList::new));
  }

  public Subject[] subjectList() {
    return new Subject[]{
      this.subjectWithId(),
      this.subjectWithId(),
      this.subjectWithId(),
    };
  }

  public List<IsLearning> isLearningList() {

    var subjects = this.subjectList();

    return Arrays.asList(
      this.isLearningWithId(),
      this.isLearningWithId(),
      this.isLearningWithId()
    );
  }

  public IsLearning isLearning() {
    return new IsLearning(marksPercent(), this.subjectWithId());
  }

  public IsLearning isLearningWithId() {
    return new IsLearning(id(), marksPercent(), this.subjectWithId());
  }

  public List<IsLearning> isLearningListRandomWithId() {
    return IntStream.range(0, size())
      .mapToObj(i -> this.isLearningWithId())
      .collect(Collectors.toCollection(ArrayList::new));
  }
}
