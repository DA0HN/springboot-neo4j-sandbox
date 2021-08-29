package me.gabriel.neo4j.application.api.controllers;

import lombok.AllArgsConstructor;
import me.gabriel.neo4j.application.api.request.StudentCreateRequest;
import me.gabriel.neo4j.application.api.response.ResponseBase;
import me.gabriel.neo4j.application.api.response.StudentResponse;
import me.gabriel.neo4j.core.ports.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author daohn
 * @since 19/08/2021
 */
@RestController
@RequestMapping("/student")
@AllArgsConstructor
public class StudentController {

  private StudentService studentService;

  @PostMapping
  public ResponseEntity<ResponseBase<StudentResponse>> create(
    @RequestBody StudentCreateRequest request
  ) {
    var studentCreateRequest = this.studentService.create(request);

    var response = new ResponseBase<StudentResponse>()
      .data(studentCreateRequest)
      .message("Student created successfully");

    return ResponseEntity.ok(response);
  }

}
