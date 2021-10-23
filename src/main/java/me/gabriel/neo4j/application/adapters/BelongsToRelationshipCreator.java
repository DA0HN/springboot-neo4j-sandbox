package me.gabriel.neo4j.application.adapters;

import lombok.AllArgsConstructor;
import me.gabriel.neo4j.configuration.Messages;
import me.gabriel.neo4j.core.domain.Department;
import me.gabriel.neo4j.core.domain.SandboxDomainException;
import me.gabriel.neo4j.core.ports.DepartmentRepository;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class BelongsToRelationshipCreator implements StudentRelationshipCreator<Department, String> {

  private final DepartmentRepository departmentRepository;

  @Override public Department create(final String departmentName) {
    if(departmentName == null) {
      throw new SandboxDomainException(Messages.X0_NAME_SHOULD_NOT_NULL, "Department");
    }

    final var maybeDepartment = this.departmentRepository.findByName(
      departmentName
    );
    if(maybeDepartment.isEmpty()) {
      return this.createDepartment(departmentName);
    }
    return maybeDepartment.get();
  }

  private Department createDepartment(final String name) {
    final var department = new Department(name);
    this.departmentRepository.create(department);
    return department;
  }
}
