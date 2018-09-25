package com.wkdgusdn3.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.wkdgusdn3.model.SharedPreferenceText;
import com.wkdgusdn3.model.SoundFunctionType;

public class InfoManager {
    public static boolean isApplicationEnable = true;
    public static boolean isSeeStatusBarIcon = true;
    public static int theme;
    public static SoundFunctionType[] buttons = new SoundFunctionType[4];

    public static void setData(Context context) {
        SharedPreferences sharedPreferences;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        isApplicationEnable = sharedPreferences.getBoolean(SharedPreferenceText.IS_APPLICATION_ENABLE, true);
        isSeeStatusBarIcon = sharedPreferences.getBoolean(SharedPreferenceText.IS_SEE_STATUS_BAR_ICON, true);
        theme = sharedPreferences.getInt(SharedPreferenceText.THEME, 0);

        buttons[0] = SoundFunctionType.valueOf(sharedPreferences.getString(SharedPreferenceText.BUTTON_01, SoundFunctionType.MUSIC_PLAY.toString()));
        buttons[1] = SoundFunctionType.valueOf(sharedPreferences.getString(SharedPreferenceText.BUTTON_02, SoundFunctionType.VOLUME_UP.toString()));
        buttons[2] = SoundFunctionType.valueOf(sharedPreferences.getString(SharedPreferenceText.BUTTON_03, SoundFunctionType.VOLUME_DOWN.toString()));
        buttons[3] = SoundFunctionType.valueOf(sharedPreferences.getString(SharedPreferenceText.BUTTON_04, SoundFunctionType.VOLUME_0.toString()));
    }
}
