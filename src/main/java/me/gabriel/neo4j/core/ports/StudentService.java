package me.gabriel.neo4j.core.ports;

import me.gabriel.neo4j.application.api.request.CreateStudentRequest;
import me.gabriel.neo4j.application.api.response.StudentCreateResponse;

/**
 * @author daohn
 * @since 19/08/2021
 */
public interface StudentService {

  StudentCreateResponse create(CreateStudentRequest request);

}
