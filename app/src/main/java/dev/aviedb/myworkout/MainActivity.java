package dev.aviedb.myworkout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.List;

import dev.aviedb.myworkout.util.DataLoader;
import dev.aviedb.myworkout.util.Latihan;
import dev.aviedb.myworkout.util.LatihanAdapter;
import dev.aviedb.myworkout.util.NaiveBayesClassifier;

public class MainActivity extends AppCompatActivity {

  private EditText jenisLatihan, durasiLatihan, tingkatKesulitan;
  private SwitchMaterial peralatanLatihan;
  private Button submitButton;

  private RecyclerView recyclerView;
  private LatihanAdapter adapter;
  private DataLoader dataLoader;
  private NaiveBayesClassifier classifier;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    this.jenisLatihan = this.findViewById(R.id.jenisLatihan);
    this.durasiLatihan = this.findViewById(R.id.durasiLatihan);
    this.tingkatKesulitan = this.findViewById(R.id.tingkatKesulitan);
    this.peralatanLatihan = this.findViewById(R.id.peralatanLatihan);
    this.submitButton = this.findViewById(R.id.submitButton);
    this.recyclerView = this.findViewById(R.id.recyclerView);

    dataLoader = new DataLoader(this);
    List<Latihan> semuaLatihan = dataLoader.loadDataset();

    classifier = new NaiveBayesClassifier();
    classifier.train(semuaLatihan);

    submitButton.setOnClickListener(v -> {
      String jenis = jenisLatihan.getText().toString();
      String durasi = durasiLatihan.getText().toString();
      String kesulitan = tingkatKesulitan.getText().toString();
      boolean peralatan = peralatanLatihan.isChecked();
      String peralatanStr = peralatan ? "Yes" : "No";

      if (jenis.isEmpty() || durasi.isEmpty() || kesulitan.isEmpty()) {
        Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        return;
      }

      Latihan userLatihan = new Latihan("Russian twist", jenis, durasi, kesulitan, peralatanStr);
      List<Latihan> rekomendasi = classifier.recommend(userLatihan, semuaLatihan);
      showRecommendations(rekomendasi);
    });
  }

  private void showRecommendations(List<Latihan> rekomendasi) {
    adapter = new LatihanAdapter(rekomendasi);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
  }
}