package com.example.veripark.Model.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListPost {
    @Expose
    @SerializedName("period")
    private String period;

    public ListPost(String period) {
        this.period = period;
    }
}
