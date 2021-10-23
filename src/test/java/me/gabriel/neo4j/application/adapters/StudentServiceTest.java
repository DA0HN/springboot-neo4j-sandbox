package me.gabriel.neo4j.application.adapters;

import me.gabriel.neo4j.application.api.request.SubjectCreateRequest;
import me.gabriel.neo4j.application.api.response.StudentResponse;
import me.gabriel.neo4j.core.domain.Department;
import me.gabriel.neo4j.core.domain.InvalidStateException;
import me.gabriel.neo4j.core.domain.IsLearning;
import me.gabriel.neo4j.core.domain.SandboxDomainException;
import me.gabriel.neo4j.core.domain.Student;
import me.gabriel.neo4j.core.domain.StudentNotFoundException;
import me.gabriel.neo4j.core.ports.StudentRepository;
import me.gabriel.neo4j.utils.data.DepartmentFactory;
import me.gabriel.neo4j.utils.data.IsLearningFactory;
import me.gabriel.neo4j.utils.data.StudentFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static me.gabriel.neo4j.utils.asserts.StudentResponseAssert.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
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
  private static final Class<Student> STUDENT_REPOSITORY_ARG = Student.class;
  private static final Class<Long> LONG_ARG = Long.class;
  private static final Class<String> STRING_ARG = String.class;
  @InjectMocks
  private StudentServiceAdapter studentService;
  @Mock
  private StudentRepository studentRepository;
  @Mock
  private StudentRelationshipCreator<Department, String> belongsToRelationshipCreator;
  @Mock
  private StudentRelationshipCreator<List<IsLearning>, List<SubjectCreateRequest>> isLearningRelationshipCreator;

  @BeforeEach
  void setUp() {
  }

  @Test
  @DisplayName("Quando os dados do estudante for válido deveria criar o `Student` e seus relacionamentos")
  @MockitoSettings(strictness = Strictness.LENIENT)
  void whenValidRequestShouldCreateStudentAndRelationship() {
    final var request = StudentFactory.createStudentRequest();

    when(this.studentRepository.create(isA(STUDENT_REPOSITORY_ARG))).thenReturn(StudentFactory.studentWithId());
    when(this.belongsToRelationshipCreator.create(isA(STRING_ARG))).thenReturn(DepartmentFactory.departmentWithId());
    when(this.isLearningRelationshipCreator.create(anyList())).thenReturn(IsLearningFactory.isLearningListRandom());

    final var response = this.studentService.create(request);

    this.assertStudentResponse(response);
  }

  private void assertStudentResponse(final StudentResponse response) {
    assertThat(response)
      .hasId()
      .subjectsRelationshipWereCreated()
      .hasDepartment();
  }

  @Test
  @DisplayName("Quando o identificador do `Student` for válido deveria retornar o `Student` com este identificador")
  void whenValidIdShouldFindStudentById() {

    when(this.studentRepository.findById(isA(LONG_ARG))).thenReturn(Optional.of(StudentFactory.studentWithId()));

    final var response = this.studentService.findById(STUDENT_ID);

    verify(this.studentRepository, times(1)).findById(isA(LONG_ARG));
    assertThat(response).isNotNull();
  }

  @Test
  @DisplayName("Quando o `Student` não for encontrado deveria lançar a exceção `StudentNotFoundException`")
  void whenNotFoundStudentShouldThrowException() {
    when(this.studentRepository.findById(isA(LONG_ARG))).thenReturn(Optional.empty());

    final var exception = assertThrows(
      StudentNotFoundException.class,
      () -> this.studentService.findById(STUDENT_ID)
    );

    verify(this.studentRepository, times(1)).findById(isA(LONG_ARG));
    assertEquals("Student with id " + STUDENT_ID + " not found", exception.getMessage());
  }

  @Test
  @DisplayName("Quando o identificador do `Student` for nulo deveria lançar a exceção `IllegalArgumentException`")
  void whenFindWithIdNullShouldThrowException() {

    final var exception = assertThrows(
      InvalidStateException.class,
      () -> this.studentService.findById(null)
    );

    assertEquals("Student id must be not null", exception.getMessage());

    verify(this.studentRepository, never()).findById(isA(LONG_ARG));
  }

  @Test
  @DisplayName("Quando o nome parcial é valido deveria retornar uma lista de estudantes")
  void whenPartialNameIsValidShouldReturnStudents() {
    when(this.studentRepository.findAllByPartialName(isA(STRING_ARG))).thenReturn(asList(
      StudentFactory.studentWithId(),
      StudentFactory.studentWithId(),
      StudentFactory.studentWithId()
    ));

    final var response = this.studentService.findAllByPartialName(STUDENT_NAME);

    verify(this.studentRepository, times(1)).findAllByPartialName(isA(STRING_ARG));

    final int EXPECTED_SIZE_LIST = 3;

    assertThat(response)
      .isNotNull()
      .hasSize(EXPECTED_SIZE_LIST);
  }

  @Test
  @DisplayName("Quando o nome parcial for nulo deveria lançar a exceção `IllegalArgumentException`")
  void whenPartialNameIsNullShouldThrowIllegalArgumentException() {
    final var exception = assertThrows(
      SandboxDomainException.class,
      () -> this.studentService.findAllByPartialName(null)
    );

    assertEquals("Partial name must be not null", exception.getMessage());
  }

}
