package me.gabriel.neo4j.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.gabriel.neo4j.infra.db.repositories.NodeIdentity;
import org.springframework.data.neo4j.core.schema.Node;

/**
 * @author daohn
 * @since 19/08/2021
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Node(labels = {"Subject"})
public class Subject extends NodeIdentity {
  private String name;

  public Subject(Long id, String name) {
    super(id);
    this.name = name;
  }
}
