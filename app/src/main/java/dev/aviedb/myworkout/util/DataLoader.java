package dev.aviedb.myworkout.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataLoader {
  private final Context context;

  // Maps to convert categorical values to numerical
  private final Map<String, Integer> typeMap = new HashMap<>();
  private final Map<String, Integer> bodyPartMap = new HashMap<>();
  private final Map<String, Integer> equipmentMap = new HashMap<>();
  private final Map<String, Integer> levelMap = new HashMap<>();

  // Reverse maps to convert numerical values back to categorical values
  private final Map<Integer, String> reverseTypeMap = new HashMap<>();
  private final Map<Integer, String> reverseBodyPartMap = new HashMap<>();
  private final Map<Integer, String> reverseEquipmentMap = new HashMap<>();
  private final Map<Integer, String> reverseLevelMap = new HashMap<>();

  public DataLoader(Context context) {
    this.context = context;
  }

  public List<Latihan> loadDataset() {
    List<Latihan> latihanList = new ArrayList<>();
    AssetManager assetManager = context.getAssets();

    try {
      InputStream is = assetManager.open("gym_dataset.csv");
      CSVReader reader = new CSVReader(new InputStreamReader(is));
      String[] nextLine;

      // Skip header
      reader.readNext();

      int typeCounter = 0;
      int bodyPartCounter = 0;
      int equipmentCounter = 0;
      int levelCounter = 0;

      while ((nextLine = reader.readNext()) != null) {
        Log.d("DataLoader", nextLine.length + "...");
        String title = nextLine[1];
        String type = nextLine[3];
        String bodyPart = nextLine[4];
        String equipment = nextLine[5];
        String level = nextLine[6];
        String desc = nextLine[2].isEmpty() ? null : nextLine[2];
        Double rating = nextLine[7].isEmpty() ? null : Double.valueOf(nextLine[7]);
        String ratingDesc = nextLine[8].isEmpty() ? null : nextLine[8];

        // Map categorical values to numerical and reverse
        if (!typeMap.containsKey(type)) {
          typeMap.put(type, typeCounter);
          reverseTypeMap.put(typeCounter, type);
          typeCounter++;
        }
        if (!bodyPartMap.containsKey(bodyPart)) {
          bodyPartMap.put(bodyPart, bodyPartCounter);
          reverseBodyPartMap.put(bodyPartCounter, bodyPart);
          bodyPartCounter++;
        }
        if (!equipmentMap.containsKey(equipment)) {
          equipmentMap.put(equipment, equipmentCounter);
          reverseEquipmentMap.put(equipmentCounter, equipment);
          equipmentCounter++;
        }
        if (!levelMap.containsKey(level)) {
          levelMap.put(level, levelCounter);
          reverseLevelMap.put(levelCounter, level);
          levelCounter++;
        }

        Latihan latihan = new Latihan(
            title,
            typeMap.get(type),
            bodyPartMap.get(bodyPart),
            equipmentMap.get(equipment),
            levelMap.get(level),
            desc,
            rating,
            ratingDesc
        );
        latihanList.add(latihan);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return latihanList;
  }

  public Map<String, Integer> getTypeMap() {
    return typeMap;
  }

  public Map<String, Integer> getBodyPartMap() {
    return bodyPartMap;
  }

  public Map<String, Integer> getEquipmentMap() {
    return equipmentMap;
  }

  public Map<String, Integer> getLevelMap() {
    return levelMap;
  }

  public int getEquipmentValue(String equipment) {
    // "No" is treated as "Body Only"
    if (equipment.equalsIgnoreCase("No")) {
      return equipmentMap.get("Body Only");
    }
    // "Yes" is treated as any other equipment type
    else {
      // Return the first equipment type that is not "Body Only"
      for (Map.Entry<String, Integer> entry : equipmentMap.entrySet()) {
        if (!entry.getKey().equals("Body Only")) {
          return entry.getValue();
        }
      }
    }
    // Default to "Body Only" if no other equipment found
    return equipmentMap.get("Body Only");
  }

  public String getTypeString(int value) {
    return reverseTypeMap.get(value);
  }

  public String getBodyPartString(int value) {
    return reverseBodyPartMap.get(value);
  }

  public String getEquipmentString(int value) {
    return reverseEquipmentMap.get(value);
  }

  public String getLevelString(int value) {
    return reverseLevelMap.get(value);
  }
}