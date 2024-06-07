package dev.aviedb.myworkout.util;

public class Latihan {
  private String title;
  private int type;
  private int bodyPart;
  private int equipment;
  private int level;

  public Latihan(String title, int type, int bodyPart, int equipment, int level) {
    this.title = title;
    this.type = type;
    this.bodyPart = bodyPart;
    this.equipment = equipment;
    this.level = level;
  }

  public Latihan(String title, int bodyPart, int equipment, int level) {
    this.title = title;
    this.bodyPart = bodyPart;
    this.equipment = equipment;
    this.level = level;
  }

  // Getters
  public String getTitle() {
    return title;
  }

  public int getType() {
    return type;
  }

  public int getBodyPart() {
    return bodyPart;
  }

  public int getEquipment() {
    return equipment;
  }

  public int getLevel() {
    return level;
  }
}