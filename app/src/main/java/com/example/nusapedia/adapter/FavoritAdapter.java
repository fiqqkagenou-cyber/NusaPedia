package com.example.nusapedia.adapter;

import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nusapedia.DetailActivity;
import com.example.nusapedia.R;
import com.example.nusapedia.database.FavoritEntity;

import java.util.List;

public class FavoritAdapter extends RecyclerView.Adapter<FavoritAdapter.ViewHolder> {

    private List<FavoritEntity> list;

    public FavoritAdapter(List<FavoritEntity> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_endemik, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FavoritEntity item = list.get(position);

        SharedPreferences pref = holder.itemView.getContext()
                .getSharedPreferences("setting", Context.MODE_PRIVATE);

        boolean darkMode = pref.getBoolean("dark_mode", false);

        if (darkMode) {
            holder.itemView.setBackgroundResource(R.drawable.rounded_card_dark);
            holder.tvNama.setTextColor(Color.WHITE);
        } else {
            holder.itemView.setBackgroundResource(R.drawable.rounded_card);
            holder.tvNama.setTextColor(Color.parseColor("#212121"));
        }

        holder.tvNama.setText(item.getNama());

        if (item.getFoto() == null || item.getFoto().trim().isEmpty()) {
            holder.ivFoto.setImageResource(R.drawable.ic_placeholder);
        } else {
            Glide.with(holder.itemView.getContext())
                    .load(item.getFoto())
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_placeholder)
                    .into(holder.ivFoto);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), DetailActivity.class);

            intent.putExtra("id", item.getId());
            intent.putExtra("nama", item.getNama());
            intent.putExtra("nama_latin", item.getNamaLatin());
            intent.putExtra("deskripsi", item.getDeskripsi());
            intent.putExtra("asal", item.getAsal());
            intent.putExtra("sebaran", item.getSebaran());
            intent.putExtra("foto", item.getFoto());
            intent.putExtra("tipe", item.getTipe());
            intent.putExtra("status", item.getStatus());

            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFoto;
        TextView tvNama;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFoto = itemView.findViewById(R.id.iv_foto);
            tvNama = itemView.findViewById(R.id.tv_nama);
        }
    }
}