package com.example.cartoon.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.cartoon.App;
import com.example.cartoon.model.Bean.NetCartoon;
import com.example.cartoon.model.Bean.NetCartoonCache;
import com.example.cartoon.model.Bean.NetCartoonCacheDao;
import com.example.cartoon.model.Bean.NetCartoonDao;

@Database(entities = {NetCartoon.class, NetCartoonCache.class},version = 1,exportSchema = false)
public abstract class AppDateBase extends RoomDatabase {
    private static final String DATABASE_NAME = "cartoon_db";

    public abstract NetCartoonDao cartoonDao();

    public abstract NetCartoonCacheDao cacheDao();

    private static AppDateBase INSTANCE;

    public static AppDateBase getInstance(){
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(App.getContext(),AppDateBase.class,DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
