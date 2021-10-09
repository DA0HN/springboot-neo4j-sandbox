package me.gabriel.neo4j.utils.data;

import me.gabriel.neo4j.application.api.request.SubjectCreateRequest;
import me.gabriel.neo4j.core.domain.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static me.gabriel.neo4j.utils.data.DummyData.id;
import static me.gabriel.neo4j.utils.data.DummyData.size;
import static me.gabriel.neo4j.utils.data.DummyData.subjectName;

public class SubjectFactory {

  public static Subject subject() {
    return new Subject(subjectName());
  }

  public static Subject subjectWithId() {
    Subject subject = subject();
    subject.setId(id());
    return subject;
  }

  public static Subject subjectWithDummyName() {
    Subject subject = subject();
    subject.setName("subject 1");
    subject.setId(id());
    return subject;
  }

  public static List<Subject> subjectListRandom() {
    return IntStream.range(0, size())
      .mapToObj(i -> subject())
      .collect(Collectors.toCollection(ArrayList::new));
  }

  public static List<Subject> subjectListRandomWithId() {
    return IntStream.range(0, size())
      .mapToObj(i -> subjectWithId())
      .collect(Collectors.toCollection(ArrayList::new));
  }

  public static Subject[] subjectList() {
    return new Subject[]{
      subjectWithId(),
      subjectWithId(),
      subjectWithId(),
    };
  }

  public static List<SubjectCreateRequest> subjectRequestCreateList() {
    return asList(
      subjectCreateRequest(),
      subjectCreateRequest(),
      subjectCreateRequest()
    );
  }

  public static SubjectCreateRequest subjectCreateRequest() {
    return new SubjectCreateRequest(
      "subject 1",
      10L
    );
  }
}
