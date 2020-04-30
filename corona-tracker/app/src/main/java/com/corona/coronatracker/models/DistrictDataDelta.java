package com.corona.coronatracker.models;

public class DistrictDataDelta {
    private String State;
    private String stateCode;
    private String district;
    private int confirmedCountDelta;
    private int deceasedCountDelta;
    private int recoveredCountDelta;

    public DistrictDataDelta(String state, String stateCode, String district, int confirmedCountDelta,
                             int deceasedCountDelta, int recoveredCountDelta) {
        this.State = state;
        this.stateCode = stateCode;
        this.district = district;
        this.confirmedCountDelta = confirmedCountDelta;
        this.deceasedCountDelta = deceasedCountDelta;
        this.recoveredCountDelta = recoveredCountDelta;
    }

    public DistrictDataDelta(int confirmedCountDelta, int deceasedCountDelta, int recoveredCountDelta) {
        this.confirmedCountDelta = confirmedCountDelta;
        this.deceasedCountDelta = deceasedCountDelta;
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
