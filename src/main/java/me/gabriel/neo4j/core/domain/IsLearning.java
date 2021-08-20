package me.gabriel.neo4j.core.domain;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

/**
 * @author daohn
 * @since 19/08/2021
 */
@Data
@RelationshipProperties
public class IsLearning {

  private Long marks;

  @TargetNode
  private Subject subject;
}
