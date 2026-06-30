package com.example.nusapedia;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

public class ProfilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        MaterialToolbar toolbar = findViewById(R.id.toolbar_profil);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        SharedPreferences pref = getSharedPreferences("setting", MODE_PRIVATE);
        boolean darkMode = pref.getBoolean("dark_mode", false);
        terapkanTema(darkMode);
    }

    private void terapkanTema(boolean darkMode) {
        if (darkMode) {
            findViewById(R.id.toolbar_profil).setBackgroundColor(Color.parseColor("#0B3D0B"));
            findViewById(R.id.root_profil).setBackgroundColor(Color.parseColor("#121212"));
            ((TextView) findViewById(R.id.txt_nama)).setTextColor(Color.WHITE);
            ((TextView) findViewById(R.id.txt_nim)).setTextColor(Color.parseColor("#AAAAAA"));
        } else {
            findViewById(R.id.toolbar_profil).setBackgroundColor(Color.parseColor("#2E7D32"));
            findViewById(R.id.root_profil).setBackgroundColor(Color.parseColor("#F5F5F5"));
            ((TextView) findViewById(R.id.txt_nama)).setTextColor(Color.parseColor("#212121"));
            ((TextView) findViewById(R.id.txt_nim)).setTextColor(Color.parseColor("#757575"));
        }
    }
}