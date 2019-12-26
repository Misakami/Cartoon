package com.example.cartoon.model.Bean;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface CartoonMarkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CartoonMark mark);

    @Update
    void upDate(CartoonMark mark);

    @Delete
    void delect(CartoonMark mark);

    @Query("select * from cartoonMark where cartoonName =:name and siteType = :Type")
    CartoonMark select(String name,int Type);
}
