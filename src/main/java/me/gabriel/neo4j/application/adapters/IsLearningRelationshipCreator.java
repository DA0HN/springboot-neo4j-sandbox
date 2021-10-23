package me.gabriel.neo4j.application.adapters;

import lombok.AllArgsConstructor;
import me.gabriel.neo4j.application.api.request.SubjectCreateRequest;
import me.gabriel.neo4j.configuration.Message;
import me.gabriel.neo4j.core.domain.InvalidStateException;
import me.gabriel.neo4j.core.domain.IsLearning;
import me.gabriel.neo4j.core.domain.Subject;
import me.gabriel.neo4j.core.ports.SubjectRepository;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@AllArgsConstructor
public class IsLearningRelationshipCreator implements StudentRelationshipCreator<List<IsLearning>, List<SubjectCreateRequest>> {

  private final SubjectRepository subjectRepository;

  @Override public List<IsLearning> create(final List<SubjectCreateRequest> request) {
    if(request == null) throw new IllegalArgumentException("Subject list must be not null");
    final var subjects = request.stream()
      .map(this::findOrCreateSubject)
      .toList();
    return this.createIsLearningRelationshipWithSubject(subjects, request);
  }

  private Subject findOrCreateSubject(final SubjectCreateRequest request) {
    final var maybeSubject = this.subjectRepository.findByName(request.name());
    if(maybeSubject.isEmpty()) {
      return this.subjectRepository.create(new Subject(request.name()));
    }
    return maybeSubject.get();
  }

  private List<IsLearning> createIsLearningRelationshipWithSubject(
    final Collection<Subject> subjects,
    final Collection<SubjectCreateRequest> subjectRequest
  ) {
    return subjectRequest
      .stream()
      .map(data -> {
        final var subject = subjects.stream()
          .filter(sub -> sub.getName().equalsIgnoreCase(data.name()))
          .findFirst()
          .orElseThrow(() -> new InvalidStateException(Message.X0_NOT_FOUND_BY_NAME, "Subject"));
        return new IsLearning(data.marks(), subject);
      })
      .toList();
  }


}
