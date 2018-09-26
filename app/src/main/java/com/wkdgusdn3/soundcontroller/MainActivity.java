package com.wkdgusdn3.soundcontroller;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.wkdgusdn3.manager.InfoManager;
import com.wkdgusdn3.manager.SharedPreferenceManager;
import com.wkdgusdn3.model.SoundFunctionType;
import com.wkdgusdn3.model.ThemeType;
import com.wkdgusdn3.observer.VolumeChangeObserver;
import com.wkdgusdn3.service.SoundService;

public class MainActivity extends Activity {

    private CheckBox applicationEnableCheckBox;
    private CheckBox statusBarIconEnableCheckBox;
    private CheckBox currentVolumeIconEnableCheckBox;
    private Spinner themeSpinner;
    private Spinner[] functionSpinners = new Spinner[4];
    private Button applyButton;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InfoManager.setData(getApplicationContext());

        setVariable();
        setView();
        initializeView();
        setClickListener();

        startService();
    }

    void setVariable() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = sharedPreferences.edit();
    }

    void setView() {
        applicationEnableCheckBox = (CheckBox) findViewById(R.id.main_applicationEnableCheckBox);
        statusBarIconEnableCheckBox = (CheckBox) findViewById(R.id.main_statusBarIconEnableCheckBox);
        currentVolumeIconEnableCheckBox = (CheckBox) findViewById(R.id.main_currentVolumeCheckBox);
        themeSpinner = (Spinner) findViewById(R.id.main_theme);
        functionSpinners[0] = (Spinner) findViewById(R.id.main_button1);
        functionSpinners[1] = (Spinner) findViewById(R.id.main_button2);
        functionSpinners[2] = (Spinner) findViewById(R.id.main_button3);
        functionSpinners[3] = (Spinner) findViewById(R.id.main_button4);
        applyButton = (Button) findViewById(R.id.main_apply);
    }

    void initializeView() { // 옵션을 이전에 저장된 상태로 초기화
        if (InfoManager.isEnableApplication) {
            applicationEnableCheckBox.setChecked(true);
        }
        if(InfoManager.isEnableCurrentVolumeIcon) {
            currentVolumeIconEnableCheckBox.setChecked(true);
        }
        if (InfoManager.isEnableStatusBarIcon) {
            statusBarIconEnableCheckBox.setChecked(true);
        }

        themeSpinner.setSelection(InfoManager.theme.getPosition());

        for (int i = 0; i < 4; i++) {
            functionSpinners[i].setSelection(InfoManager.buttons[i].getPosition());
        }
    }

    void setClickListener() {

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.putBoolean(SharedPreferenceManager.IS_ENABLE_APPLICATION, applicationEnableCheckBox.isChecked());
                editor.putBoolean(SharedPreferenceManager.IS_ENABLE_STATUS_BAR_ICON, statusBarIconEnableCheckBox.isChecked());
                editor.putBoolean(SharedPreferenceManager.IS_ENABLE_CURRENT_VOLUME_ICON, currentVolumeIconEnableCheckBox.isChecked());
                editor.putString(SharedPreferenceManager.THEME, ThemeType.getThemeType(themeSpinner.getSelectedItemPosition()).toString());

                editor.putString(SharedPreferenceManager.BUTTON_01, SoundFunctionType.getSoundFunction(functionSpinners[0].getSelectedItemPosition()).toString());
                editor.putString(SharedPreferenceManager.BUTTON_02, SoundFunctionType.getSoundFunction(functionSpinners[1].getSelectedItemPosition()).toString());
                editor.putString(SharedPreferenceManager.BUTTON_03, SoundFunctionType.getSoundFunction(functionSpinners[2].getSelectedItemPosition()).toString());
                editor.putString(SharedPreferenceManager.BUTTON_04, SoundFunctionType.getSoundFunction(functionSpinners[3].getSelectedItemPosition()).toString());

                editor.commit();
                InfoManager.setData(getApplicationContext());

                if(InfoManager.isEnableCurrentVolumeIcon) {

                    // volumeChangeObserver 등록
                    VolumeChangeObserver volumeChangeObserver = new VolumeChangeObserver(getApplicationContext(), new Handler());
                    getApplicationContext()
                            .getContentResolver()
                            .registerContentObserver(android.provider.Settings.System.CONTENT_URI, true, volumeChangeObserver );
                } else {

                    // volumeChangeObserver 제거
                    VolumeChangeObserver volumeChangeObserver = new VolumeChangeObserver(getApplicationContext(), new Handler());
                    getApplicationContext()
                            .getContentResolver()
                            .unregisterContentObserver(volumeChangeObserver);
                }

                startService();
            }
        });
    }

    void startService() {
        Intent soundServiceIntent = new Intent(getApplicationContext(), SoundService.class);

        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.cancel(3);
        stopService(soundServiceIntent);

        InfoManager.setData(getApplicationContext());

        if (InfoManager.isEnableApplication) {
            startService(soundServiceIntent);
        }
    }
}
