package me.gabriel.neo4j.utils;

import me.gabriel.neo4j.application.api.request.DepartmentCreateRequest;
import me.gabriel.neo4j.application.api.request.SubjectCreateRequest;
import me.gabriel.neo4j.application.api.response.IsLearningCreateResponse;
import me.gabriel.neo4j.application.api.response.StudentCreateResponse;
import org.assertj.core.api.AbstractAssert;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author daohn
 * @since 21/08/2021
 */
public class StudentCreateResponseAssert extends AbstractAssert<StudentCreateResponseAssert, StudentCreateResponse> {

  // https://www.baeldung.com/assertj-custom-assertion
  protected StudentCreateResponseAssert(StudentCreateResponse actual) {
    super(actual, StudentCreateResponseAssert.class);
  }

  public static StudentCreateResponseAssert assertThat(StudentCreateResponse actual) {
    return new StudentCreateResponseAssert(actual);
  }

  public StudentCreateResponseAssert hasName(String name) {
    isNotNull();
    if(!actual.name().equalsIgnoreCase(name)) {
      failWithMessage(
        "Expected student to have name '%s' but was '%s'",
        name,
        actual.name()
      );
    }
    return this;
  }

  public StudentCreateResponseAssert hasId() {
    isNotNull();
    if(actual.id() == null) {
      failWithMessage("Expected student id to be non null");
    }
    return this;
  }

  public StudentCreateResponseAssert hasDepartment() {
    isNotNull();
    if(actual.department() == null) {
      failWithMessage("Expected department to be non null");
    }
    return this;
  }

  public StudentCreateResponseAssert hasSameDepartmentName(DepartmentCreateRequest department) {
    isNotNull();
    if(!actual.department().name().equals(department.name())) {
      failWithMessage(
        "Expected department to have name '%s' but was '%s'",
        actual.department().name(),
        department.name()
      );
    }
    return this;
  }

  public StudentCreateResponseAssert hasCountry(String country) {
    isNotNull();
    if(!actual.country().equals(country)) {
      failWithMessage(
        "Expected country to be '%s' but was '%s'",
        actual.country(),
        country
      );
    }
    return this;
  }

  public StudentCreateResponseAssert hasBirthYear(Integer birthYear) {
    isNotNull();
    if(!actual.birthYear().equals(birthYear)) {
      failWithMessage(
        "Expected birth year to be '%s' but was '%s'",
        actual.birthYear(),
        birthYear
      );
    }
    return this;
  }

  public StudentCreateResponseAssert subjectsRelationshipWereCreated() {
    isNotNull();

    actual.isLearning()
      .stream()
      .filter(isLearning -> isLearning.id() == null)
      .findAny()
      .ifPresent(isLearning -> failWithMessage(
        "Expected the relationship 'isLearning' to be created correctly, but found id null"
      ));

    return this;
  }

  public StudentCreateResponseAssert containsTheseSubjects(List<SubjectCreateRequest> subjects) {
    isNotNull();

    var subjectNames = subjects.stream()
      .map(SubjectCreateRequest::name)
      .collect(Collectors.toList());

    actual.isLearning()
      .stream()
      .map(IsLearningCreateResponse::subject)
      .forEach(actualSubject -> {

        Objects.requireNonNull(actualSubject, "Expected Subject be not null");

        if(!subjectNames.contains(actualSubject.name())) {
          failWithMessage("Expected Subject %s to be created correctly, but not found");
        }
        else {
          if(actualSubject.id() == null) {
            failWithMessage("Expected Subject %s to be created correctly, but found id null");
          }
        }
      });
    return this;
  }
}
