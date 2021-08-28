package me.gabriel.neo4j.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.gabriel.neo4j.application.api.request.StudentCreateRequest;
import me.gabriel.neo4j.infra.db.repositories.NodeIdentity;
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
public class Student extends NodeIdentity {

  private String name;
  private String country;

  @Property(name = "birth_year")
  private Integer birthYear;

  @Relationship(type = "BELONGS_TO", direction = OUTGOING)
  private Department department;

  @Relationship(type = "IS_LEARNING", direction = OUTGOING)
  private List<IsLearning> isLearning;

  public Student(
    Long id,
    String name,
    String country,
    Integer birthYear,
    Department department,
    List<IsLearning> isLearning
  ) {
    super(id);
    this.name = name;
    this.country = country;
    this.birthYear = birthYear;
    this.department = department;
    this.isLearning = isLearning;
  }

  public static Student from(StudentCreateRequest request) {
    var student = new Student();
    student.setName(request.name());
    student.setCountry(request.country());
    student.setBirthYear(request.birthYear());
    return student;
  }
}
