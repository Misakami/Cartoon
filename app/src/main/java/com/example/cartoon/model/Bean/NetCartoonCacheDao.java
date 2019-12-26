package com.example.cartoon.model.Bean;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface NetCartoonCacheDao {
    @Query("select * from netCache where ncCacheId =:ncCacheId")
    NetCartoonCache getcatLog(int ncCacheId);

    @Update
    void Update(NetCartoonCache cache);
}
