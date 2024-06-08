package dev.aviedb.myworkout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dev.aviedb.myworkout.util.DataLoader;
import dev.aviedb.myworkout.util.DataLoaderSingleton;
import dev.aviedb.myworkout.util.Latihan;
import dev.aviedb.myworkout.util.LatihanAdapter;

public class RecommendationFragment extends Fragment {

  private RecyclerView recyclerView;
  private LatihanAdapter adapter;
  private DataLoader dataLoader;

  public void setRecommendations(List<Latihan> rekomendasi) {
    if (adapter != null) {
      adapter.updateData(rekomendasi);
    }
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_recommendation, container, false);

    recyclerView = view.findViewById(R.id.recyclerView);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    dataLoader = DataLoaderSingleton.getInstance(requireContext());

    adapter = new LatihanAdapter(new ArrayList<>(), dataLoader);
    recyclerView.setAdapter(adapter);

    return view;
  }
}