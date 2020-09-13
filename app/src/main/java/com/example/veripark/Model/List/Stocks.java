package com.example.veripark.Model.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stocks {
    @Expose
    @SerializedName("symbol")
    private String symbol;
    @Expose
    @SerializedName("volume")
    private String volume;
    @Expose
    @SerializedName("price")
    private String price;
    @Expose
    @SerializedName("offer")
    private String offer;
    @Expose
    @SerializedName("difference")
    private String difference;
    @Expose
    @SerializedName("bid")
    private String bid;
    @Expose
    @SerializedName("isUp")
    private String isUp;
    @Expose
    @SerializedName("isDown")
    private String isDown;
    @Expose
    @SerializedName("id")
    private String id;

    public String getSymbol() {
        return symbol;
    }

    public String getVolume() {
        return volume;
    }

    public String getPrice() {
        return price;
    }

    public String getOffer() {
        return offer;
    }

    public String getDifference() {
        return difference;
    }

    public String getBid() {
        return bid;
    }

    public String isUp() {
        return isUp;
    }

    public String isDown() {
        return isDown;
    }

    public String getId() {
        return id;
    }

    public Stocks(String symbol, String volume, String price, String offer, String difference, String bid, String isUp, String isDown, String id) {
        this.symbol = symbol;
        this.volume = volume;
        this.price = price;
        this.offer = offer;
        this.difference = difference;
        this.bid = bid;
        this.isUp = isUp;
        this.isDown = isDown;
        this.id = id;
    }
}
