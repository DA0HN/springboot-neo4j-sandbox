package me.gabriel.neo4j.application.api.request;

/**
 * @author daohn
 * @since 19/08/2021
 */
public record UpdateStudentRequest(
  String name,
  Integer birthYear,
  String country
) {
}
