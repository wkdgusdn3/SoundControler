package com.wkdgusdn3.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class InfoManager {
    public static boolean boolean_operation = true;
    public static boolean boolean_icon = true;
    public static int[] functions = new int[4];

    public static void setData(Context context) {
        SharedPreferences sharedPreferences;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        InfoManager.boolean_operation = sharedPreferences.getBoolean("OPERATION", true);
        InfoManager.boolean_icon = sharedPreferences.getBoolean("ICON", true);

        for (int i = 0; i < 4; i++) {
            InfoManager.functions[i] = sharedPreferences.getInt("FUNCTION" + i, i+1);
        }
    }
}
