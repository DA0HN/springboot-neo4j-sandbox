package me.gabriel.neo4j.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.gabriel.neo4j.application.api.request.CreateDepartmentRequest;
import me.gabriel.neo4j.infra.db.repositories.NodeIdentity;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

/**
 * @author daohn
 * @since 19/08/2021
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Node(labels = {"Department"})
public class Department extends NodeIdentity {

  @Property(name = "department_name")
  private String name;

  public Department(Long id, String name) {
    super(id);
    this.name = name;
  }

  public static Department from(CreateDepartmentRequest request) {
    return new Department(request.name());
  }
}
