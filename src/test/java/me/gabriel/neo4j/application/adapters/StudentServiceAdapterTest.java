package me.gabriel.neo4j.application.adapters;

import me.gabriel.neo4j.application.api.request.CreateDepartmentRequest;
import me.gabriel.neo4j.application.api.request.CreateStudentRequest;
import me.gabriel.neo4j.application.api.request.CreateSubjectRequest;
import me.gabriel.neo4j.core.domain.Department;
import me.gabriel.neo4j.core.domain.IsLearning;
import me.gabriel.neo4j.core.domain.Student;
import me.gabriel.neo4j.core.domain.Subject;
import me.gabriel.neo4j.core.ports.DepartmentRepository;
import me.gabriel.neo4j.core.ports.StudentRepository;
import me.gabriel.neo4j.core.ports.StudentService;
import me.gabriel.neo4j.core.ports.SubjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

/**
 * @author daohn
 * @since 21/08/2021
 */
@SpringBootTest
class StudentServiceAdapterTest {

  private static final Class<Department> DEPARTMENT_REPOSITORY_ARG = Department.class;
  private static final Class<Student> STUDENT_REPOSITORY_ARG = Student.class;
  @Autowired
  private StudentService service;
  @MockBean
  private StudentRepository studentRepository;
  @MockBean
  private DepartmentRepository departmentRepository;
  @MockBean
  private SubjectRepository subjectRepository;

  @Test
  void shouldCreateStudentAndRelationship() {
    when(departmentRepository.create(isA(DEPARTMENT_REPOSITORY_ARG))).thenReturn(department());
    when(studentRepository.create(isA(STUDENT_REPOSITORY_ARG))).thenReturn(student());

    var response = service.create(createStudentRequest());


  }

  private CreateStudentRequest createStudentRequest() {
    return new CreateStudentRequest(
      "name",
      1999,
      "country",
      asList(
        new CreateSubjectRequest("name 1", 1L),
        new CreateSubjectRequest("name 2", 2L),
        new CreateSubjectRequest("name 3", 3L)
      ),
      new CreateDepartmentRequest("name")
    );
  }

  private Student student() {
    return new Student(
      1L,
      "name",
      "country",
      1999,
      department(),
      isLearningList()
    );
  }

  private Department department() {
    return new Department(1L, "department");
  }

  private Subject[] subjectList() {
    return new Subject[]{
      new Subject(1L, "name 1"),
      new Subject(2L, "name 1"),
      new Subject(3L, "name 1"),
    };
  }

  private List<IsLearning> isLearningList() {

    var subjects = subjectList();

    return asList(
      new IsLearning(1L, subjects[0]),
      new IsLearning(2L, subjects[1]),
      new IsLearning(3L, subjects[2])
    );
  }

}
