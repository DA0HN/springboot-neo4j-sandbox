package me.gabriel.neo4j.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.gabriel.neo4j.infra.db.repositories.NodeEntity;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

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

}
