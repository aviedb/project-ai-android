package dev.aviedb.myworkout.util;

import android.content.Context;
import android.content.res.AssetManager;

import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {
  private Context context;

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

      while ((nextLine = reader.readNext()) != null) {
        String title = nextLine[1];
        String type = nextLine[3];
        String bodyPart = nextLine[4];
        String equipment = nextLine[5];
        String level = nextLine[6];

        Latihan latihan = new Latihan(title, type, bodyPart, equipment, level);
        latihanList.add(latihan);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return latihanList;
  }
}