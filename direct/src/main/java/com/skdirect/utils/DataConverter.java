package com.skdirect.utils;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class DataConverter {

    @TypeConverter
    public String fromMoqList(ArrayList<String> list) {
        if (list == null) {
            return null;
        }
        return new Gson().toJson(list, new TypeToken<ArrayList<String>>() {
        }.getType());
    }

    @TypeConverter
    public ArrayList<String> toMoqList(String list) {
        if (list == null) {
            return null;
        }
        return new Gson().fromJson(list, new TypeToken<ArrayList<String>>() {
        }.getType());
    }
}