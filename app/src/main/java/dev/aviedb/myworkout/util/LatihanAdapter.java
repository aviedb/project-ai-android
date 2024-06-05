package dev.aviedb.myworkout.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import java.util.List;

import dev.aviedb.myworkout.R;

public class LatihanAdapter extends RecyclerView.Adapter<LatihanAdapter.LatihanViewHolder> {
  private List<Latihan> latihanList;

  public LatihanAdapter(List<Latihan> latihanList) {
    this.latihanList = latihanList;
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
    holder.bind(latihan);
  }

  @Override
  public int getItemCount() {
    return latihanList.size();
  }

  static class LatihanViewHolder extends RecyclerView.ViewHolder {
    private TextView titleTextView, typeTextView, bodyPartTextView, equipmentTextView, levelTextView;

    public LatihanViewHolder(@NonNull View itemView) {
      super(itemView);
      titleTextView = itemView.findViewById(R.id.titleTextView);
      typeTextView = itemView.findViewById(R.id.typeTextView);
      bodyPartTextView = itemView.findViewById(R.id.bodyPartTextView);
      equipmentTextView = itemView.findViewById(R.id.equipmentTextView);
      levelTextView = itemView.findViewById(R.id.levelTextView);
    }

    void bind(Latihan latihan) {
      titleTextView.setText(latihan.getTitle());
      typeTextView.setText(latihan.getType());
      bodyPartTextView.setText(latihan.getBodyPart());
      equipmentTextView.setText(latihan.getEquipment());
      levelTextView.setText(latihan.getLevel());
    }
  }
}