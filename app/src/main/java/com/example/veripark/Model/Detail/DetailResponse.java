package com.example.veripark.Model.Detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailResponse {
    @Expose
    @SerializedName("status")
    public Status status;
    @Expose
    @SerializedName("graphicData")
    public List<GraphicData> graphicData;
    @Expose
    @SerializedName("symbol")
    public String symbol;
    @Expose
    @SerializedName("volume")
    public String volume;
    @Expose
    @SerializedName("price")
    public String price;
    @Expose
    @SerializedName("minimum")
    public String minimum;
    @Expose
    @SerializedName("maximum")
    public String maximum;
    @Expose
    @SerializedName("lowest")
    public String lowest;
    @Expose
    @SerializedName("highest")
    public String highest;
    @Expose
    @SerializedName("offer")
    public String offer;
    @Expose
    @SerializedName("difference")
    public String difference;
    @Expose
    @SerializedName("count")
    public String count;
    @Expose
    @SerializedName("channge")
    public String channge;
    @Expose
    @SerializedName("bid")
    public String bid;
    @Expose
    @SerializedName("isUp")
    public String isUp;
    @Expose
    @SerializedName("isDown")
    public String isDown;

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
        public String code;
    }

    public static class GraphicData {
        @Expose
        @SerializedName("value")
        public Number value;
        @Expose
        @SerializedName("day")
        public String day;

        public GraphicData(Number value, String day) {
            this.value = value;
            this.day = day;
        }
    }
}
