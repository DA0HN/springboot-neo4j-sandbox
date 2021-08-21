package me.gabriel.neo4j.application.api.response;

import me.gabriel.neo4j.core.domain.Student;

import java.util.List;

/**
 * @author daohn
 * @since 21/08/2021
 */
public record StudentCreateResponse(
  Long id,
  String name,
  Integer birthYear,
  String country,
  List<IsLearningCreateResponse> isLearning,
  DepartmentCreateResponse department
) {
  public static StudentCreateResponse from(Student student) {
    return new StudentCreateResponse(
      student.getId(),
      student.getName(),
      student.getBirthYear(),
      student.getCountry(),
      IsLearningCreateResponse.from(student.getIsLearning()),
      DepartmentCreateResponse.from(student.getDepartment())
    );
  }
}
