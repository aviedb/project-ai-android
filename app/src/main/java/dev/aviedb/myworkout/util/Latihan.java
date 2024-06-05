package dev.aviedb.myworkout.util;

public class Latihan {
  private String title;
  private String type;
  private String bodyPart;
  private String equipment;
  private String level;

  public Latihan(String title, String type, String bodyPart, String equipment, String level) {
    this.title = title;
    this.type = type;
    this.bodyPart = bodyPart;
    this.equipment = equipment;
    this.level = level;
  }

  // Getters and setters
  public String getTitle() {
    return title;
  }

  public String getType() {
    return type;
  }

  public String getBodyPart() {
    return bodyPart;
  }

  public String getEquipment() {
    return equipment;
  }

  public String getLevel() {
    return level;
  }
}