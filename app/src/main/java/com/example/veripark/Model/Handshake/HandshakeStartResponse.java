package com.example.veripark.Model.Handshake;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HandshakeStartResponse {

    @Expose
    @SerializedName("status")
    public Status status;
    @Expose
    @SerializedName("lifeTime")
    public String lifeTime;
    @Expose
    @SerializedName("authorization")
    public String authorization;
    @Expose
    @SerializedName("aesIV")
    public String aesIV;
    @Expose
    @SerializedName("aesKey")
    public String aesKey;

    public static class Status {
        @Expose
        @SerializedName("error")
        public java.lang.Error error;
        @Expose
        @SerializedName("isSuccess")
        public boolean isSuccess;
    }

    public static class Error {
        @Expose
        @SerializedName("message")
        public String message;
        @Expose
        @SerializedName("code")
        public int code;
    }
}
