package me.gabriel.neo4j.infra.db.repositories;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;

import java.io.Serializable;

/**
 * @author daohn
 * @since 19/08/2021
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class NodeIdentity implements Serializable {
  @Id
  @GeneratedValue
  private Long id;
}
