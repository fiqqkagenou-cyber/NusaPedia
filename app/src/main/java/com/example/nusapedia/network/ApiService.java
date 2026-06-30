package com.example.nusapedia.network;

import com.example.nusapedia.model.Endemik;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    // Sesuaikan endpoint dengan API kamu
    @GET("endemik")
    Call<List<Endemik>> getAllEndemik();
}