package dev.aviedb.myworkout;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dev.aviedb.myworkout.util.DataLoader;
import dev.aviedb.myworkout.util.DataLoaderSingleton;
import dev.aviedb.myworkout.util.KNNClassifier;
import dev.aviedb.myworkout.util.Latihan;

public class InputFragment extends Fragment {

  private AutoCompleteTextView spinnerBodyPart, spinnerLevel;
  private SwitchMaterial switchEquipment;
  private Button submitButton;
  private DataLoader dataLoader;
  private KNNClassifier classifier;
  private Handler handler;

  private OnRecommendationGeneratedListener listener;

  public interface OnRecommendationGeneratedListener {
    void onRecommendationGenerated(List<Latihan> rekomendasi);
  }

  public void setOnRecommendationGeneratedListener(OnRecommendationGeneratedListener listener) {
    this.listener = listener;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_input, container, false);

    spinnerBodyPart = view.findViewById(R.id.spinnerBodyPart);
    spinnerLevel = view.findViewById(R.id.spinnerLevel);
    switchEquipment = view.findViewById(R.id.switchEquipment);
    submitButton = view.findViewById(R.id.submitButton);

    dataLoader = DataLoaderSingleton.getInstance(requireContext());
    List<Latihan> semuaLatihan = dataLoader.loadDataset();

    classifier = new KNNClassifier(20);
    classifier.train(semuaLatihan);

    List<String> bodyParts = new ArrayList<>(dataLoader.getBodyPartMap().keySet());
    Collections.sort(bodyParts);

    List<String> levels = new ArrayList<>(dataLoader.getLevelMap().keySet());
//    Collections.sort(levels);

    ArrayAdapter<String> bodyPartAdapter = new ArrayAdapter<>(requireContext(), R.layout.dropdown_item, bodyParts);
    spinnerBodyPart.setAdapter(bodyPartAdapter);

    ArrayAdapter<String> levelAdapter = new ArrayAdapter<>(requireContext(), R.layout.dropdown_item, levels);
    spinnerLevel.setAdapter(levelAdapter);

    submitButton.setOnClickListener(v -> onSubmit());

    handler = new Handler(Looper.getMainLooper());

    return view;
  }

  private void onSubmit() {
    new Thread(() -> {
      String bodyPart = spinnerBodyPart.getText().toString();
      String difficultyLevel = spinnerLevel.getText().toString();
      boolean useEquipment = switchEquipment.isChecked();
      String useEquipmentStr = useEquipment ? "Yes" : "No";

      if (bodyPart.isEmpty() || difficultyLevel.isEmpty()) {
        handler.post(() -> Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show());
        return;
      }

      Integer bodyPartValue = dataLoader.getBodyPartMap().get(bodyPart);
      Integer kesulitanValue = dataLoader.getLevelMap().get(difficultyLevel);
      int peralatanValue = dataLoader.getEquipmentValue(useEquipmentStr);

      Latihan userLatihan = new Latihan("User Input", bodyPartValue, peralatanValue, kesulitanValue);
      List<Latihan> rekomendasi = classifier.recommend(userLatihan);

      handler.post(() -> {
        if (listener != null)
          listener.onRecommendationGenerated(rekomendasi);
      });
    }).start();
  }
}