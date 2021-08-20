package me.gabriel.neo4j.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.gabriel.neo4j.infra.db.repositories.NodeEntity;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.OUTGOING;

/**
 * @author daohn
 * @since 19/08/2021
 */
@Getter
@Setter
@Node(labels = {"Student"})
@NoArgsConstructor
@AllArgsConstructor
public class Student extends NodeEntity {

  private String name;
  private String country;

  @Property(name = "birth_year")
  private Integer birthYear;

  @Relationship(type = "BELONGS_TO", direction = OUTGOING)
  private Department departments;

  @Relationship(type = "IS_LEARNING", direction = OUTGOING)
  private List<IsLearning> isLearningSubjects;
}
