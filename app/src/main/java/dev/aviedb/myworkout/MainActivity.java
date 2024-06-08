package dev.aviedb.myworkout;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dev.aviedb.myworkout.util.DataLoader;
import dev.aviedb.myworkout.util.KNNClassifier;
import dev.aviedb.myworkout.util.Latihan;
import dev.aviedb.myworkout.util.LatihanAdapter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
  private AutoCompleteTextView spinnerBodyPart, spinnerLevel;
  private SwitchMaterial switchEquipment;
  private Button submitButton;
  private RecyclerView recyclerView;
  private LatihanAdapter adapter;
  private DataLoader dataLoader;
  private KNNClassifier classifier;
  private Handler handler;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    spinnerBodyPart = findViewById(R.id.spinnerBodyPart);
    spinnerLevel = findViewById(R.id.spinnerLevel);
    switchEquipment = findViewById(R.id.switchEquipment);
    submitButton = findViewById(R.id.submitButton);
    recyclerView = findViewById(R.id.recyclerView);

    dataLoader = new DataLoader(this);
    List<Latihan> semuaLatihan = dataLoader.loadDataset();

    classifier = new KNNClassifier(20);
    classifier.train(semuaLatihan);

    List<String> bodyParts = new ArrayList<>(dataLoader.getBodyPartMap().keySet());
    Collections.sort(bodyParts);

    List<String> levels = new ArrayList<>(dataLoader.getLevelMap().keySet());
//    Collections.sort(levels);

    ArrayAdapter<String> bodyPartAdapter = new ArrayAdapter<>(this, R.layout.dropdown_item, bodyParts);
    spinnerBodyPart.setAdapter(bodyPartAdapter);

    ArrayAdapter<String> levelAdapter = new ArrayAdapter<>(this, R.layout.dropdown_item, levels);
    spinnerLevel.setAdapter(levelAdapter);

    submitButton.setOnClickListener(this);

    handler = new Handler(Looper.getMainLooper());

    recyclerView.setLayoutManager(new LinearLayoutManager(this));
  }

  @Override
  public void onClick(View v) {
    new Thread(() -> {
      String bodyPart = spinnerBodyPart.getText().toString();
      String kesulitan = spinnerLevel.getText().toString();
      boolean peralatan = switchEquipment.isChecked();
      String peralatanStr = peralatan ? "Yes" : "No";

      if (bodyPart.isEmpty() || kesulitan.isEmpty()) {
        handler.post(() -> Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show());
        return;
      }

      Log.d("MainActivity", "BodyPart: " + bodyPart + ", Kesulitan: " + kesulitan + ", Peralatan: " + peralatanStr);

      Integer bodyPartValue = dataLoader.getBodyPartMap().get(bodyPart);
      Integer kesulitanValue = dataLoader.getLevelMap().get(kesulitan);
      int peralatanValue = dataLoader.getEquipmentValue(peralatanStr);

      if (bodyPartValue == null || kesulitanValue == null) {
        Log.e("MainActivity", bodyPartValue + ",,," + kesulitanValue);
        handler.post(() -> Toast.makeText(this, "Error in input values. Please check and try again.", Toast.LENGTH_SHORT).show());
        return;
      }

      Latihan userLatihan = new Latihan("User Input", bodyPartValue, peralatanValue, kesulitanValue);
      List<Latihan> rekomendasi = classifier.recommend(userLatihan);

      handler.post(() -> showRecommendations(rekomendasi));
    }).start();
  }

  private void showRecommendations(List<Latihan> rekomendasi) {
    adapter = new LatihanAdapter(rekomendasi, dataLoader);
    recyclerView.setAdapter(adapter);
    recyclerView.setVisibility(View.VISIBLE);
  }
}