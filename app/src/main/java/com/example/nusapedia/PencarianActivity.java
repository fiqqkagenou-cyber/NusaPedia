package com.example.nusapedia;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nusapedia.R;
import com.example.nusapedia.adapter.EndemikAdapter;
import com.example.nusapedia.database.EndemikDatabase;
import com.example.nusapedia.database.EndemikEntity;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PencarianActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText etCari;
    private ImageButton btnClear;

    private LinearLayout rootPencarian;
    private LinearLayout layoutSearchArea;
    private LinearLayout layoutSearchBox;
    private MaterialToolbar toolbar;
    private TextView txtJudulPencarian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pencarian);

        rootPencarian = findViewById(R.id.root_pencarian);
        layoutSearchArea = findViewById(R.id.layout_search_area);
        layoutSearchBox = findViewById(R.id.layout_search_box);
        toolbar = findViewById(R.id.toolbar);
        txtJudulPencarian = findViewById(R.id.txt_judul_pencarian);

        etCari = findViewById(R.id.et_cari);
        btnClear = findViewById(R.id.btn_clear);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        terapkanTemaPencarian();

        ImageButton btnCari = findViewById(R.id.btn_cari);
        btnCari.setOnClickListener(v -> {
            String keyword = etCari.getText().toString().trim();
            cariData(keyword);
        });

        btnClear.setOnClickListener(v -> {
            etCari.setText("");
            cariData("");
        });

        cariData("");
    }

    private void terapkanTemaPencarian() {
        SharedPreferences pref = getSharedPreferences("setting", MODE_PRIVATE);
        boolean darkMode = pref.getBoolean("dark_mode", false);

        if (darkMode) {
            rootPencarian.setBackgroundColor(Color.parseColor("#121212"));
            layoutSearchArea.setBackgroundColor(Color.parseColor("#121212"));
            layoutSearchBox.setBackgroundResource(R.drawable.rounded_search_dark);

            toolbar.setBackgroundColor(Color.parseColor("#0B3D0B"));
            txtJudulPencarian.setTextColor(Color.WHITE);

            etCari.setTextColor(Color.WHITE);
            etCari.setHintTextColor(Color.parseColor("#BDBDBD"));

        } else {
            rootPencarian.setBackgroundColor(Color.parseColor("#F5F5F5"));
            layoutSearchArea.setBackgroundColor(Color.parseColor("#F5F5F5"));
            layoutSearchBox.setBackgroundResource(R.drawable.rounded_search);

            toolbar.setBackgroundColor(Color.parseColor("#2E7D32"));
            txtJudulPencarian.setTextColor(Color.WHITE);

            etCari.setTextColor(Color.parseColor("#212121"));
            etCari.setHintTextColor(Color.parseColor("#757575"));
        }
    }

    private void cariData(String keyword) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            List<EndemikEntity> list = EndemikDatabase.getInstance(this)
                    .endemikDao()
                    .search(keyword);

            runOnUiThread(() -> {
                EndemikAdapter adapter = new EndemikAdapter(list);
                recyclerView.setAdapter(adapter);
            });
        });
    }

    public void kembali(View v) {
        finish();
    }

    public void onFavoritClick(View v) {
        startActivity(new Intent(this, FavoritActivity.class));
    }
}