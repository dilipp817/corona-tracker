package com.corona.coronatracker.database;

import androidx.lifecycle.LiveData;
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

    @Query("select * from district_corona_case where state = :stateName")
    List<DistrictData> getAllStateData(String stateName);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllDistrictData(List<DistrictData> districtData);

    @Query("Delete from district_corona_case")
    void deleteAllData();

    @Query("select distinct state from district_corona_case order by state")
    List<String> getStateList();

    @Query("select SUM(active_count) from district_corona_case where state = :stateName")
    Integer getActiveCountFromState(String stateName);
}
