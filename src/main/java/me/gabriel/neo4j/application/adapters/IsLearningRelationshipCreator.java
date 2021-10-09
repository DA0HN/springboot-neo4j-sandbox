package me.gabriel.neo4j.application.adapters;

import lombok.AllArgsConstructor;
import me.gabriel.neo4j.application.api.request.SubjectCreateRequest;
import me.gabriel.neo4j.core.domain.IsLearning;
import me.gabriel.neo4j.core.domain.Subject;
import me.gabriel.neo4j.core.ports.SubjectRepository;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class IsLearningRelationshipCreator implements StudentRelationshipCreator<List<IsLearning>, List<SubjectCreateRequest>> {

  private final SubjectRepository subjectRepository;

  @Override public List<IsLearning> create(final List<SubjectCreateRequest> request) {
    if(request == null) throw new IllegalArgumentException("Subject list must be not null");
    final var subjects = request.stream()
      .map(this::findOrCreateSubject)
      .collect(Collectors.toList());
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
          .orElseThrow(() -> new IllegalStateException("Subject should be found by name"));
        return new IsLearning(data.marks(), subject);
      })
      .collect(Collectors.toList());
  }


}
