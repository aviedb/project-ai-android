package dev.aviedb.myworkout;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import dev.aviedb.myworkout.util.DataLoader;
import dev.aviedb.myworkout.util.DataLoaderSingleton;
import dev.aviedb.myworkout.util.Latihan;
import dev.aviedb.myworkout.util.LatihanAdapter;

public class RecommendationActivity extends AppCompatActivity {
  private RecyclerView recyclerView;
  private LatihanAdapter adapter;
  private DataLoader dataLoader;
  List<Latihan> rekomendasi;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recommendation);

    MaterialToolbar toolbar = findViewById(R.id.materialToolbar2);
    setSupportActionBar(toolbar);

    if (getSupportActionBar() != null)
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
      @Override
      public void handleOnBackPressed() {
        finish();
      }
    });

    recyclerView = findViewById(R.id.recyclerView);
    dataLoader = DataLoaderSingleton.getInstance(this);

    String rekomendasiJson = getIntent().getStringExtra("REKOMENDASI_JSON");
    Gson gson = new Gson();
    Type listType = new TypeToken<List<Latihan>>() {}.getType();
    rekomendasi = gson.fromJson(rekomendasiJson, listType);

    adapter = new LatihanAdapter(rekomendasi, dataLoader);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
  }

  @Override
  public boolean onSupportNavigateUp() {
    finish();
    return true;
  }
}