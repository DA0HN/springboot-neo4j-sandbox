package me.gabriel.neo4j.application.api.response;

import me.gabriel.neo4j.core.domain.Department;

/**
 * @author daohn
 * @since 21/08/2021
 */
public record DepartmentResponse(
  Long id,
  String name
) {
  public static DepartmentResponse from(Department department) {
    return new DepartmentResponse(department.getId(), department.getName());
  }
}
