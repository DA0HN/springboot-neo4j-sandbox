package me.gabriel.neo4j.application.api.response;

import me.gabriel.neo4j.core.domain.IsLearning;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author daohn
 * @since 21/08/2021
 */
public record IsLearningCreateResponse(
  Long id,
  Long marks,
  SubjectCreateResponse subject
) {
  public static List<IsLearningCreateResponse> from(List<IsLearning> isLearning) {
    return isLearning.stream().map(IsLearningCreateResponse::from).collect(Collectors.toList());
  }

  public static IsLearningCreateResponse from(IsLearning isLearning) {
    return new IsLearningCreateResponse(
      isLearning.getId(),
      isLearning.getMarks(),
      SubjectCreateResponse.from(isLearning.getSubject())
    );
  }
}
