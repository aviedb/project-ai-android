package dev.aviedb.myworkout.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NaiveBayesClassifier {
  private Map<String, Integer> typeCount = new HashMap<>();
  private Map<String, Integer> bodyPartCount = new HashMap<>();
  private Map<String, Integer> equipmentCount = new HashMap<>();
  private Map<String, Integer> levelCount = new HashMap<>();
  private int totalSamples;

  public void train(List<Latihan> latihanList) {
    for (Latihan latihan : latihanList) {
      typeCount.put(latihan.getType(), typeCount.getOrDefault(latihan.getType(), 0) + 1);
      bodyPartCount.put(latihan.getBodyPart(), bodyPartCount.getOrDefault(latihan.getBodyPart(), 0) + 1);
      equipmentCount.put(latihan.getEquipment(), equipmentCount.getOrDefault(latihan.getEquipment(), 0) + 1);
      levelCount.put(latihan.getLevel(), levelCount.getOrDefault(latihan.getLevel(), 0) + 1);
      totalSamples++;
    }
  }

  private double calculateProbability(String feature, Map<String, Integer> featureCount) {
    return (double) featureCount.getOrDefault(feature, 0) / totalSamples;
  }

  public List<Latihan> recommend(Latihan userLatihan, List<Latihan> semuaLatihan) {
    List<Latihan> recommendations = new ArrayList<>();
    double maxProbability = 0;

    for (Latihan latihan : semuaLatihan) {
      double probability = calculateProbability(latihan.getType(), typeCount) *
          calculateProbability(latihan.getBodyPart(), bodyPartCount) *
          calculateProbability(latihan.getEquipment(), equipmentCount) *
          calculateProbability(latihan.getLevel(), levelCount);

      if (probability > maxProbability) {
        maxProbability = probability;
        recommendations.clear();
        recommendations.add(latihan);
      } else if (probability == maxProbability) {
        recommendations.add(latihan);
      }
    }

    return recommendations;
  }
}