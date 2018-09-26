package com.wkdgusdn3.manager;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.wkdgusdn3.model.SoundFunctionType;

public class InfoManager {

    public static boolean isEnableApplication = true;
    public static boolean isEnableStatusBarIcon = true;
    public static boolean isEnableCurrentVolumeIcon = true;
    public static int theme;
    public static SoundFunctionType[] buttons = new SoundFunctionType[4];

    public static NotificationManager notificationManager;
    public static Notification notification;

    public static void setData(Context context) {

        SharedPreferences sharedPreferences;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        isEnableApplication = sharedPreferences.getBoolean(SharedPreferenceManager.IS_ENABLE_APPLICATION, true);
        isEnableStatusBarIcon = sharedPreferences.getBoolean(SharedPreferenceManager.IS_ENABLE_STATUS_BAR_ICON, true);
        isEnableCurrentVolumeIcon = sharedPreferences.getBoolean(SharedPreferenceManager.IS_ENABLE_CURRENT_VOLUME_ICON, true);
        theme = sharedPreferences.getInt(SharedPreferenceManager.THEME, 0);

        buttons[0] = SoundFunctionType.valueOf(sharedPreferences.getString(SharedPreferenceManager.BUTTON_01, SoundFunctionType.MUSIC_PLAY.toString()));
        buttons[1] = SoundFunctionType.valueOf(sharedPreferences.getString(SharedPreferenceManager.BUTTON_02, SoundFunctionType.VOLUME_UP.toString()));
        buttons[2] = SoundFunctionType.valueOf(sharedPreferences.getString(SharedPreferenceManager.BUTTON_03, SoundFunctionType.VOLUME_DOWN.toString()));
        buttons[3] = SoundFunctionType.valueOf(sharedPreferences.getString(SharedPreferenceManager.BUTTON_04, SoundFunctionType.VOLUME_0.toString()));
    }
}
