package com.docotel.muhadif.second.util;

public class JNIHelper {

    static {
        System.loadLibrary("native-lib");
    }

    public static final native String getApiKey();

    public static String getApi(){
        return getApiKey();
    }

}
