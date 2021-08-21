package me.gabriel.neo4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@ComponentScan({
//  "me.gabriel.neo4j.application.api.controllers",
//  "me.gabriel.neo4j.application.adapters",
//  "me.gabriel.neo4j.infra.db.repositories",
//  "me.gabriel.neo4j.infra.db.adapters"
//})
//@EntityScan({
//  "me.gabriel.neo4j.core.domain"
//})
public class Neo4jApplication {

  public static void main(String[] args) {
    SpringApplication.run(Neo4jApplication.class, args);
  }

}
