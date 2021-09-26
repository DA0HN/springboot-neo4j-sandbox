package me.gabriel.neo4j.utils.data;

import me.gabriel.neo4j.core.domain.Department;

import static me.gabriel.neo4j.utils.data.DummyData.departmentName;
import static me.gabriel.neo4j.utils.data.DummyData.id;

public class DepartmentFactory {

  public static Department departmentWithId() {
    Department department = department();
    department.setId(id());
    return department;
  }

  public static Department department() {
    return new Department(departmentName());
  }

}
