package me.gabriel.neo4j.application;

import me.gabriel.neo4j.application.api.response.StudentCreateResponse;
import org.assertj.core.api.AbstractAssert;

/**
 * @author daohn
 * @since 21/08/2021
 */
public class StudentCreateResponseAssert extends AbstractAssert<StudentCreateResponseAssert, StudentCreateResponse> {

  protected StudentCreateResponseAssert(StudentCreateResponse actual) {
    super(actual, StudentCreateResponseAssert.class);
  }
  // https://www.baeldung.com/assertj-custom-assertion


}
