package com.example.veripark.Require;

public class Const {
    private static String authorization;
    private static String aesIV;
    private static String aesKey;

    public static String getAuthorization() {
        return authorization;
    }

    public static void setAuthorization(String authorization) {
        Const.authorization = authorization;
    }

    public static String getAesIV() {
        return aesIV;
    }

    public static void setAesIV(String aesIV) {
        Const.aesIV = aesIV;
    }

    public static String getAesKey() {
        return aesKey;
    }

    public static void setAesKey(String aesKey) {
        Const.aesKey = aesKey;
    }
}
