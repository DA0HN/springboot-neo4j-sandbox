package me.gabriel.neo4j.application.api.response;

import me.gabriel.neo4j.core.domain.Student;

import java.util.List;

/**
 * @author daohn
 * @since 21/08/2021
 */
public record StudentResponse(
  Long id,
  String name,
  Integer birthYear,
  String country,
  List<IsLearningResponse> isLearning,
  DepartmentResponse department
) {
  public static StudentResponse from(Student student) {
    return new StudentResponse(
      student.getId(),
      student.getName(),
      student.getBirthYear(),
      student.getCountry(),
      IsLearningResponse.from(student.getIsLearning()),
      DepartmentResponse.from(student.getDepartment())
    );
  }
}
