package me.gabriel.neo4j.application.adapters;

import lombok.AllArgsConstructor;
import me.gabriel.neo4j.application.api.request.CreateStudentRequest;
import me.gabriel.neo4j.application.api.response.StudentCreateResponse;
import me.gabriel.neo4j.core.domain.Department;
import me.gabriel.neo4j.core.domain.IsLearning;
import me.gabriel.neo4j.core.domain.Student;
import me.gabriel.neo4j.core.domain.Subject;
import me.gabriel.neo4j.core.ports.DepartmentRepository;
import me.gabriel.neo4j.core.ports.StudentRepository;
import me.gabriel.neo4j.core.ports.StudentService;
import me.gabriel.neo4j.core.ports.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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

  @Override public StudentCreateResponse create(CreateStudentRequest request) {
    var department = createDepartment(request);

    var isLearningRelations = createSubjects(request);

    var student = Student.from(request);

    student.setIsLearning(isLearningRelations);
    student.setDepartment(department);

    this.studentRepository.create(student);

    return StudentCreateResponse.from(student);
  }

  private List<IsLearning> createSubjects(CreateStudentRequest request) {
    return request.subjects()
      .stream()
      .map(data -> {
        var subject = new Subject(data.name());

        this.subjectRepository.create(subject);

        return new IsLearning(data.marks(), subject);
      }).collect(Collectors.toList());
  }

  private Department createDepartment(CreateStudentRequest request) {
    var department = Department.from(request.department());
    departmentRepository.create(department);
    return department;
  }
}
