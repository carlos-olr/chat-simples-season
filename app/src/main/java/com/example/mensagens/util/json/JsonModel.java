package com.example.mensagens.util.json;

import com.google.gson.Gson;

import java.io.Serializable;

public class JsonModel implements Serializable {

    public static String toJson(Object o) {
        return new Gson().toJson(o);
    }

    public String toJson() {
        return toJson(this);
    }

    public static <T> T fromJson(String json, Class<T> classType) {
        try {
            return new Gson().fromJson(json, classType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
