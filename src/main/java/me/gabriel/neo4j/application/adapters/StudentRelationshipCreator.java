package me.gabriel.neo4j.application.adapters;

public interface StudentRelationshipCreator<RESULT, ARGUMENT> {

  RESULT create(ARGUMENT request);

}
