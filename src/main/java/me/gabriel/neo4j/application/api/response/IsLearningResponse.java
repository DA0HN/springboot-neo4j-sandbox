package me.gabriel.neo4j.application.api.response;

import me.gabriel.neo4j.core.domain.IsLearning;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author daohn
 * @since 21/08/2021
 */
public record IsLearningResponse(
  Long id,
  Long marks,
  SubjectResponse subject
) {
  public static List<IsLearningResponse> from(List<IsLearning> isLearning) {
    return isLearning.stream().map(IsLearningResponse::from).collect(Collectors.toList());
  }

  public static IsLearningResponse from(IsLearning isLearning) {
    return new IsLearningResponse(
      isLearning.getId(),
      isLearning.getMarks(),
      SubjectResponse.from(isLearning.getSubject())
    );
  }
}
