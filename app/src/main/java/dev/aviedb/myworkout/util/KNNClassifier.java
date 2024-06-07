package dev.aviedb.myworkout.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class KNNClassifier {
  private List<Latihan> dataset;
  private int k;

  public KNNClassifier(int k) {
    this.k = k;
    this.dataset = new ArrayList<>();
  }

  public void train(List<Latihan> latihanList) {
    this.dataset = latihanList;
  }

  private double calculateDistance(Latihan a, Latihan b) {
    double distance = 0.0;

    // Calculate Euclidean distance for numerical features
    distance += Math.pow(a.getType() - b.getType(), 2);
    distance += Math.pow(a.getBodyPart() - b.getBodyPart(), 2);
    distance += Math.pow(a.getEquipment() - b.getEquipment(), 2);
    distance += Math.pow(a.getLevel() - b.getLevel(), 2);

    return Math.sqrt(distance);
  }

  public List<Latihan> recommend(Latihan userLatihan) {
    // Calculate distances from userLatihan to all latihan in dataset
    List<LatihanDistance> distances = new ArrayList<>();
    for (Latihan latihan : dataset) {
      double distance = calculateDistance(userLatihan, latihan);
      distances.add(new LatihanDistance(latihan, distance));
    }

    // Sort by distance and get the k nearest neighbors
    List<Latihan> kNearestNeighbors = distances.stream()
        .sorted(Comparator.comparingDouble(LatihanDistance::getDistance))
        .limit(k)
        .map(LatihanDistance::getLatihan)
        .collect(Collectors.toList());

    return kNearestNeighbors;
  }

  private static class LatihanDistance {
    private Latihan latihan;
    private double distance;

    public LatihanDistance(Latihan latihan, double distance) {
      this.latihan = latihan;
      this.distance = distance;
    }

    public Latihan getLatihan() {
      return latihan;
    }

    public double getDistance() {
      return distance;
    }
  }
}