package com.example.cartoon.model.Bean;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NetCartoonDao {
    @Query("select * from netCartoon order by time desc")
    List<NetCartoon> getNetCartoons();

    @Query("select * from netCartoon where siteType = :siteType and cartoonName = :cartoonName")
    NetCartoon getNetCartoon(int siteType,String cartoonName);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NetCartoon cartoon);

    @Query("delete from netCartoon where siteType = :siteType and cartoonName = :cartoonName")
    void delect(int siteType,String cartoonName);

    @Update
    void upDate(NetCartoon cartoon);
}
