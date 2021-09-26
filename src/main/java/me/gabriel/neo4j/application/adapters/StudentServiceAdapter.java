package me.gabriel.neo4j.application.adapters;

import lombok.AllArgsConstructor;
import me.gabriel.neo4j.application.api.request.StudentCreateRequest;
import me.gabriel.neo4j.application.api.response.StudentResponse;
import me.gabriel.neo4j.core.domain.Department;
import me.gabriel.neo4j.core.domain.IsLearning;
import me.gabriel.neo4j.core.domain.Student;
import me.gabriel.neo4j.core.domain.StudentNotFoundException;
import me.gabriel.neo4j.core.domain.Subject;
import me.gabriel.neo4j.core.ports.DepartmentRepository;
import me.gabriel.neo4j.core.ports.StudentRepository;
import me.gabriel.neo4j.core.ports.StudentService;
import me.gabriel.neo4j.core.ports.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author daohn
 * @since 19/08/2021
 */
@Service
@AllArgsConstructor
public class StudentServiceAdapter implements StudentService {

  private final StudentRepository studentRepository;
  private final DepartmentRepository departmentRepository;
  private final SubjectRepository subjectRepository;

  @Override public StudentResponse create(StudentCreateRequest request) {

    var student = Student.from(request);

    this.createRelationships(request, student);

    student = this.studentRepository.create(student);

    return StudentResponse.from(student);
  }

  private void createRelationships(StudentCreateRequest request, Student student) {
    student.setRelationship(
      this.extractDepartmentRelationship(request, student),
      this.extractSubjectRelationship(request, student)
    );
  }

  private Department extractDepartmentRelationship(StudentCreateRequest request, Student student) {
    String departmentName = request.departmentName();

    final var maybeDepartment = this.departmentRepository.findByName(departmentName);

    if(maybeDepartment.isEmpty()) {
      return this.createDepartment(request);
    }

    return maybeDepartment.get();
  }

  private Department createDepartment(StudentCreateRequest request) {
    var department = Department.from(request.department());
    this.departmentRepository.create(department);
    return department;
  }

  private List<IsLearning> extractSubjectRelationship(StudentCreateRequest request, Student student) {
    return this.createSubjects(request);
  }

  private List<IsLearning> createSubjects(StudentCreateRequest request) {
    return request.subjects()
      .stream()
      .map(data -> {
        var subject = this.subjectRepository.create(new Subject(
          data.name()
        ));
        return new IsLearning(data.marks(), subject);
      }).collect(Collectors.toList());
  }

  @Override public StudentResponse findById(Long studentId) {

    if(Objects.isNull(studentId)) {
      throw new IllegalArgumentException("Student id must be not null");
    }

    return this.studentRepository
      .findById(studentId)
      .map(StudentResponse::from)
      .orElseThrow(() -> new StudentNotFoundException("Student with id " + studentId + " not found."));
  }

  @Override public List<StudentResponse> findAllByPartialName(String partialName) {

    if(partialName == null) {
      throw new IllegalArgumentException("Partial name must be not null");
    }

    return this.studentRepository.findAllByPartialName(partialName)
      .stream()
      .map(StudentResponse::from)
      .collect(Collectors.toList());
  }
}
