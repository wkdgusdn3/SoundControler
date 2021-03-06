package com.wkdgusdn3.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Build;
import android.os.IBinder;
import android.widget.RemoteViews;

import com.wkdgusdn3.broadcastreceiver.DisableReceiver;
import com.wkdgusdn3.broadcastreceiver.MusicPlayReceiver;
import com.wkdgusdn3.broadcastreceiver.VolumeDownReceiver;
import com.wkdgusdn3.broadcastreceiver.VolumeUpReceiver;
import com.wkdgusdn3.broadcastreceiver.VolumeChangeReceiver;
import com.wkdgusdn3.manager.InfoManager;
import com.wkdgusdn3.model.SoundFunctionType;
import com.wkdgusdn3.model.ThemeType;
import com.wkdgusdn3.soundcontroller.R;

public class SoundService extends Service {

    private Context context;
    private final int currentVolumeId = R.id.current_volume;
    private final int[] functionId = {R.id.function1, R.id.function2, R.id.function3, R.id.function4};

    @Override
    public void onCreate() {

        InfoManager.setData(getApplicationContext());
        context = getApplicationContext();

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            notificationJellyBean();
        } else {
            notificationIceCreamSandwich();
        }
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    void notificationIceCreamSandwich() {

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(R.drawable.notification_icon, "soundcontroller", System.currentTimeMillis());

        if (!InfoManager.isEnableStatusBarIcon) {
            notification.priority = Notification.PRIORITY_MIN;
        }
        notification.flags = Notification.FLAG_NO_CLEAR;

        // set Views
        RemoteViews views = new RemoteViews(this.getPackageName(), R.layout.sound_notification);
        views.setImageViewResource(R.id.sound_icon, R.drawable.sound_icon);
        setCurrentVolume(views);

        for (int i = 0; i < 4; i++) {
            switch (InfoManager.buttons[i]) {
                case DISABLE:
                    setDisable(views, functionId[i]);
                    break;
                case MUSIC_PLAY:
                    setMusicPlay(views, functionId[i]);
                    break;
                case VOLUME_DOWN:
                    setSoundDown(views, functionId[i]);
                    break;
                case VOLUME_UP:
                    setSoundUp(views, functionId[i]);
                    break;
                default:
                    setSound(views, functionId[i], InfoManager.buttons[i]);
                    break;
            }
        }

        // volume change 
        enableOrDisableVolumeChangeReceiver();

        // regist to notification bar
        notification.contentView = views;
        notificationManager.notify(3, notification);

        setNotificationInfo(notificationManager, notification);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    void notificationJellyBean() {

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification;
        Notification.Builder builder = new Notification.Builder(getApplicationContext());

        builder.setSmallIcon(R.drawable.notification_icon);
        builder.setTicker("SoundController");
        if (!InfoManager.isEnableStatusBarIcon) {
            builder.setPriority(Notification.PRIORITY_MIN);
        }
        builder.setOngoing(true);
        notification = builder.build();

        // set Views
        RemoteViews views = new RemoteViews(this.getPackageName(), R.layout.sound_notification);
        views.setImageViewResource(R.id.sound_icon, R.drawable.sound_icon);
        setCurrentVolume(views);

        for (int i = 0; i < 4; i++) {
            switch (InfoManager.buttons[i]) {
                case DISABLE:
                    setDisable(views, functionId[i]);
                    break;
                case MUSIC_PLAY:
                    setMusicPlay(views, functionId[i]);
                    break;
                case VOLUME_DOWN:
                    setSoundDown(views, functionId[i]);
                    break;
                case VOLUME_UP:
                    setSoundUp(views, functionId[i]);
                    break;
                default:
                    setSound(views, functionId[i], InfoManager.buttons[i]);
                    break;

            }
        }

        // volume change
        enableOrDisableVolumeChangeReceiver();

        // regist to notification bar
        notification.contentView = views;
        notificationManager.notify(3, notification);

        setNotificationInfo(notificationManager, notification);
    }

    void setCurrentVolume(RemoteViews remoteViews) {

        // currentVolume disable
        if (!InfoManager.isEnableCurrentVolumeIcon) {
            setDisable(remoteViews, currentVolumeId);
            return;
        }

        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        if(InfoManager.theme == ThemeType.DARK) { // dark
            remoteViews.setImageViewResource(
                currentVolumeId,
                context.getResources().getIdentifier(String.format("current_volume_%s_%s", Long.toString(currentVolume), ThemeType.DARK.getResourceAdditionName()), "drawable", "com.wkdgusdn3.soundcontroller")
            );
        } else { // white
            remoteViews.setImageViewResource(
                currentVolumeId,
                context.getResources().getIdentifier(String.format("current_volume_%s_%s", Long.toString(currentVolume), ThemeType.WHITE.getResourceAdditionName()), "drawable", "com.wkdgusdn3.soundcontroller")
            );
        }
    }

    void setDisable(RemoteViews views, int id) {
        Intent intent_clear = new Intent(getApplicationContext(), DisableReceiver.class);
        PendingIntent pendingIntent_clear = PendingIntent.getBroadcast(context, 0, intent_clear, 0);
        views.setImageViewResource(id, R.drawable.disable);
        views.setOnClickPendingIntent(id, pendingIntent_clear);
    }

    void setMusicPlay(RemoteViews views, int id) {
        Intent intent = new Intent(getApplicationContext(), MusicPlayReceiver.class);
        PendingIntent pedingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        if(InfoManager.theme == ThemeType.DARK) { // dark
            views.setImageViewResource(
                id,
                context.getResources().getIdentifier(String.format("music_play_%s", ThemeType.DARK.getResourceAdditionName()), "drawable", "com.wkdgusdn3.soundcontroller")
            );
        } else { // white
            views.setImageViewResource(
                id,
                context.getResources().getIdentifier(String.format("music_play_%s", ThemeType.WHITE.getResourceAdditionName()), "drawable", "com.wkdgusdn3.soundcontroller")
            );
        }
        views.setOnClickPendingIntent(id, pedingIntent);
    }

    void setSoundUp(RemoteViews views, int id) {
        Intent intent = new Intent(getApplicationContext(), VolumeUpReceiver.class);
        PendingIntent pedingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        if(InfoManager.theme == ThemeType.DARK) { // dark
            views.setImageViewResource(
                id,
                context.getResources().getIdentifier(String.format("volume_up_%s", ThemeType.DARK.getResourceAdditionName()), "drawable", "com.wkdgusdn3.soundcontroller")
            );
        } else { // white
            views.setImageViewResource(
                id,
                context.getResources().getIdentifier(String.format("volume_up_%s", ThemeType.WHITE.getResourceAdditionName()), "drawable", "com.wkdgusdn3.soundcontroller")
            );
        }
        views.setOnClickPendingIntent(id, pedingIntent);
    }

    void setSoundDown(RemoteViews views, int id) {
        Intent intent = new Intent(getApplicationContext(), VolumeDownReceiver.class);
        PendingIntent pedingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        if(InfoManager.theme == ThemeType.DARK) { // dark
            views.setImageViewResource(
                id,
                context.getResources().getIdentifier(String.format("volume_down_%s", ThemeType.DARK.getResourceAdditionName()), "drawable", "com.wkdgusdn3.soundcontroller")
            );
        } else { // white
            views.setImageViewResource(
                id,
                context.getResources().getIdentifier(String.format("volume_down_%s", ThemeType.WHITE.getResourceAdditionName()), "drawable", "com.wkdgusdn3.soundcontroller")
            );
        }
        views.setOnClickPendingIntent(id, pedingIntent);
    }

    void setSound(RemoteViews views, int id, SoundFunctionType soundFunctionType) {

        try {

            Intent intent = new Intent(getApplicationContext(), Class.forName(String.format("com.wkdgusdn3.broadcastreceiver.SetVolumeTo%sReceiver", soundFunctionType.getVolumeAmount())));
            intent.putExtra("VOL", soundFunctionType.getVolumeAmount());
            final PendingIntent pedingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

            if(InfoManager.theme == ThemeType.DARK) { // dark
                views.setImageViewResource(
                    id,
                    context.getResources().getIdentifier(String.format("volume_%s_%s", soundFunctionType.getVolumeAmount(), ThemeType.DARK.getResourceAdditionName()), "drawable", "com.wkdgusdn3.soundcontroller")
                );
            } else { // white
                views.setImageViewResource(
                    id,
                    context.getResources().getIdentifier(String.format("volume_%s_%s", soundFunctionType.getVolumeAmount(), ThemeType.WHITE.getResourceAdditionName()), "drawable", "com.wkdgusdn3.soundcontroller")
                );
            }

            views.setOnClickPendingIntent(id, pedingIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void setNotificationInfo(NotificationManager notificationManager, Notification notification) {

        InfoManager.notificationManager = notificationManager;
        InfoManager.notification = notification;
    }

    void enableOrDisableVolumeChangeReceiver() {

        ComponentName volumeChangeReceiverComponentName = new ComponentName(getApplicationContext(), VolumeChangeReceiver.class);
        PackageManager packageManager = getApplicationContext().getPackageManager();

        if(InfoManager.isEnableCurrentVolumeIcon) {
            packageManager.setComponentEnabledSetting( // currentVolume enable
                    volumeChangeReceiverComponentName,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP
            );
        } else {
            packageManager.setComponentEnabledSetting( // currentVolume disable
                    volumeChangeReceiverComponentName,
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                    PackageManager.DONT_KILL_APP
            );
        }
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}
