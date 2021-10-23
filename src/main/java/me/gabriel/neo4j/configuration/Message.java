package me.gabriel.neo4j.configuration;

public enum Message {
  X0_NOT_FOUND_BY_NAME("x0.not-found.by.name"),
  X0_NAME_SHOULD_NOT_NULL("x0.name.not-null"),
  PARTIAL_NAME_NOT_NULL("partial.name.not-null"),
  X0_ID_NOT_NULL("x0.id.not-null"),
  X0_WITH_ID_X1_NOT_FOUND("x0.with-id.x1.not-found"),
  X0_LIST_NOT_NULL("x0.list.not-null");

  private final String messageKey;

  Message(final String messageKey) {
    this.messageKey = messageKey;
  }

  public String messageAsKey() {
    return this.messageKey;
  }


}
