package me.gabriel.neo4j.application.adapters;

import me.gabriel.neo4j.application.api.request.StudentCreateRequest;

public interface StudentRelationshipCreator<T> {

  T create(StudentCreateRequest request);

}
