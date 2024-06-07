package dev.aviedb.myworkout.util;

public class Latihan {
  private String title;
  private int type;
  private int bodyPart;
  private int equipment;
  private int level;
  private String desc;
  private Double rating;
  private String ratingDesc;

  public Latihan(String title, int type, int bodyPart, int equipment, int level, String desc, Double rating, String ratingDesc) {
    this.title = title;
    this.type = type;
    this.bodyPart = bodyPart;
    this.equipment = equipment;
    this.level = level;
    this.desc = desc;
    this.rating = rating;
    this.ratingDesc = ratingDesc;
  }

  public Latihan(String title, int type, int bodyPart, int equipment, int level) {
    this.title = title;
    this.type = type;
    this.bodyPart = bodyPart;
    this.equipment = equipment;
    this.level = level;
    this.desc = null;
    this.rating = 0.0;
    this.ratingDesc = null;
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

  public String getDesc() {
    return desc;
  }

  public Double getRating() {
    return rating;
  }

  public String getRatingDesc() {
    return ratingDesc;
  }
}