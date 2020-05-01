package com.corona.coronatracker.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.corona.coronatracker.models.DistrictData;

@Database(entities = DistrictData.class, exportSchema = false, version = 1)
public abstract class CoronaDatabase extends RoomDatabase {
    private static final String DB_NAME = "corona_database";
    private static CoronaDatabase coronaDatabase;

    public static synchronized CoronaDatabase getInstanse(Context context) {
        if (coronaDatabase == null) {
            coronaDatabase = Room.databaseBuilder(context.getApplicationContext(), CoronaDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return coronaDatabase;
    }

    public abstract DistrictDataDao districtDataDao();
}
