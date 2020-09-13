package com.example.veripark.Require;

import android.os.Build;
import java.util.UUID;

public class Device {
    public static String getDeviceID(){
        return UUID.randomUUID().toString();
    }

    public static String getSystemVersion(){
        return String.valueOf(Build.VERSION.SDK_INT);
    }

    public static String getPlatformName(){
        return "Android";
    }

    public static String getDeviceModel(){
        return String.valueOf(Build.MODEL);
    }

    public static String getDeviceManifacturer(){
        return String.valueOf(Build.BRAND);
    }
}
