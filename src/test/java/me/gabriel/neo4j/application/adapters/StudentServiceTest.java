package me.gabriel.neo4j.application.adapters;

import me.gabriel.neo4j.core.domain.Department;
import me.gabriel.neo4j.core.domain.Student;
import me.gabriel.neo4j.core.domain.Subject;
import me.gabriel.neo4j.core.ports.DepartmentRepository;
import me.gabriel.neo4j.core.ports.StudentRepository;
import me.gabriel.neo4j.core.ports.StudentService;
import me.gabriel.neo4j.core.ports.SubjectRepository;
import me.gabriel.neo4j.utils.StudentFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static me.gabriel.neo4j.utils.StudentResponseAssert.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

/**
 * @author daohn
 * @since 21/08/2021
 */
@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

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
      studentRepository,
      departmentRepository,
      subjectRepository
    );
  }

  @Test
  void shouldCreateStudentAndRelationship() {
    when(departmentRepository.create(isA(DEPARTMENT_REPOSITORY_ARG))).thenReturn(studentFactory.department());
    when(studentRepository.create(isA(STUDENT_REPOSITORY_ARG))).thenReturn(studentFactory.student());
    when(subjectRepository.create(isA(SUBJECT_REPOSITORY_ARG))).thenReturn(studentFactory.subjectList()[0]);

    var request = studentFactory.createStudentRequest();

    var response = service.create(request);

    assertThat(response)
      .hasId()
      .hasName(request.name())
      .hasBirthYear(request.birthYear())
      .hasCountry(request.country())
      .subjectsRelationshipWereCreated().containsTheseSubjects(request.subjects())
      .hasDepartment().hasSameDepartmentName(request.department());
  }

}
