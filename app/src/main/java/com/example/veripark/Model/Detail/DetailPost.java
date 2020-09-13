package com.example.veripark.Model.Detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailPost {
    @Expose
    @SerializedName("id")
    private String id;

    public DetailPost(String id) {
        this.id = id;
    }
}
