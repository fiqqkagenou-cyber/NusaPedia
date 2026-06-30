package com.example.nusapedia.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EndemikDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<EndemikEntity> list);

    @Query("SELECT * FROM endemik WHERE tipe = :tipe")
    List<EndemikEntity> getByTipe(String tipe);

    @Query("SELECT * FROM endemik WHERE nama LIKE '%' || :keyword || '%' OR namaLatin LIKE '%' || :keyword || '%'")
    List<EndemikEntity> search(String keyword);

    @Query("SELECT COUNT(*) FROM endemik")
    int count();
}