package com.example.nusapedia;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private LinearLayout rootLayout;
    private LinearLayout layoutMode;
    private TextView txtMode;
    private MaterialToolbar toolbar;
    private BottomNavigationView bottomNav;
    private SwitchCompat switchDarkMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        rootLayout = findViewById(R.id.root_home);
        layoutMode = findViewById(R.id.layout_mode);
        txtMode = findViewById(R.id.txt_mode);
        toolbar = findViewById(R.id.toolbar);
        bottomNav = findViewById(R.id.bottom_nav);
        switchDarkMode = findViewById(R.id.switch_dark_mode);

        setupDarkMode();
        setupBottomNav();

        loadFragment(new HewanFragment());
    }

    private void setupDarkMode() {
        SharedPreferences pref = getSharedPreferences("setting", MODE_PRIVATE);
        boolean darkMode = pref.getBoolean("dark_mode", false);

        switchDarkMode.setChecked(darkMode);
        terapkanTema(darkMode);

        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            pref.edit().putBoolean("dark_mode", isChecked).apply();
            terapkanTema(isChecked);

            int selectedId = bottomNav.getSelectedItemId();
            if (selectedId == R.id.nav_tumbuhan) {
                loadFragment(new TumbuhanFragment());
            } else {
                loadFragment(new HewanFragment());
            }
        });
    }

    private void terapkanTema(boolean darkMode) {
        if (darkMode) {
            rootLayout.setBackgroundColor(Color.parseColor("#121212"));
            layoutMode.setBackgroundColor(Color.parseColor("#1E1E1E"));
            txtMode.setTextColor(Color.WHITE);
            toolbar.setBackgroundColor(Color.parseColor("#0B3D0B"));
            bottomNav.setBackgroundColor(Color.parseColor("#0B3D0B"));
        } else {
            rootLayout.setBackgroundColor(Color.parseColor("#F5F5F5"));
            layoutMode.setBackgroundColor(Color.parseColor("#F5F5F5"));
            txtMode.setTextColor(Color.parseColor("#212121"));
            toolbar.setBackgroundColor(Color.parseColor("#2E7D32"));
            bottomNav.setBackgroundColor(Color.parseColor("#1A237E"));
        }
    }

    private void setupBottomNav() {
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_hewan) {
                loadFragment(new HewanFragment());
                return true;
            } else if (id == R.id.nav_tumbuhan) {
                loadFragment(new TumbuhanFragment());
                return true;
            } else if (id == R.id.nav_profil) {
                startActivity(new Intent(this, ProfilActivity.class));
                bottomNav.post(() -> {
                    if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof TumbuhanFragment) {
                        bottomNav.setSelectedItemId(R.id.nav_tumbuhan);
                    } else {
                        bottomNav.setSelectedItemId(R.id.nav_hewan);
                    }
                });
                return false;
            }

            return false;
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    public void onSearchClick(View v) {
        startActivity(new Intent(this, PencarianActivity.class));
    }

    public void onFavoritClick(View v) {
        startActivity(new Intent(this, FavoritActivity.class));
    }
}