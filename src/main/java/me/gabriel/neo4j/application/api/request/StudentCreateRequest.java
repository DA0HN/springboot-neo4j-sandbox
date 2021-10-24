package me.gabriel.neo4j.application.api.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author daohn
 * @since 19/08/2021
 */
public record StudentCreateRequest(
  @NotEmpty(message = "name.not-empty")
  String name,
  @Max(value = 2000, message = "student.born.after.2000")
  Integer birthYear,
  @NotEmpty
  String country,
  @NotEmpty
  List<SubjectCreateRequest> subjects,
  @NotNull
  DepartmentCreateRequest department
) {

  public String departmentName() {
    return this.department.name();
  }

}
