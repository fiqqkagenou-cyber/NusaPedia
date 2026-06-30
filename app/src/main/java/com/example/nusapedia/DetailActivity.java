package com.example.nusapedia;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.nusapedia.R;
import com.example.nusapedia.database.EndemikDatabase;
import com.example.nusapedia.database.FavoritEntity;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DetailActivity extends AppCompatActivity {

    private String id, nama, namaLatin, deskripsi, asal, sebaran, foto, tipe, status;
    private boolean isFavorit = false;
    private ImageView ivHati;

    private LinearLayout rootDetail;
    private ScrollView scrollDetail;
    private LinearLayout cardDetail;
    private MaterialToolbar toolbar;

    private TextView tvNamaLatin;
    private TextView tvDeskripsi;
    private TextView tvAsal;
    private TextView tvSebaran;
    private TextView tvStatus;
    private TextView tvLabelAsal;
    private TextView tvLabelSebaran;
    private TextView tvLabelDeskripsi;
    private View garis1;
    private View garis2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        id = getIntent().getStringExtra("id");
        nama = getIntent().getStringExtra("nama");
        namaLatin = getIntent().getStringExtra("nama_latin");
        deskripsi = getIntent().getStringExtra("deskripsi");
        asal = getIntent().getStringExtra("asal");
        sebaran = getIntent().getStringExtra("sebaran");
        foto = getIntent().getStringExtra("foto");
        tipe = getIntent().getStringExtra("tipe");
        status = getIntent().getStringExtra("status");

        rootDetail = findViewById(R.id.root_detail);
        scrollDetail = findViewById(R.id.scroll_detail);
        cardDetail = findViewById(R.id.card_detail);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(nama);
        toolbar.setNavigationOnClickListener(v -> finish());

        ImageView ivFoto = findViewById(R.id.iv_foto);
        tvNamaLatin = findViewById(R.id.tv_nama_latin);
        tvDeskripsi = findViewById(R.id.tv_deskripsi);
        tvAsal = findViewById(R.id.tv_asal);
        tvSebaran = findViewById(R.id.tv_sebaran);
        tvStatus = findViewById(R.id.tv_status);
        tvLabelAsal = findViewById(R.id.tv_label_asal);
        tvLabelSebaran = findViewById(R.id.tv_label_sebaran);
        tvLabelDeskripsi = findViewById(R.id.tv_label_deskripsi);
        garis1 = findViewById(R.id.garis_1);
        garis2 = findViewById(R.id.garis_2);
        ivHati = findViewById(R.id.iv_hati);

        terapkanTemaDetail();

        if (foto == null || foto.trim().isEmpty()) {
            ivFoto.setImageResource(R.drawable.ic_placeholder);
        } else {
            Glide.with(this)
                    .load(foto)
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_placeholder)
                    .into(ivFoto);
        }

        tvNamaLatin.setText(namaLatin);
        tvDeskripsi.setText(deskripsi);
        tvAsal.setText("Asal: " + asal);
        tvSebaran.setText("Sebaran: " + sebaran);
        tvStatus.setText("Status: " + status);

        checkFavoritStatus();

        ivHati.setOnClickListener(v -> toggleFavorit());
    }

    private void terapkanTemaDetail() {
        SharedPreferences pref = getSharedPreferences("setting", MODE_PRIVATE);
        boolean darkMode = pref.getBoolean("dark_mode", false);

        if (darkMode) {
            rootDetail.setBackgroundColor(Color.parseColor("#121212"));
            scrollDetail.setBackgroundColor(Color.parseColor("#121212"));
            cardDetail.setBackgroundResource(R.drawable.rounded_card_dark);
            toolbar.setBackgroundColor(Color.parseColor("#0B3D0B"));

            tvNamaLatin.setTextColor(Color.parseColor("#81C784"));
            tvDeskripsi.setTextColor(Color.WHITE);
            tvAsal.setTextColor(Color.WHITE);
            tvSebaran.setTextColor(Color.WHITE);
            tvStatus.setTextColor(Color.WHITE);

            tvLabelAsal.setTextColor(Color.parseColor("#BDBDBD"));
            tvLabelSebaran.setTextColor(Color.parseColor("#BDBDBD"));
            tvLabelDeskripsi.setTextColor(Color.parseColor("#BDBDBD"));

            garis1.setBackgroundColor(Color.parseColor("#333333"));
            garis2.setBackgroundColor(Color.parseColor("#333333"));

        } else {
            rootDetail.setBackgroundColor(Color.parseColor("#F5F5F5"));
            scrollDetail.setBackgroundColor(Color.parseColor("#F5F5F5"));
            cardDetail.setBackgroundResource(R.drawable.rounded_card);
            toolbar.setBackgroundColor(Color.parseColor("#2E7D32"));

            tvNamaLatin.setTextColor(Color.parseColor("#2E7D32"));
            tvDeskripsi.setTextColor(Color.parseColor("#212121"));
            tvAsal.setTextColor(Color.parseColor("#212121"));
            tvSebaran.setTextColor(Color.parseColor("#212121"));
            tvStatus.setTextColor(Color.parseColor("#212121"));

            tvLabelAsal.setTextColor(Color.parseColor("#757575"));
            tvLabelSebaran.setTextColor(Color.parseColor("#757575"));
            tvLabelDeskripsi.setTextColor(Color.parseColor("#757575"));

            garis1.setBackgroundColor(Color.parseColor("#E0E0E0"));
            garis2.setBackgroundColor(Color.parseColor("#E0E0E0"));
        }
    }

    private void checkFavoritStatus() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            int count = EndemikDatabase.getInstance(this).favoritDao().isFavorit(id);
            isFavorit = count > 0;
            runOnUiThread(() -> updateHatiIcon());
        });
    }

    private void toggleFavorit() {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            FavoritEntity entity = new FavoritEntity();
            entity.setId(id);
            entity.setTipe(tipe);
            entity.setNama(nama);
            entity.setNamaLatin(namaLatin);
            entity.setDeskripsi(deskripsi);
            entity.setAsal(asal);
            entity.setSebaran(sebaran);
            entity.setFoto(foto);
            entity.setStatus(status);

            if (isFavorit) {
                EndemikDatabase.getInstance(this)
                        .favoritDao()
                        .deleteById(id);

                isFavorit = false;

                runOnUiThread(() -> {
                    updateHatiIcon();
                    Toast.makeText(this, "Dihapus dari Favorit", Toast.LENGTH_SHORT).show();
                });

            } else {
                EndemikDatabase.getInstance(this)
                        .favoritDao()
                        .insert(entity);

                isFavorit = true;

                runOnUiThread(() -> {
                    updateHatiIcon();
                    Toast.makeText(this, "Ditambahkan ke Favorit", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    private void updateHatiIcon() {
        if (isFavorit) {
            ivHati.setImageResource(android.R.drawable.btn_star_big_on);
        } else {
            ivHati.setImageResource(android.R.drawable.btn_star_big_off);
        }
    }
}