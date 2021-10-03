package me.gabriel.neo4j.application.adapters;

import me.gabriel.neo4j.application.api.request.StudentCreateRequest;
import me.gabriel.neo4j.application.api.response.StudentResponse;
import me.gabriel.neo4j.core.domain.Department;
import me.gabriel.neo4j.core.domain.Student;
import me.gabriel.neo4j.core.domain.StudentNotFoundException;
import me.gabriel.neo4j.core.domain.Subject;
import me.gabriel.neo4j.core.ports.DepartmentRepository;
import me.gabriel.neo4j.core.ports.StudentRepository;
import me.gabriel.neo4j.core.ports.SubjectRepository;
import me.gabriel.neo4j.utils.data.DepartmentFactory;
import me.gabriel.neo4j.utils.data.StudentFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static java.util.Arrays.asList;
import static me.gabriel.neo4j.utils.asserts.StudentResponseAssert.assertThat;
import static me.gabriel.neo4j.utils.data.SubjectFactory.subjectWithDummyName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
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
  @InjectMocks
  private StudentServiceAdapter studentService;
  @Mock
  private StudentRepository studentRepository;
  @Mock
  private DepartmentRepository departmentRepository;
  @Mock
  private SubjectRepository subjectRepository;

  @BeforeEach
  void setUp() {
  }

  @Test
  @DisplayName("Quando os dados do estudante for válido deveria criar o `Student` e seus relacionamentos")
  void whenValidRequestShouldCreateStudentAndRelationship() {
    when(this.departmentRepository.create(isA(DEPARTMENT_REPOSITORY_ARG))).thenReturn(DepartmentFactory.departmentWithId());
    when(this.studentRepository.create(isA(STUDENT_REPOSITORY_ARG))).thenReturn(StudentFactory.studentWithId());
    when(this.subjectRepository.findByName(isA(String.class))).thenReturn(Optional.empty());
    when(this.subjectRepository.create(isA(SUBJECT_REPOSITORY_ARG))).thenReturn(subjectWithDummyName());

    var request = StudentFactory.createStudentRequest();

    var response = this.studentService.create(request);

    this.assertStudentResponse(request, response);
  }

  private void assertStudentResponse(StudentCreateRequest request, StudentResponse response) {
    assertThat(response)
      .hasId()
      .subjectsRelationshipWereCreated()
      .hasDepartment();
  }

  @Test
  @DisplayName("Quando o identificador do `Student` for válido deveria retornar o `Student` com este identificador")
  void whenValidIdShouldFindStudentById() {

    when(this.studentRepository.findById(isA(Long.class))).thenReturn(Optional.of(StudentFactory.studentWithId()));

    var response = this.studentService.findById(STUDENT_ID);

    verify(this.studentRepository, times(1)).findById(isA(Long.class));
    assertThat(response).isNotNull();
  }

  @Test
  @DisplayName("Quando o `Student` não for encontrado deveria lançar a exceção `StudentNotFoundException`")
  void whenNotFoundStudentShouldThrowException() {
    when(this.studentRepository.findById(isA(Long.class))).thenReturn(Optional.empty());

    assertThrows(
      StudentNotFoundException.class,
      () -> this.studentService.findById(STUDENT_ID)
    );

    verify(this.studentRepository, times(1)).findById(isA(Long.class));
  }

  @Test
  @DisplayName("Quando o identificador do `Student` for nulo deveria lançar a exceção `IllegalArgumentException`")
  void whenFindWithIdNullShouldThrowException() {

    var exception = assertThrows(
      IllegalArgumentException.class,
      () -> this.studentService.findById(null)
    );

    assertEquals("Student id must be not null", exception.getMessage());

    verify(this.studentRepository, never()).findById(isA(Long.class));
  }

  @Test
  @DisplayName("Quando o nome parcial é valido deveria retornar uma lista de estudantes")
  void whenPartialNameIsValidShouldReturnStudents() {
    when(this.studentRepository.findAllByPartialName(isA(String.class))).thenReturn(asList(
      StudentFactory.studentWithId(),
      StudentFactory.studentWithId(),
      StudentFactory.studentWithId()
    ));

    var response = this.studentService.findAllByPartialName(STUDENT_NAME);

    verify(this.studentRepository, times(1)).findAllByPartialName(isA(String.class));

    int EXPECTED_SIZE_LIST = 3;

    assertThat(response)
      .isNotNull()
      .hasSize(EXPECTED_SIZE_LIST);
  }

  @Test
  @DisplayName("Quando o nome parcial for nulo deveria lançar a exceção `IllegalArgumentException`")
  void whenPartialNameIsNullShouldThrowIllegalArgumentException() {
    var exception = assertThrows(
      IllegalArgumentException.class,
      () -> this.studentService.findAllByPartialName(null)
    );

    assertEquals("Partial name must be not null", exception.getMessage());
  }

  @Test
  @DisplayName("Quando o `Department` já existir deveria busca-lo no banco de dados e criar o relacionamento `BELONGS_TO`")
  void whenDepartmentAlreadyExistShouldFindInDatabaseAndCreateRelationship() {
    when(this.studentRepository.create(isA(STUDENT_REPOSITORY_ARG))).thenReturn(StudentFactory.studentWithId());
    when(this.subjectRepository.create(isA(SUBJECT_REPOSITORY_ARG))).thenReturn(subjectWithDummyName());
    when(this.departmentRepository.findByName(isA(String.class))).thenReturn(Optional.of(DepartmentFactory.departmentWithId()));

    final var request = StudentFactory.createStudentRequest();

    final var response = this.studentService.create(request);

    verify(this.departmentRepository, times(1)).findByName(anyString());
    verify(this.departmentRepository, never()).create(isA(DEPARTMENT_REPOSITORY_ARG));
  }

  @Test
  @DisplayName("Quando o `Subject` já existir deveria busca-lo no banco de dados e criar o relacionamento `IS_LEARNING`")
  void whenSubjectAlreadyExistsShouldFindInDatabaseAndCreateRelationship() {
    when(this.studentRepository.create(isA(STUDENT_REPOSITORY_ARG))).thenReturn(StudentFactory.studentWithId());
    when(this.departmentRepository.create(isA(DEPARTMENT_REPOSITORY_ARG))).thenReturn(DepartmentFactory.departmentWithId());
    when(this.subjectRepository.findByName(isA(String.class))).thenReturn(Optional.of(subjectWithDummyName()));

    final var request = StudentFactory.createStudentRequest();
    final var SUBJECT_LIST_SIZE = request.subjects().size();

    final var response = this.studentService.create(request);

    verify(this.subjectRepository, times(SUBJECT_LIST_SIZE)).findByName(anyString());
    verify(this.subjectRepository, never()).create(isA(SUBJECT_REPOSITORY_ARG));
  }

}
