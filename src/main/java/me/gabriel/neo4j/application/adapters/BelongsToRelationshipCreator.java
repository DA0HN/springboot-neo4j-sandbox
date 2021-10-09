package me.gabriel.neo4j.application.adapters;

import lombok.AllArgsConstructor;
import me.gabriel.neo4j.core.domain.Department;
import me.gabriel.neo4j.core.ports.DepartmentRepository;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class BelongsToRelationshipCreator implements StudentRelationshipCreator<Department, String> {

  private final DepartmentRepository departmentRepository;

  @Override public Department create(String departmentName) {
    if(departmentName == null) {
      throw new IllegalArgumentException("Department name should be not null");
    }

    final var maybeDepartment = this.departmentRepository.findByName(
      departmentName
    );
    if(maybeDepartment.isEmpty()) {
      return this.createDepartment(departmentName);
    }
    return maybeDepartment.get();
  }

  private Department createDepartment(String name) {
    var department = new Department(name);
    this.departmentRepository.create(department);
    return department;
  }
}
