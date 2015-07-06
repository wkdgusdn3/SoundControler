package com.wkdgusdn3.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class InfoManager {
    public static boolean boolean_operation = true;
    public static boolean boolean_musicPlay = true;
    public static boolean boolean_soundUp = true;
    public static boolean boolean_soundDown = true;
    public static boolean boolean_soundMute = true;
    public static boolean boolean_icon = true;

    public static void setData(Context context) {
        SharedPreferences sharedPreferences;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        InfoManager.boolean_operation = sharedPreferences.getBoolean("OPERATION", true);
        InfoManager.boolean_musicPlay = sharedPreferences.getBoolean("MUSICPLAY", true);
        InfoManager.boolean_soundUp = sharedPreferences.getBoolean("SOUNDUP", true);
        InfoManager.boolean_soundDown = sharedPreferences.getBoolean("SOUNDDOWN", true);
        InfoManager.boolean_soundMute = sharedPreferences.getBoolean("SOUNDMUTE", true);
        InfoManager.boolean_icon = sharedPreferences.getBoolean("ICON", true);
    }
}
