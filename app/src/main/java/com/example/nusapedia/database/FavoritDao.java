package com.example.nusapedia.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavoritDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FavoritEntity favorit);

    @Query("DELETE FROM favorit WHERE id = :id")
    void deleteById(String id);

    @Query("SELECT * FROM favorit ORDER BY nama ASC")
    List<FavoritEntity> getAll();

    @Query("SELECT COUNT(*) FROM favorit WHERE id = :id")
    int isFavorit(String id);
}