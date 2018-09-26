package com.wkdgusdn3.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.IBinder;
import android.widget.RemoteViews;

import com.wkdgusdn3.broadcastreceiver.ReceiverClear;
import com.wkdgusdn3.broadcastreceiver.ReceiverMusicPlay;
import com.wkdgusdn3.broadcastreceiver.ReceiverSoundDown;
import com.wkdgusdn3.broadcastreceiver.ReceiverSoundUp;
import com.wkdgusdn3.manager.InfoManager;
import com.wkdgusdn3.model.SoundFunctionType;
import com.wkdgusdn3.soundcontroller.R;

public class SoundService extends Service {

    private Context context;
    private final int currentTextViewId = R.id.currentVolume;
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

        if (!InfoManager.isSeeStatusBarIcon) {
            notification.priority = Notification.PRIORITY_MIN;
        }
        notification.flags = Notification.FLAG_NO_CLEAR;

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
        if (!InfoManager.isSeeStatusBarIcon) {
            builder.setPriority(Notification.PRIORITY_MIN);
        }
        builder.setOngoing(true);
        notification = builder.build();

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

        notification.contentView = views;
        notificationManager.notify(3, notification);

        setNotificationInfo(notificationManager, notification);
    }

    void setCurrentVolume(RemoteViews views) {

        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        views.setTextViewText(currentTextViewId, Integer.toString(currentVolume));
    }

    void setDisable(RemoteViews views, int id) {
        Intent intent_clear = new Intent(getApplicationContext(), ReceiverClear.class);
        PendingIntent pendingIntent_clear = PendingIntent.getBroadcast(context, 0, intent_clear, 0);
        views.setImageViewResource(id, R.drawable.clear);
        views.setOnClickPendingIntent(id, pendingIntent_clear);
    }

    void setMusicPlay(RemoteViews views, int id) {
        Intent intent = new Intent(getApplicationContext(), ReceiverMusicPlay.class);
        PendingIntent pedingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        if(InfoManager.theme == 0) { // 검은색
            views.setImageViewResource(id, context.getResources().getIdentifier("music_play", "drawable", "com.wkdgusdn3.soundcontroller"));
        } else { // 흰색
            views.setImageViewResource(id, context.getResources().getIdentifier("music_play_white", "drawable", "com.wkdgusdn3.soundcontroller"));
        }
        views.setOnClickPendingIntent(id, pedingIntent);
    }

    void setSoundUp(RemoteViews views, int id) {
        Intent intent = new Intent(getApplicationContext(), ReceiverSoundUp.class);
        PendingIntent pedingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        if(InfoManager.theme == 0) { // 검은색
            views.setImageViewResource(id, context.getResources().getIdentifier("sound_up", "drawable", "com.wkdgusdn3.soundcontroller"));
        } else { // 흰색
            views.setImageViewResource(id, context.getResources().getIdentifier("sound_up_white", "drawable", "com.wkdgusdn3.soundcontroller"));
        }
        views.setOnClickPendingIntent(id, pedingIntent);
    }

    void setSoundDown(RemoteViews views, int id) {
        Intent intent = new Intent(getApplicationContext(), ReceiverSoundDown.class);
        PendingIntent pedingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        if(InfoManager.theme == 0) { // 검은색
            views.setImageViewResource(id, context.getResources().getIdentifier("sound_down", "drawable", "com.wkdgusdn3.soundcontroller"));
        } else { // 흰색
            views.setImageViewResource(id, context.getResources().getIdentifier("sound_down_white", "drawable", "com.wkdgusdn3.soundcontroller"));
        }
        views.setOnClickPendingIntent(id, pedingIntent);
    }

    void setSound(RemoteViews views, int id, SoundFunctionType soundFunctionType) {
        try {
            Intent intent = new Intent(getApplicationContext(), Class.forName("com.wkdgusdn3.broadcastreceiver.ReceiverSetSound" + soundFunctionType.getVolumeAmount()));
            intent.putExtra("VOL", soundFunctionType.getVolumeAmount());
            final PendingIntent pedingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
            if(InfoManager.theme == 0) { // 검은색
                views.setImageViewResource(id, context.getResources().getIdentifier("sound_vol" + soundFunctionType.getVolumeAmount(), "drawable", "com.wkdgusdn3.soundcontroller"));
            } else { // 흰색
                views.setImageViewResource(id, context.getResources().getIdentifier("sound_vol" + soundFunctionType.getVolumeAmount()+"_white", "drawable", "com.wkdgusdn3.soundcontroller"));
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

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}
