package me.gabriel.neo4j.application.adapters;


import me.gabriel.neo4j.core.domain.InvalidStateException;
import me.gabriel.neo4j.core.domain.Subject;
import me.gabriel.neo4j.core.ports.SubjectRepository;
import me.gabriel.neo4j.utils.data.SubjectFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static me.gabriel.neo4j.utils.data.SubjectFactory.subjectRequestCreateList;
import static me.gabriel.neo4j.utils.data.SubjectFactory.subjectWithDummyName;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tags({@Tag("unit"), @Tag("all")})
@ExtendWith(MockitoExtension.class)
class IsLearningRelationshipCreatorTest {


  private static final Class<Subject> SUBJECT_REPOSITORY_ARG = Subject.class;
  private static final Class<String> STRING_ARG = String.class;
  @InjectMocks
  private IsLearningRelationshipCreator isLearningRelationshipCreator;
  @Mock
  private SubjectRepository subjectRepository;


  @Test
  @DisplayName("Quando o `Subject` já existir deveria busca-lo no banco de dados e criar o relacionamento `IS_LEARNING`")
  void whenSubjectAlreadyExistsShouldFindInDatabaseAndCreateRelationship() {
    when(this.subjectRepository.findByName(isA(STRING_ARG))).thenReturn(Optional.of(subjectWithDummyName()));

    final var subjects = subjectRequestCreateList();
    final var SUBJECT_LIST_SIZE = subjects.size();

    final var response = this.isLearningRelationshipCreator.create(subjects);

    verify(this.subjectRepository, times(SUBJECT_LIST_SIZE)).findByName(anyString());
    verify(this.subjectRepository, never()).create(isA(SUBJECT_REPOSITORY_ARG));
  }

  @Test
  @DisplayName("Quando o `Subject` não existir deveria cria-lo e retornar o relacionamento `IS_LEARNING`")
  void whenSubjectNotExistShouldCreate() {
    when(this.subjectRepository.findByName(isA(STRING_ARG))).thenReturn(Optional.empty());
    when(this.subjectRepository.create(isA(SUBJECT_REPOSITORY_ARG))).thenReturn(SubjectFactory.subjectWithDummyName());

    final var subjects = subjectRequestCreateList();
    final var SUBJECT_LIST_SIZE = subjects.size();

    final var response = this.isLearningRelationshipCreator.create(subjects);

    verify(this.subjectRepository, times(SUBJECT_LIST_SIZE)).findByName(anyString());
    verify(this.subjectRepository, times(SUBJECT_LIST_SIZE)).create(isA(SUBJECT_REPOSITORY_ARG));
  }


  @Test
  @DisplayName("Quando a lista de `Subjects` for nula deveria lançar a exceção `IllegalArgumentException`")
  void whenSubjectsIsNullShouldThrowIllegalArgumentException() {

    assertThatThrownBy(() -> this.isLearningRelationshipCreator.create(null))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("Subject list must be not null");

    verify(this.subjectRepository, never()).findByName(anyString());
    verify(this.subjectRepository, never()).create(isA(SUBJECT_REPOSITORY_ARG));
  }

  @Test
  @DisplayName("Quando não encontrar o `Subject` por nome durante a criação do relacionamento `IS_LEARNING` deveria a exceção `IllegalStateException`")
  void whenSubjectNameNotMatchShouldThrowIllegalStateException() {
    when(this.subjectRepository.findByName(isA(STRING_ARG))).thenReturn(
      Optional.of(SubjectFactory.subjectWithId())
    );
    final var subjects = subjectRequestCreateList();
    final var SUBJECT_LIST_SIZE = subjects.size();
    assertThatThrownBy(() -> this.isLearningRelationshipCreator.create(subjects))
      .isInstanceOf(InvalidStateException.class)
      .hasMessageContaining("Subject should be found by name");
    verify(this.subjectRepository, times(SUBJECT_LIST_SIZE)).findByName(anyString());
    verify(this.subjectRepository, never()).create(isA(SUBJECT_REPOSITORY_ARG));
  }

}
