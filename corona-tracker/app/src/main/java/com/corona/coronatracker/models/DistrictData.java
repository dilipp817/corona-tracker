package com.corona.coronatracker.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.corona.coronatracker.database.Converters;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@Entity (tableName = "district_corona_case")
public class DistrictData implements Serializable {

    @ColumnInfo(name = "state")
    private String State;
    @ColumnInfo(name = "state_code")
    private String stateCode;
    @NotNull
    @PrimaryKey
    @ColumnInfo(name = "district")
    private String district;
    @ColumnInfo(name = "active_count")
    private int activeCount;
    @ColumnInfo(name = "confirmed_count")
    private int confirmedCount;
    @ColumnInfo(name = "deceased_count")
    private int deceasedCount;
    @ColumnInfo(name = "recovered_count")
    private int recoveredCount;
    @ColumnInfo(name = "delta_confirmed")
    private int confirmedCountDelta;
    @ColumnInfo(name = "delta_deceased")
    private int deceasedCountDelta;
    @ColumnInfo(name = "delta_recovered")
    private int recoveredCountDelta;

    public DistrictData(String state, String stateCode, String district, int activeCount, int confirmedCount,
                        int deceasedCount, int recoveredCount, int deltaConfirmed, int deltaDecased, int deltaRecovered) {
        State = state;
        this.stateCode = stateCode;
        this.district = district;
        this.activeCount = activeCount;
        this.confirmedCount = confirmedCount;
        this.deceasedCount = deceasedCount;
        this.recoveredCount = recoveredCount;
        this.confirmedCountDelta = deltaConfirmed;
        this.deceasedCountDelta = deltaDecased;
        this.recoveredCountDelta = deltaRecovered;
    }

    public DistrictData() {}

    public void setState(String state) {
        State = state;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setActiveCount(int activeCount) {
        this.activeCount = activeCount;
    }

    public void setConfirmedCount(int confirmedCount) {
        this.confirmedCount = confirmedCount;
    }

    public void setDeceasedCount(int deceasedCount) {
        this.deceasedCount = deceasedCount;
    }

    public void setRecoveredCount(int recoveredCount) {
        this.recoveredCount = recoveredCount;
    }

    public void setConfirmedCountDelta(int confirmedCountDelta) {
        this.confirmedCountDelta = confirmedCountDelta;
    }

    public void setDeceasedCountDelta(int deceasedCountDelta) {
        this.deceasedCountDelta = deceasedCountDelta;
    }

    public void setRecoveredCountDelta(int recoveredCountDelta) {
        this.recoveredCountDelta = recoveredCountDelta;
    }

    public String getState() {
        return State;
    }

    public String getStateCode() {
        return stateCode;
    }

    public String getDistrict() {
        return district;
    }

    public int getActiveCount() {
        return activeCount;
    }

    public int getConfirmedCount() {
        return confirmedCount;
    }

    public int getDeceasedCount() {
        return deceasedCount;
    }

    public int getRecoveredCount() {
        return recoveredCount;
    }

    public int getConfirmedCountDelta() {
        return confirmedCountDelta;
    }

    public int getDeceasedCountDelta() {
        return deceasedCountDelta;
    }

    public int getRecoveredCountDelta() {
        return recoveredCountDelta;
    }
}
