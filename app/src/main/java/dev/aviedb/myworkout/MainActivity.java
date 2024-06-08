package dev.aviedb.myworkout;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

import dev.aviedb.myworkout.util.Latihan;

public class MainActivity extends AppCompatActivity implements InputFragment.OnRecommendationGeneratedListener {
  private InputFragment inputFragment;
  private RecommendationFragment recommendationFragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    if (savedInstanceState == null) {
      inputFragment = new InputFragment();
      inputFragment.setOnRecommendationGeneratedListener(this);

      recommendationFragment = new RecommendationFragment();

      FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
      transaction.replace(R.id.fragment_container_input, inputFragment);
      transaction.replace(R.id.fragment_container_recommendation, recommendationFragment);
      transaction.commit();
    }
  }

  @Override
  public void onRecommendationGenerated(List<Latihan> rekomendasi) {
    recommendationFragment.setRecommendations(rekomendasi);
  }
}