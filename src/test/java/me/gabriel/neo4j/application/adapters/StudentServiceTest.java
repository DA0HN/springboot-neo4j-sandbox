package me.gabriel.neo4j.application.adapters;

import me.gabriel.neo4j.application.api.request.StudentCreateRequest;
import me.gabriel.neo4j.application.api.response.StudentResponse;
import me.gabriel.neo4j.core.domain.Department;
import me.gabriel.neo4j.core.domain.Student;
import me.gabriel.neo4j.core.domain.StudentNotFoundException;
import me.gabriel.neo4j.core.domain.Subject;
import me.gabriel.neo4j.core.ports.DepartmentRepository;
import me.gabriel.neo4j.core.ports.StudentRepository;
import me.gabriel.neo4j.core.ports.StudentService;
import me.gabriel.neo4j.core.ports.SubjectRepository;
import me.gabriel.neo4j.utils.StudentFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static java.util.Arrays.asList;
import static me.gabriel.neo4j.utils.StudentResponseAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author daohn
 * @since 21/08/2021
 */
@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  private static final long STUDENT_ID = 1L;
  private static final String STUDENT_NAME = "studentName";
  private static final Class<Department> DEPARTMENT_REPOSITORY_ARG = Department.class;
  private static final Class<Student> STUDENT_REPOSITORY_ARG = Student.class;
  private static final Class<Subject> SUBJECT_REPOSITORY_ARG = Subject.class;
  private final StudentFactory studentFactory = new StudentFactory();
  private StudentService service;
  @Mock
  private StudentRepository studentRepository;
  @Mock
  private DepartmentRepository departmentRepository;
  @Mock
  private SubjectRepository subjectRepository;

  @BeforeEach
  void setUp() {
    this.service = new StudentServiceAdapter(
      this.studentRepository,
      this.departmentRepository,
      this.subjectRepository
    );
  }

  @Test
  void whenValidRequestShouldCreateStudentAndRelationship() {
    when(this.departmentRepository.create(isA(DEPARTMENT_REPOSITORY_ARG))).thenReturn(this.studentFactory.department());
    when(this.studentRepository.create(isA(STUDENT_REPOSITORY_ARG))).thenReturn(this.studentFactory.student());
    when(this.subjectRepository.create(isA(SUBJECT_REPOSITORY_ARG))).thenReturn(this.studentFactory.subjectList()[0]);

    var request = this.studentFactory.createStudentRequest();

    var response = this.service.create(request);

    this.assertStudentResponse(request, response);
  }

  @Test
  void whenValidIdShouldFindStudentById() {

    when(this.studentRepository.findById(isA(Long.class))).thenReturn(Optional.of(this.studentFactory.student()));

    var response = this.service.findById(STUDENT_ID);

    verify(this.studentRepository, times(1)).findById(isA(Long.class));
    assertThat(response).isNotNull();
  }

  @Test
  void whenNotFoundStudentShouldThrowException() {
    when(this.studentRepository.findById(isA(Long.class))).thenReturn(Optional.empty());

    assertThrows(
      StudentNotFoundException.class,
      () -> this.service.findById(STUDENT_ID)
    );

    verify(this.studentRepository, times(1)).findById(isA(Long.class));
  }

  @Test
  void whenFindWithIdNullShouldThrowException() {

    var exception = assertThrows(
      IllegalArgumentException.class,
      () -> this.service.findById(null)
    );

    assertEquals("Student id must be not null", exception.getMessage());

    verify(this.studentRepository, never()).findById(isA(Long.class));
  }

  @Test
  void whenPartialNameIsValidShouldReturnStudents() {
    when(this.studentRepository.findAllByPartialName(isA(String.class))).thenReturn(asList(
      this.studentFactory.student(),
      this.studentFactory.student(),
      this.studentFactory.student()
    ));

    var response = this.service.findAllByPartialName(STUDENT_NAME);

    verify(this.studentRepository, times(1)).findAllByPartialName(isA(String.class));

    int EXPECTED_SIZE_LIST = 3;

    Assertions.assertThat(response).isNotNull()
      .hasSize(EXPECTED_SIZE_LIST);
  }

  @Test
  void whenPartialNameIsNullShouldThrowIllegalArgumentException() {

    final String PARTIAL_STUDENT_NAME = null;

    var exception = assertThrows(
      IllegalArgumentException.class,
      () -> this.service.findAllByPartialName(PARTIAL_STUDENT_NAME)
    );

    assertEquals("Partial name must be not null", exception.getMessage());
  }

  private void assertStudentResponse(StudentCreateRequest request, StudentResponse response) {
    assertThat(response)
      .hasId()
      .hasName(request.name())
      .hasBirthYear(request.birthYear())
      .hasCountry(request.country())
      .subjectsRelationshipWereCreated().containsTheseSubjects(request.subjects())
      .hasDepartment().hasSameDepartmentName(request.department());
  }

}
