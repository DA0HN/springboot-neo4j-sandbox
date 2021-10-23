package me.gabriel.neo4j.core.domain;

import me.gabriel.neo4j.configuration.Messages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tags({@Tag("unit"), @Tag("all")})
class SandboxDomainExceptionTest {

  @BeforeEach
  void setUp() {
  }

  @Test
  @DisplayName("Quando receber um enum do tipo `Message` com argumentos deveria formata-lo de acordo com o enumerado")
  void whenReceiveMessageEnumAndArgsShouldFormat() {
    final var exception = new InvalidStateException(Messages.X0_NOT_FOUND_BY_NAME, "subject");
    assertEquals("subject should be found by name", exception.getMessage());
  }

  @Test
  @DisplayName("Quando receber uma `String` como argumento não deveria formatar")
  void whenReceiveStringShouldNotFormat() {
    final var exception = new InvalidStateException("Internal Server Error");
    assertEquals("Internal Server Error", exception.getMessage());
  }

}
