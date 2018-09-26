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
import com.wkdgusdn3.observer.VolumeChangeObserver;
import com.wkdgusdn3.service.SoundService;

public class MainActivity extends Activity {

    private CheckBox checkBox_operation;
    private CheckBox checkBox_icon;
    private Spinner spinner_color;
    private Spinner[] spinners_function = new Spinner[4];
    private Button button_apply;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.wkdgusdn3.soundcontroller.R.layout.activity_main);

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
        checkBox_operation = (CheckBox) findViewById(R.id.main_operationCheckBox);
        checkBox_icon = (CheckBox) findViewById(R.id.main_iconCheckBox);
        spinner_color = (Spinner) findViewById(R.id.main_color);
        spinners_function[0] = (Spinner) findViewById(R.id.main_function1);
        spinners_function[1] = (Spinner) findViewById(R.id.main_function2);
        spinners_function[2] = (Spinner) findViewById(R.id.main_function3);
        spinners_function[3] = (Spinner) findViewById(R.id.main_function4);
        button_apply = (Button) findViewById(R.id.main_apply);
    }

    void initializeView() { // 옵션을 이전에 저장된 상태로 초기화
        if (InfoManager.isApplicationEnable) {
            checkBox_operation.setChecked(true);
        }
        if (InfoManager.isSeeStatusBarIcon) {
            checkBox_icon.setChecked(true);
        }

        spinner_color.setSelection(InfoManager.theme);

        for (int i = 0; i < 4; i++) {
            spinners_function[i].setSelection(InfoManager.buttons[i].getPosition());
        }
    }

    void setClickListener() {

        button_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.putBoolean(SharedPreferenceManager.IS_APPLICATION_ENABLE, checkBox_operation.isChecked());
                editor.putBoolean(SharedPreferenceManager.IS_SEE_STATUS_BAR_ICON, checkBox_icon.isChecked());
                editor.putInt(SharedPreferenceManager.THEME, spinner_color.getSelectedItemPosition());

                editor.putString(SharedPreferenceManager.BUTTON_01, SoundFunctionType.getSoundFunction(spinners_function[0].getSelectedItemPosition()).toString());
                editor.putString(SharedPreferenceManager.BUTTON_02, SoundFunctionType.getSoundFunction(spinners_function[1].getSelectedItemPosition()).toString());
                editor.putString(SharedPreferenceManager.BUTTON_03, SoundFunctionType.getSoundFunction(spinners_function[2].getSelectedItemPosition()).toString());
                editor.putString(SharedPreferenceManager.BUTTON_04, SoundFunctionType.getSoundFunction(spinners_function[3].getSelectedItemPosition()).toString());

                editor.commit();

                InfoManager.setData(getApplicationContext());

                startService();

                VolumeChangeObserver volumeChangeObserver = new VolumeChangeObserver(getApplicationContext(), new Handler());
                getApplicationContext()
                    .getContentResolver()
                    .registerContentObserver(android.provider.Settings.System.CONTENT_URI, true, volumeChangeObserver );
            }
        });
    }

    void startService() {
        Intent soundServiceIntent = new Intent(getApplicationContext(), SoundService.class);

        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.cancel(3);
        stopService(soundServiceIntent);

        InfoManager.setData(getApplicationContext());

        if (InfoManager.isApplicationEnable) {
            startService(soundServiceIntent);
        }
    }
}
