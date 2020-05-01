package com.corona.coronatracker.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.corona.coronatracker.models.DistrictData;

import java.util.List;

@Dao
public interface DistrictDataDao {
    @Query("Select * from district_corona_case")
    List<DistrictData> getAllDistrictData();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllDistrictData(List<DistrictData> districtData);

    @Query("Delete from district_corona_case")
    void deleteAllData();
}
