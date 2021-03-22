package com.example.hospitalmanagment;

import android.content.Context;
import android.content.SharedPreferences;

public class StorageMangment {
    private static StorageMangment mInstance;
    private Context context;
    private static String SHARED_PRE_NAME = "my_shared_pre";

    public StorageMangment(Context context) {
        this.context = context;

    }

    public static synchronized StorageMangment getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new StorageMangment(context);
        }
        return mInstance;
    }
    public void getLoginInformation(String gettoken) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PRE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("KEY_GET_TOKEN", gettoken);
        editor.apply();
    }
    public String setLoginInformation() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PRE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("KEY_GET_TOKEN", "");
    }
}
