package me.gabriel.neo4j.application.adapters;

import lombok.AllArgsConstructor;
import me.gabriel.neo4j.application.api.request.StudentCreateRequest;
import me.gabriel.neo4j.core.domain.Department;
import me.gabriel.neo4j.core.ports.DepartmentRepository;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class BelongsToRelationshipCreator implements StudentRelationshipCreator<Department> {

  private final DepartmentRepository departmentRepository;

  @Override public Department create(StudentCreateRequest request) {

    if(request.departmentName() == null) {
      throw new IllegalArgumentException("Department name should be not null");
    }

    final var maybeDepartment = this.departmentRepository.findByName(
      request.departmentName()
    );
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
}
