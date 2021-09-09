package me.gabriel.neo4j.utils;

import com.github.javafaker.Faker;

import java.util.Locale;

public class DummyData {

  public static final int MAX_MARK_VALUE = 100;
  public static final int MIN_SIZE_VALUE = 0;
  public static final int MIN_MARK_VALUE = 0;
  public static final int MAX_SIZE_VALUE = 8;
  public static final int NUMBER_OF_DIGITS = 4;
  private static final int MIN_VALUE_YEAR = 1970;
  private static final int MAX_VALUE_YEAR = 2030;
  private static final Faker faker = new Faker(new Locale("pt-BR"));

  public static int year() {
    return faker.number().numberBetween(MIN_VALUE_YEAR, MAX_VALUE_YEAR);
  }

  public static String name() {
    return faker.name().nameWithMiddle();
  }

  public static String country() {
    return faker.country().name();
  }

  public static String departmentName() {
    return faker.educator().course();
  }

  public static String subjectName() {
    return faker.job().title();
  }

  public static long marksPercent() {
    return faker.number().numberBetween(MIN_MARK_VALUE, MAX_MARK_VALUE);
  }

  public static long id() {
    return faker.number().randomNumber(NUMBER_OF_DIGITS, false);
  }

  public static int size() {
    return faker.number().numberBetween(MIN_SIZE_VALUE, MAX_SIZE_VALUE);
  }
}
