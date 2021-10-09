package me.gabriel.neo4j.application.adapters;

import lombok.AllArgsConstructor;
import me.gabriel.neo4j.application.api.request.StudentCreateRequest;
import me.gabriel.neo4j.application.api.request.SubjectCreateRequest;
import me.gabriel.neo4j.application.api.response.StudentResponse;
import me.gabriel.neo4j.core.domain.Department;
import me.gabriel.neo4j.core.domain.IsLearning;
import me.gabriel.neo4j.core.domain.Student;
import me.gabriel.neo4j.core.domain.StudentNotFoundException;
import me.gabriel.neo4j.core.ports.StudentRepository;
import me.gabriel.neo4j.core.ports.StudentService;
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
  private final StudentRelationshipCreator<Department, String> belongsToRelationshipCreator;
  private final StudentRelationshipCreator<List<IsLearning>, List<SubjectCreateRequest>> isLearningRelationshipCreator;

  @Override public StudentResponse create(StudentCreateRequest request) {
    var student = Student.from(request);

    this.createRelationships(request, student);

    student = this.studentRepository.create(student);

    return StudentResponse.from(student);
  }

  private void createRelationships(StudentCreateRequest request, Student student) {
    student.relationships(
      this.belongsToRelationshipCreator.create(request.departmentName()),
      this.isLearningRelationshipCreator.create(request.subjects())
    );
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
