package me.gabriel.neo4j.application.api.controllers;

import lombok.AllArgsConstructor;
import me.gabriel.neo4j.application.api.request.StudentCreateRequest;
import me.gabriel.neo4j.application.api.response.ResponseBase;
import me.gabriel.neo4j.application.api.response.StudentResponse;
import me.gabriel.neo4j.core.ports.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

  @GetMapping("/{studentId}")
  public ResponseEntity<ResponseBase<StudentResponse>> findById(@PathVariable("studentId") Long studentId) {
    var foundStudent = this.studentService.findById(studentId);

    var response = new ResponseBase<StudentResponse>()
      .data(foundStudent)
      .message("Student found!");
    return ResponseEntity.ok(response);
  }

  @GetMapping
  public ResponseEntity<ResponseBase<List<StudentResponse>>> findByName(@RequestParam("name") String name) {
    var foundStudent = this.studentService.findAllByPartialName(name);

    String message = foundStudent.isEmpty() ? "No students found with name '" + name + "'." : "Student found!";

    var response = new ResponseBase<List<StudentResponse>>()
      .data(foundStudent)
      .message(message);

    return ResponseEntity.ok(response);
  }
}
