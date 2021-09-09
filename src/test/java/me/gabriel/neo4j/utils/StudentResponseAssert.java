package me.gabriel.neo4j.utils;

import me.gabriel.neo4j.application.api.request.DepartmentCreateRequest;
import me.gabriel.neo4j.application.api.request.SubjectCreateRequest;
import me.gabriel.neo4j.application.api.response.IsLearningResponse;
import me.gabriel.neo4j.application.api.response.StudentResponse;
import org.assertj.core.api.AbstractAssert;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author daohn
 * @since 21/08/2021
 */
public class StudentResponseAssert extends AbstractAssert<StudentResponseAssert, StudentResponse> {

  // https://www.baeldung.com/assertj-custom-assertion
  protected StudentResponseAssert(StudentResponse actual) {
    super(actual, StudentResponseAssert.class);
  }

  public static StudentResponseAssert assertThat(StudentResponse actual) {
    return new StudentResponseAssert(actual);
  }

  public StudentResponseAssert hasName(String name) {
    this.isNotNull();
    if(!this.actual.name().equalsIgnoreCase(name)) {
      this.failWithMessage(
        "Expected student to have name '%s' but was '%s'",
        name,
        this.actual.name()
      );
    }
    return this;
  }

  public StudentResponseAssert hasId() {
    this.isNotNull();
    if(this.actual.id() == null) {
      this.failWithMessage("Expected student id to be non null");
    }
    return this;
  }

  public StudentResponseAssert hasDepartment() {
    this.isNotNull();
    if(this.actual.department() == null) {
      this.failWithMessage("Expected department to be non null");
    }
    return this;
  }

  public StudentResponseAssert hasSameDepartmentName(DepartmentCreateRequest department) {
    this.isNotNull();
    if(!this.actual.department().name().equals(department.name())) {
      this.failWithMessage(
        "Expected department to have name '%s' but was '%s'",
        this.actual.department().name(),
        department.name()
      );
    }
    return this;
  }

  public StudentResponseAssert hasCountry(String country) {
    this.isNotNull();
    if(!this.actual.country().equals(country)) {
      this.failWithMessage(
        "Expected country to be '%s' but was '%s'",
        this.actual.country(),
        country
      );
    }
    return this;
  }

  public StudentResponseAssert hasBirthYear(Integer birthYear) {
    this.isNotNull();
    if(!this.actual.birthYear().equals(birthYear)) {
      this.failWithMessage(
        "Expected birth year to be '%s' but was '%s'",
        this.actual.birthYear(),
        birthYear
      );
    }
    return this;
  }

  public StudentResponseAssert subjectsRelationshipWereCreated() {
    this.isNotNull();

    this.actual.isLearning()
      .stream()
      .filter(isLearning -> isLearning.id() == null)
      .findAny()
      .ifPresent(isLearning -> this.failWithMessage(
        "Expected the relationship 'isLearning' to be created correctly, but found id null"
      ));

    return this;
  }

  public StudentResponseAssert containsTheseSubjects(List<SubjectCreateRequest> subjects) {
    this.isNotNull();

    var subjectNames = subjects.stream()
      .map(SubjectCreateRequest::name)
      .collect(Collectors.toList());

    this.actual.isLearning()
      .stream()
      .map(IsLearningResponse::subject)
      .forEach(actualSubject -> {

        Objects.requireNonNull(actualSubject, "Expected Subject be not null");

        if(!subjectNames.contains(actualSubject.name())) {
          this.failWithMessage("Expected Subject %s to be created correctly, but not found", actualSubject.name());
        }
        else {
          if(actualSubject.id() == null) {
            this.failWithMessage("Expected Subject %s to be created correctly, but found id null", actualSubject.name());
          }
        }
      });
    return this;
  }
}
