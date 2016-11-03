package com.wkdgusdn3.soundcontroller;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.wkdgusdn3.manager.InfoManager;
import com.wkdgusdn3.service.SoundService;

public class MainActivity extends Activity {

    CheckBox checkBox_operation;
    CheckBox checkBox_icon;
    Spinner[] spinners_function = new Spinner[4];
    Button button_apply;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

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
        spinners_function[0] = (Spinner) findViewById(R.id.main_function1);
        spinners_function[1] = (Spinner) findViewById(R.id.main_function2);
        spinners_function[2] = (Spinner) findViewById(R.id.main_function3);
        spinners_function[3] = (Spinner) findViewById(R.id.main_function4);
        button_apply = (Button) findViewById(R.id.main_apply);
    }

    void initializeView() { // 옵션을 이전에 저장된 상태로 초기화
        if (InfoManager.boolean_operation) {
            checkBox_operation.setChecked(true);
        }
        if (InfoManager.boolean_icon) {
            checkBox_icon.setChecked(true);
        }

        for (int i = 0; i < 4; i++) {
            spinners_function[i].setSelection(InfoManager.functions[i]);
        }
    }

    void setClickListener() {

        button_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putBoolean("OPERATION", checkBox_operation.isChecked());
                editor.putBoolean("ICON", checkBox_icon.isChecked());

                for (int i = 0; i < 4; i++) {
                    editor.putInt("FUNCTION" + i, spinners_function[i].getSelectedItemPosition());
                }
                editor.commit();

                InfoManager.setData(getApplicationContext());

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

        if (InfoManager.boolean_operation) {
            startService(soundServiceIntent);
        }
    }
}
