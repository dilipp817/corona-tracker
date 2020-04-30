package com.corona.coronatracker.models;

public class DistrictData {
    private String State;
    private String stateCode;
    private String district;
    private int activeCount;
    private int confirmedCount;
    private int deceasedCount;
    private int recoveredCount;
    private DistrictDataDelta deltaCounts;

    public DistrictData(String state, String stateCode, String district, int activeCount, int confirmedCount,
                        int deceasedCount, int recoveredCount, DistrictDataDelta deltaCounts) {
        State = state;
        this.stateCode = stateCode;
        this.district = district;
        this.activeCount = activeCount;
        this.confirmedCount = confirmedCount;
        this.deceasedCount = deceasedCount;
        this.recoveredCount = recoveredCount;
        this.deltaCounts = deltaCounts;
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

    public DistrictDataDelta getDeltaCounts() {
        return deltaCounts;
    }
}
