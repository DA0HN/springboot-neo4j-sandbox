package me.gabriel.neo4j.application.api.request;

import java.util.List;

/**
 * @author daohn
 * @since 19/08/2021
 */
public record CreateStudentRequest(
  String name,
  Integer birthYear,
  String country,
  List<CreateSubjectRequest> subjects,
  CreateDepartmentRequest department
) {
}
