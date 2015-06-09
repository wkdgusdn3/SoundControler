package com.wkdgusdn3.soundcontroler;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class InfoManager {
    public static boolean boolean_musicPlay;
    public static boolean boolean_soundUp;
    public static boolean boolean_soundDown;
    public static boolean boolean_soundMute;
    public static boolean boolean_icon;

    public static void setData(Context context) {
        SharedPreferences sharedPreferences;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        InfoManager.boolean_musicPlay = sharedPreferences.getBoolean("MUSICPLAY", true);
        InfoManager.boolean_soundUp = sharedPreferences.getBoolean("SOUNDUP", true);
        InfoManager.boolean_soundDown = sharedPreferences.getBoolean("SOUNDDOWN", true);
        InfoManager.boolean_soundMute = sharedPreferences.getBoolean("SOUNDMUTE", true);
        InfoManager.boolean_icon = sharedPreferences.getBoolean("ICON", true);
    }
}
