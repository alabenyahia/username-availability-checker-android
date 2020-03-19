package com.pickurapps.usernameavailabilitychecker;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonData {
    String avatar;
    Boolean possible;
    String profile;
    Integer status;
    String url;
    Boolean usable;

    public static JsonData fromJson(JSONObject jsonObject) {

        JsonData jsonData = new JsonData();
        try {
            jsonData.avatar = jsonObject.getString("avatar");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            jsonData.possible = jsonObject.getBoolean("possible");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            jsonData.profile = jsonObject.getString("profile");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            jsonData.status = jsonObject.getInt("status");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            jsonData.url = jsonObject.getString("url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            jsonData.usable = jsonObject.getBoolean("usable");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  jsonData;
    }

    @NonNull
    @Override
    public String toString() {
        String str = "";

        if (avatar != null) str += " Avatar = " + avatar + "\n";
        else str += "Avatar = null" + "\n";
        if (possible != null) str += " Possible = " + possible + "\n";
        else str += "Possible = null" + "\n";
        if (profile != null) str += "Profile = " + profile + "\n";
        else str += "Profile = null" + "\n";
        if (status != null) str += "Status = " + status + "\n";
        else str += "Status = null" + "\n";
        if (url != null) str += "Url = " + url + "\n";
        else str += "Url = null" + "\n";
        if (usable != null) str += "Usable = " + usable + "\n";
        else str += "Usable = null" + "\n";

        return str;
    }
}
