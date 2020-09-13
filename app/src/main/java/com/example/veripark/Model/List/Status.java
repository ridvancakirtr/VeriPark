package com.example.veripark.Model.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Status {
    @Expose
    @SerializedName("error")
    public java.lang.Error error;
    @Expose
    @SerializedName("isSuccess")
    public boolean isSuccess;
}
