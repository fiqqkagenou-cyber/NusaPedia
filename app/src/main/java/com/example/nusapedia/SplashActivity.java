package com.example.nusapedia;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nusapedia.database.EndemikDatabase;
import com.example.nusapedia.database.EndemikEntity;
import com.example.nusapedia.model.Endemik;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SplashActivity extends AppCompatActivity {

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(() -> {
            cekDatabaseDanMasuk();
        }, 1800);
    }

    private void cekDatabaseDanMasuk() {
        executor.execute(() -> {
            EndemikDatabase db = EndemikDatabase.getInstance(this);
            int jumlahData = db.endemikDao().count();

            if (jumlahData > 0) {
                runOnUiThread(this::goToHome);
            } else {
                ambilDataDariTxt();
            }
        });
    }

    private void ambilDataDariTxt() {
        try {
            List<Endemik> semuaData = new ArrayList<>();

            semuaData.addAll(bacaFileTxt("isi.txt"));

            saveToRoom(semuaData);

        } catch (Exception e) {
            runOnUiThread(() ->
                    Toast.makeText(this, "Gagal membaca database txt", Toast.LENGTH_LONG).show()
            );
        }
    }

    private List<Endemik> bacaFileTxt(String namaFile) throws Exception {
        InputStream inputStream = getAssets().open(namaFile);

        int size = inputStream.available();
        byte[] buffer = new byte[size];

        inputStream.read(buffer);
        inputStream.close();

        String json = new String(buffer, StandardCharsets.UTF_8);

        Type listType = new TypeToken<List<Endemik>>() {}.getType();

        return new Gson().fromJson(json, listType);
    }

    private void saveToRoom(List<Endemik> list) {
        List<EndemikEntity> entities = new ArrayList<>();

        for (Endemik e : list) {
            EndemikEntity entity = new EndemikEntity();

            entity.setId(e.getId());
            entity.setTipe(e.getTipe());
            entity.setNama(e.getNama());
            entity.setNamaLatin(e.getNamaLatin());
            entity.setFamili(e.getFamili());
            entity.setGenus(e.getGenus());
            entity.setDeskripsi(e.getDeskripsi());
            entity.setAsal(e.getAsal());
            entity.setSebaran(e.getSebaran());
            entity.setFoto(e.getFoto());
            entity.setStatus(e.getStatus());

            entities.add(entity);
        }

        EndemikDatabase.getInstance(this)
                .endemikDao()
                .insertAll(entities);

        runOnUiThread(this::goToHome);
    }

    private void goToHome() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}