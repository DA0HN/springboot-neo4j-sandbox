package me.gabriel.neo4j.application.api.request;

import java.util.List;

/**
 * @author daohn
 * @since 19/08/2021
 */
public record StudentCreateRequest(
  String name,
  Integer birthYear,
  String country,
  List<SubjectCreateRequest> subjects,
  DepartmentCreateRequest department
) {

  public String departmentName() {
    return this.department.name();
  }

}
