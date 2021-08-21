package me.gabriel.neo4j.application.api.response;

import me.gabriel.neo4j.core.domain.Department;

/**
 * @author daohn
 * @since 21/08/2021
 */
public record DepartmentCreateResponse(
  Long id,
  String name
) {
  public static DepartmentCreateResponse from(Department department) {
    return new DepartmentCreateResponse(department.getId(), department.getName());
  }
}
