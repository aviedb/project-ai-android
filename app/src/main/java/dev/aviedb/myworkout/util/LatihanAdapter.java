package dev.aviedb.myworkout.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dev.aviedb.myworkout.R;

public class LatihanAdapter extends RecyclerView.Adapter<LatihanAdapter.LatihanViewHolder> {
  private List<Latihan> latihanList;
  private DataLoader dataLoader;

  public LatihanAdapter(List<Latihan> latihanList, DataLoader dataLoader) {
    this.latihanList = latihanList;
    this.dataLoader = dataLoader;
  }

  @NonNull
  @Override
  public LatihanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_latihan, parent, false);
    return new LatihanViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull LatihanViewHolder holder, int position) {
    Latihan latihan = latihanList.get(position);
    holder.bind(latihan, dataLoader);
  }

  @Override
  public int getItemCount() {
    return latihanList.size();
  }

  static class LatihanViewHolder extends RecyclerView.ViewHolder {
    private TextView titleTextView, typeTextView, bodyPartTextView, equipmentTextView, levelTextView, descTextView, ratingTextView, ratingDescTextView;

    LatihanViewHolder(@NonNull View itemView) {
      super(itemView);
      titleTextView = itemView.findViewById(R.id.titleTextView);
      typeTextView = itemView.findViewById(R.id.typeTextView);
      bodyPartTextView = itemView.findViewById(R.id.bodyPartTextView);
      equipmentTextView = itemView.findViewById(R.id.equipmentTextView);
      levelTextView = itemView.findViewById(R.id.levelTextView);
      descTextView = itemView.findViewById(R.id.descTextView);
      ratingTextView = itemView.findViewById(R.id.ratingTextView);
      ratingDescTextView = itemView.findViewById(R.id.ratingDescTextView);
    }

    void bind(Latihan latihan, DataLoader dataLoader) {
      titleTextView.setText(latihan.getTitle());
      typeTextView.setText(dataLoader.getTypeString(latihan.getType()));
      bodyPartTextView.setText(dataLoader.getBodyPartString(latihan.getBodyPart()));
      equipmentTextView.setText(dataLoader.getEquipmentString(latihan.getEquipment()));
      levelTextView.setText(dataLoader.getLevelString(latihan.getLevel()));
      descTextView.setText(latihan.getDesc() != null ? latihan.getDesc() : "No description available");
      ratingTextView.setText((latihan.getRating() != null && latihan.getRating() > 0) ? String.valueOf(latihan.getRating()) : "N/A");
      ratingDescTextView.setText(latihan.getRatingDesc() != null ? latihan.getRatingDesc() : "No rating description");
    }
  }
}