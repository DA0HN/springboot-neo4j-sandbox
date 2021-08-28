package me.gabriel.neo4j.application.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author daohn
 * @since 19/08/2021
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseBase<T> {

  private String message;
  private T data;

  public ResponseBase<T> data(T data) {
    this.data = data;
    return this;
  }

  public ResponseBase<T> message(String message) {
    this.message = message;
    return this;
  }

}
