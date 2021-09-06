package me.gabriel.neo4j.core.ports;

import me.gabriel.neo4j.application.api.request.StudentCreateRequest;
import me.gabriel.neo4j.application.api.response.StudentResponse;

import java.util.List;

/**
 * @author daohn
 * @since 19/08/2021
 */
public interface StudentService {

  StudentResponse create(StudentCreateRequest request);

  StudentResponse findById(Long studentId);
  List<StudentResponse> findAllByPartialName(String name);
}
