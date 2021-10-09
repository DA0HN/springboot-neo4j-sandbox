package me.gabriel.neo4j.application.adapters;

import me.gabriel.neo4j.core.domain.Department;
import me.gabriel.neo4j.core.ports.DepartmentRepository;
import me.gabriel.neo4j.utils.data.DepartmentFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static me.gabriel.neo4j.utils.data.StudentFactory.createStudentRequest;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BelongsToRelationshipCreatorTest {

  private static final Class<Department> DEPARTMENT_ARGUMENT = Department.class;
  @InjectMocks
  private BelongsToRelationshipCreator belongsToRelationshipCreator;
  @Mock
  private DepartmentRepository departmentRepository;


  @Test
  @DisplayName("Quando `StudentCreateRequest` for válido deveria buscar pelo nome o `Department` e retorna-lo caso exista")
  void shouldFindDepartmentByNameAndReturn() {
    var request = createStudentRequest();
    when(this.departmentRepository.findByName(anyString())).thenReturn(of(DepartmentFactory.departmentWithId(request.departmentName())));

    final var department = this.belongsToRelationshipCreator.create("name");

    verify(this.departmentRepository, times(1)).findByName(anyString());
    verify(this.departmentRepository, never()).create(isA(DEPARTMENT_ARGUMENT));

    assertNotNull(department.getId());
    assertEquals(
      request.departmentName(),
      department.getName(),
      "Department name should be same"
    );
  }

  @Test
  @DisplayName("Quando `StudentCreateRequest` for válido e o `Department` não for encontrado deveria criar e retorna-lo")
  void shouldCreateDepartmentIfNotFindByName() {
    var request = createStudentRequest();
    when(this.departmentRepository.findByName(anyString())).thenReturn(empty());
    when(this.departmentRepository.create(isA(DEPARTMENT_ARGUMENT))).thenReturn(DepartmentFactory.departmentWithId());

    final var department = this.belongsToRelationshipCreator.create("name");

    verify(this.departmentRepository, times(1)).findByName(anyString());
    verify(this.departmentRepository, times(1)).create(isA(DEPARTMENT_ARGUMENT));
  }

  @Test
  @DisplayName("Quando o nome do `Department` não for válido deveria lançar a exceção `IllegalArgumentException`")
  void shouldThrowIllegalArgumentExceptionIfInvalidName() {
    assertThatThrownBy(() -> this.belongsToRelationshipCreator.create(null))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("Department name should be not null");
    verifyNoInteractions(this.departmentRepository);
  }

}
