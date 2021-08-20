package me.gabriel.neo4j.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.gabriel.neo4j.infra.db.repositories.NodeEntity;

/**
 * @author daohn
 * @since 19/08/2021
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Subject extends NodeEntity {
  private String name;
}
