package com.wkdgusdn3.soundcontroler;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class MainActivity extends Activity {

    CheckBox checkBox_MusicPlay;
    CheckBox checkBox_SoundUp;
    CheckBox checkBox_SoundDown;
    CheckBox checkBox_SoundMute;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = sharedPreferences.edit();

        setView();
        setCheckBox();
        setClickListener();

        startService();
    }

    void setView() {
        checkBox_MusicPlay = (CheckBox)findViewById(R.id.main_musicPlayCheckBox);
        checkBox_SoundUp = (CheckBox)findViewById(R.id.main_soundUpCheckBox);
        checkBox_SoundDown = (CheckBox)findViewById(R.id.main_soundDownCheckBox);
        checkBox_SoundMute = (CheckBox)findViewById(R.id.main_soundMuteCheckBox);
    }

    void setCheckBox() {
        InfoManager.boolean_musicPlay = sharedPreferences.getBoolean("MUSICPLAY", true);
        InfoManager.boolean_soundUp = sharedPreferences.getBoolean("SOUNDUP", true);
        InfoManager.boolean_soundDown = sharedPreferences.getBoolean("SOUNDDOWN", true);
        InfoManager.boolean_soundMute = sharedPreferences.getBoolean("SOUNDMUTE", true);

        if(InfoManager.boolean_musicPlay) {
            checkBox_MusicPlay.setChecked(true);
        }
        if(InfoManager.boolean_soundUp) {
            checkBox_SoundUp.setChecked(true);
        }
        if(InfoManager.boolean_soundDown) {
            checkBox_SoundDown.setChecked(true);
        }
        if(InfoManager.boolean_soundMute) {
            checkBox_SoundMute.setChecked(true);
        }
    }

    void setClickListener() {
        checkBox_MusicPlay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    InfoManager.boolean_musicPlay = true;
                    editor.putBoolean("MUSICPLAY", true);
                    editor.commit();

                    startService();
                } else {
                    InfoManager.boolean_musicPlay = false;
                    editor.putBoolean("MUSICPLAY", false);
                    editor.commit();

                    startService();
                }
            }
        });
        checkBox_SoundUp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    InfoManager.boolean_soundUp = true;
                    editor.putBoolean("SOUNDUP", true);
                    editor.commit();

                    startService();
                } else {
                    InfoManager.boolean_soundUp = false;
                    editor.putBoolean("SOUNDUP", false);
                    editor.commit();

                    startService();
                }
            }
        });
        checkBox_SoundDown.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    InfoManager.boolean_soundDown = true;
                    editor.putBoolean("SOUNDDOWN", true);
                    editor.commit();

                    startService();
                } else {
                    InfoManager.boolean_soundDown = false;
                    editor.putBoolean("SOUNDDOWN", false);
                    editor.commit();

                    startService();
                }
            }
        });
        checkBox_SoundMute.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    InfoManager.boolean_soundMute = true;
                    editor.putBoolean("SOUNDMUTE", true);
                    editor.commit();

                    startService();
                } else {
                    InfoManager.boolean_soundMute = false;
                    editor.putBoolean("SOUNDMUTE", false);
                    editor.commit();

                    startService();
                }
            }
        });
    }

    void startService() {
        Intent soundServiceIntent = new Intent(getApplicationContext(), SoundService.class);
        soundServiceIntent.putExtra("MUSICPLAY", InfoManager.boolean_musicPlay);
        soundServiceIntent.putExtra("SOUNDUP", InfoManager.boolean_soundUp);
        soundServiceIntent.putExtra("SOUNDDOWN", InfoManager.boolean_soundDown);
        soundServiceIntent.putExtra("SOUNDMUTE", InfoManager.boolean_soundMute);
        startService(soundServiceIntent);
    }
}
