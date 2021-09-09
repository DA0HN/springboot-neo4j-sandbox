package me.gabriel.neo4j.utils.data;

import me.gabriel.neo4j.core.domain.IsLearning;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static me.gabriel.neo4j.utils.data.DummyData.id;
import static me.gabriel.neo4j.utils.data.DummyData.marksPercent;
import static me.gabriel.neo4j.utils.data.DummyData.size;
import static me.gabriel.neo4j.utils.data.SubjectFactory.subjectList;
import static me.gabriel.neo4j.utils.data.SubjectFactory.subjectWithId;

public class IsLearningFactory {

  public static List<IsLearning> isLearningList() {

    var subjects = subjectList();

    return Arrays.asList(
      isLearningWithId(),
      isLearningWithId(),
      isLearningWithId()
    );
  }

  public static IsLearning isLearning() {
    return new IsLearning(marksPercent(), subjectWithId());
  }

  public static IsLearning isLearningWithId() {
    return new IsLearning(id(), marksPercent(), subjectWithId());
  }

  public static List<IsLearning> isLearningListRandomWithId() {
    return IntStream.range(0, size())
      .mapToObj(i -> isLearningWithId())
      .collect(Collectors.toCollection(ArrayList::new));
  }

  public static List<IsLearning> isLearningListRandom() {
    return IntStream.range(0, size())
      .mapToObj(i -> isLearning())
      .collect(Collectors.toCollection(ArrayList::new));
  }

}
