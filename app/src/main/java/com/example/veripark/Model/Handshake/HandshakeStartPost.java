package com.example.veripark.Model.Handshake;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HandshakeStartPost {

    @Expose
    @SerializedName("manifacturer")
    private String manifacturer;
    @Expose
    @SerializedName("deviceModel")
    private String deviceModel;
    @Expose
    @SerializedName("platformName")
    private String platformName;
    @Expose
    @SerializedName("systemVersion")
    private String systemVersion;
    @Expose
    @SerializedName("deviceId")
    private String deviceId;

    public HandshakeStartPost(String manifacturer, String deviceModel, String platformName, String systemVersion, String deviceId) {
        this.manifacturer = manifacturer;
        this.deviceModel = deviceModel;
        this.platformName = platformName;
        this.systemVersion = systemVersion;
        this.deviceId = deviceId;
    }
}
