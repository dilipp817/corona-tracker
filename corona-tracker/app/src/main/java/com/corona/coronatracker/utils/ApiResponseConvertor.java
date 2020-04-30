package com.corona.coronatracker.utils;

import com.corona.coronatracker.models.DistrictData;
import com.corona.coronatracker.models.DistrictDataDelta;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ApiResponseConvertor {

    public static List<DistrictData> extractCoronaData(JSONArray districtDataArray) throws JSONException {
        List<DistrictData> districtDataList = new ArrayList<>();

        int stateArrayLingth = districtDataArray.length();
        for (int index = 0; index < stateArrayLingth; index++) {
            JSONObject stateArray = districtDataArray.getJSONObject(index);

            JSONArray districtArray = stateArray.getJSONArray("districtData");
            int districtArrayLength = districtArray.length();
            for (int districtIndex = 0; districtIndex < districtArrayLength; districtIndex++) {
                JSONObject districtCase = districtArray.getJSONObject(districtIndex);

                districtDataList.add(getDistrictData(stateArray.getString("state"),
                        stateArray.getString("statecode"), districtCase));
            }
        }
        return districtDataList;
    }

    private static DistrictData getDistrictData(String state, String stateCode, JSONObject districtData) throws JSONException {

        return new DistrictData(state, stateCode, districtData.getString("district"), districtData.getInt("active"),
                districtData.getInt("confirmed"), districtData.getInt("deceased"), districtData.getInt("recovered"),
                getDistrictDataDelta(districtData.getJSONObject("delta")));
    }

    private static DistrictDataDelta getDistrictDataDelta(JSONObject districtDelta) throws JSONException {
        return new DistrictDataDelta(districtDelta.getInt("confirmed"), districtDelta.getInt("deceased"),
                districtDelta.getInt("recovered"));
    }
}
