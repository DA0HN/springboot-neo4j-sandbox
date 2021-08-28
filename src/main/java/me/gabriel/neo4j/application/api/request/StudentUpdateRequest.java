package me.gabriel.neo4j.application.api.request;

/**
 * @author daohn
 * @since 19/08/2021
 */
public record StudentUpdateRequest(
  String name,
  Integer birthYear,
  String country
) {
}
