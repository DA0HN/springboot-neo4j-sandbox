package me.gabriel.neo4j.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.gabriel.neo4j.infra.db.repositories.NodeIdentity;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

/**
 * @author daohn
 * @since 19/08/2021
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RelationshipProperties
public class IsLearning extends NodeIdentity {

  private Long marks;

  @TargetNode
  private Subject subject;
}
