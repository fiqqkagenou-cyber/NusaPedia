package com.example.nusapedia.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "endemik")
public class EndemikEntity {

    @PrimaryKey
    @NonNull
    private String id;

    private String tipe;
    private String nama;
    private String namaLatin;
    private String famili;
    private String genus;
    private String deskripsi;
    private String asal;
    private String sebaran;
    private String foto;
    private String status;

    @NonNull
    public String getId() { return id; }
    public void setId(@NonNull String id) { this.id = id; }
    public String getTipe() { return tipe; }
    public void setTipe(String tipe) { this.tipe = tipe; }
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    public String getNamaLatin() { return namaLatin; }
    public void setNamaLatin(String namaLatin) { this.namaLatin = namaLatin; }
    public String getFamili() { return famili; }
    public void setFamili(String famili) { this.famili = famili; }
    public String getGenus() { return genus; }
    public void setGenus(String genus) { this.genus = genus; }
    public String getDeskripsi() { return deskripsi; }
    public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi; }
    public String getAsal() { return asal; }
    public void setAsal(String asal) { this.asal = asal; }
    public String getSebaran() { return sebaran; }
    public void setSebaran(String sebaran) { this.sebaran = sebaran; }
    public String getFoto() { return foto; }
    public void setFoto(String foto) { this.foto = foto; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}