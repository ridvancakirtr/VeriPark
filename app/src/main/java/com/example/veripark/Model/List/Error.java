package com.example.veripark.Model.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Error {
    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("code")
    private int code;
}
