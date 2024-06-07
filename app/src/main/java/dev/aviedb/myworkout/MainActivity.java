package dev.aviedb.myworkout;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;
import java.util.List;

import dev.aviedb.myworkout.util.DataLoader;
import dev.aviedb.myworkout.util.KNNClassifier;
import dev.aviedb.myworkout.util.Latihan;
import dev.aviedb.myworkout.util.LatihanAdapter;

public class MainActivity extends AppCompatActivity {
  private Spinner spinnerType, spinnerBodyPart, spinnerLevel;
  private SwitchMaterial switchEquipment;
  private Button submitButton;
  private RecyclerView recyclerView;
  private LatihanAdapter adapter;
  private DataLoader dataLoader;
  private KNNClassifier classifier;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    spinnerType = findViewById(R.id.spinnerType);
    spinnerBodyPart = findViewById(R.id.spinnerBodyPart);
    spinnerLevel = findViewById(R.id.spinnerLevel);
    switchEquipment = findViewById(R.id.switchEquipment);
    submitButton = findViewById(R.id.submitButton);
    recyclerView = findViewById(R.id.recyclerView);

    dataLoader = new DataLoader(this);
    List<Latihan> semuaLatihan = dataLoader.loadDataset();

    classifier = new KNNClassifier(3); // K value for KNN
    classifier.train(semuaLatihan);

    // Set up the spinners with the categories from the dataset
    ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this,
        android.R.layout.simple_spinner_item, new ArrayList<>(dataLoader.getTypeMap().keySet()));
    typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinnerType.setAdapter(typeAdapter);

    ArrayAdapter<String> bodyPartAdapter = new ArrayAdapter<>(this,
        android.R.layout.simple_spinner_item, new ArrayList<>(dataLoader.getBodyPartMap().keySet()));
    bodyPartAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinnerBodyPart.setAdapter(bodyPartAdapter);

    ArrayAdapter<String> levelAdapter = new ArrayAdapter<>(this,
        android.R.layout.simple_spinner_item, new ArrayList<>(dataLoader.getLevelMap().keySet()));
    levelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinnerLevel.setAdapter(levelAdapter);

    submitButton.setOnClickListener(v -> {
      String jenis = spinnerType.getSelectedItem().toString();
      String bodyPart = spinnerBodyPart.getSelectedItem().toString();
      String kesulitan = spinnerLevel.getSelectedItem().toString();
      boolean peralatan = switchEquipment.isChecked();
      String peralatanStr = peralatan ? "Yes" : "No";

      if (jenis.isEmpty() || bodyPart.isEmpty() || kesulitan.isEmpty()) {
        Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        return;
      }

      Log.d("MainActivity", "Jenis: " + jenis + ", BodyPart: " + bodyPart + ", Kesulitan: " + kesulitan + ", Peralatan: " + peralatanStr);

      // Convert user input to numerical values using the same maps as in DataLoader
      Integer jenisValue = dataLoader.getTypeMap().get(jenis);
      Integer bodyPartValue = dataLoader.getBodyPartMap().get(bodyPart);
      Integer kesulitanValue = dataLoader.getLevelMap().get(kesulitan);
      Integer peralatanValue = dataLoader.getEquipmentValue(peralatanStr);

      if (jenisValue == null || bodyPartValue == null || kesulitanValue == null || peralatanValue == null) {
        Log.e("MainActivity", "One of the values is null. Please check the mappings.");
        Toast.makeText(this, "Error in input values. Please check and try again.", Toast.LENGTH_SHORT).show();
        return;
      }

      Latihan userLatihan = new Latihan("User Input", jenisValue, bodyPartValue, kesulitanValue, peralatanValue);
      List<Latihan> rekomendasi = classifier.recommend(userLatihan);
      showRecommendations(rekomendasi);
    });
  }

  private void showRecommendations(List<Latihan> rekomendasi) {
    adapter = new LatihanAdapter(rekomendasi);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
  }
}