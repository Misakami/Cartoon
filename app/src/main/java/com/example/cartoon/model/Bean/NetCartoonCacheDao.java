package com.example.cartoon.model.Bean;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface NetCartoonCacheDao {
    @Query("select * from netCache where cartoonName =:cartoonName and siteType=:siteType")
    NetCartoonCache getcatLog(String cartoonName,int siteType);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NetCartoonCache cache);

    @Update
    void Update(NetCartoonCache cache);
}
