package com.example.examenfinal.models;

import com.google.gson.annotations.SerializedName;

public class Sprite {
    @SerializedName("default")
    private String url;

    public String getUrl() {
        return url;
    }
}
