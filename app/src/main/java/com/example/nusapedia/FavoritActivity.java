package com.example.nusapedia;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nusapedia.R;
import com.example.nusapedia.adapter.FavoritAdapter;
import com.example.nusapedia.database.EndemikDatabase;
import com.example.nusapedia.database.FavoritEntity;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FavoritActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayout rootFavorit;
    private MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorit);

        rootFavorit = findViewById(R.id.root_favorit);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        terapkanTemaFavorit();
        loadFavorit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        terapkanTemaFavorit();
        loadFavorit();
    }

    private void terapkanTemaFavorit() {
        SharedPreferences pref = getSharedPreferences("setting", MODE_PRIVATE);
        boolean darkMode = pref.getBoolean("dark_mode", false);

        if (darkMode) {
            rootFavorit.setBackgroundColor(Color.parseColor("#121212"));
            toolbar.setBackgroundColor(Color.parseColor("#0B3D0B"));
            recyclerView.setBackgroundColor(Color.parseColor("#121212"));
        } else {
            rootFavorit.setBackgroundColor(Color.parseColor("#F5F5F5"));
            toolbar.setBackgroundColor(Color.parseColor("#2E7D32"));
            recyclerView.setBackgroundColor(Color.parseColor("#F5F5F5"));
        }
    }

    private void loadFavorit() {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            List<FavoritEntity> list = EndemikDatabase.getInstance(this)
                    .favoritDao()
                    .getAll();

            runOnUiThread(() -> {
                FavoritAdapter adapter = new FavoritAdapter(list);
                recyclerView.setAdapter(adapter);
            });
        });
    }

    public void kembali(View v) {
        finish();
    }
}