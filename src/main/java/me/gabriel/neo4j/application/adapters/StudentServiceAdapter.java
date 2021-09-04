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
    var department = this.createDepartment(request);

    var isLearningRelations = this.createSubjects(request);

    Student student = this.setupRelationship(request, department, isLearningRelations);

    student = this.studentRepository.create(student);

    return StudentResponse.from(student);
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

  private Student setupRelationship(StudentCreateRequest request, Department department, List<IsLearning> isLearningRelations) {
    var student = Student.from(request);

    student.setIsLearning(isLearningRelations);
    student.setDepartment(department);

    return student;
  }

  private List<IsLearning> createSubjects(StudentCreateRequest request) {
    return request.subjects()
      .stream()
      .map(data -> {
        var subject = new Subject(data.name());

        this.subjectRepository.create(subject);

        return new IsLearning(data.marks(), subject);
      }).collect(Collectors.toList());
  }

  private Department createDepartment(StudentCreateRequest request) {
    var department = Department.from(request.department());
    this.departmentRepository.create(department);
    return department;
  }
}
