package com.example.veripark.Model.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListResponse {

    @Expose
    @SerializedName("status")
    public Status status;
    @Expose
    @SerializedName("stocks")
    public List<Stocks> stocks;
}
