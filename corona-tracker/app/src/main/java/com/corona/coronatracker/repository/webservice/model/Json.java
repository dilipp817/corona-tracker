package com.corona.coronatracker.repository.webservice.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Json extends JSONObject {

    JSONObject json;

    public Json(String string) {
        try {
            json = new JSONObject(string);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Json(JSONObject jsonObject) {
        json = jsonObject;
    }

    public Json() {
        json = new JSONObject();
    }


    public String string(String name, String defaultString) {
        String value = defaultString;
        try {
            value = ((null != json) && json.has(name) && !json.isNull(name)) ? json.getString(name) : defaultString;
        } catch (JSONException ignored) {

        }
        return value;
    }

    public Json json(String name) {
        Json value = new Json();
        try {
            value = ((null != json) && json.has(name) && !json.isNull(name)) ? new Json(json.getJSONObject(name)) : value;
        } catch (JSONException ignored) {
        }
        return value;
    }
}
