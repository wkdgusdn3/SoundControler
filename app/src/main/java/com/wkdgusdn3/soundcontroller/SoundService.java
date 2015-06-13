package com.wkdgusdn3.soundcontroller;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

public class SoundService extends Service {

    Context context;

    @Override
    public void onCreate() {

        NotificationManager notificationManager;
        Notification notification;
        context = getApplicationContext();

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notification = new Notification(com.wkdgusdn3.soundcontroller.R.drawable.sound_icon, "soundcontroller", System.currentTimeMillis());
        InfoManager.setData(getApplicationContext());

        if (!InfoManager.boolean_icon) {
            notification.priority = Notification.PRIORITY_MIN;
        }
        notification.flags = Notification.FLAG_NO_CLEAR;

        RemoteViews views = new RemoteViews(this.getPackageName(), com.wkdgusdn3.soundcontroller.R.layout.sound_notification);
        views.setImageViewResource(com.wkdgusdn3.soundcontroller.R.id.sound_icon, com.wkdgusdn3.soundcontroller.R.drawable.sound_icon);

        if (InfoManager.boolean_musicPlay) {
            Intent intent_play = new Intent(getApplicationContext(), ReceiverMusicPlay.class);
            PendingIntent pendingIntent_play = PendingIntent.getBroadcast(context, 0, intent_play, 0);
            views.setImageViewResource(com.wkdgusdn3.soundcontroller.R.id.music_play, com.wkdgusdn3.soundcontroller.R.drawable.music_play);
            views.setOnClickPendingIntent(com.wkdgusdn3.soundcontroller.R.id.music_play, pendingIntent_play);
        } else {
            Intent intent_clear = new Intent(getApplicationContext(), ReceiverClear.class);
            PendingIntent pendingIntent_clear = PendingIntent.getBroadcast(context, 0, intent_clear, 0);
            views.setImageViewResource(com.wkdgusdn3.soundcontroller.R.id.music_play, com.wkdgusdn3.soundcontroller.R.drawable.clear);
            views.setOnClickPendingIntent(com.wkdgusdn3.soundcontroller.R.id.music_play, pendingIntent_clear);
        }
        if (InfoManager.boolean_soundDown) {
            Intent intent_down = new Intent(getApplicationContext(), ReceiverSoundDown.class);
            PendingIntent pendingIntent_down = PendingIntent.getBroadcast(context, 0, intent_down, 0);
            views.setImageViewResource(com.wkdgusdn3.soundcontroller.R.id.sound_down, com.wkdgusdn3.soundcontroller.R.drawable.sound_down);
            views.setOnClickPendingIntent(com.wkdgusdn3.soundcontroller.R.id.sound_down, pendingIntent_down);
        } else {
            Intent intent_clear = new Intent(getApplicationContext(), ReceiverClear.class);
            PendingIntent pendingIntent_clear = PendingIntent.getBroadcast(context, 0, intent_clear, 0);
            views.setImageViewResource(com.wkdgusdn3.soundcontroller.R.id.sound_down, com.wkdgusdn3.soundcontroller.R.drawable.clear);
            views.setOnClickPendingIntent(com.wkdgusdn3.soundcontroller.R.id.sound_down, pendingIntent_clear);
        }
        if (InfoManager.boolean_soundUp) {
            Intent intent_up = new Intent(getApplicationContext(), ReceiverSoundUp.class);
            PendingIntent pendingIntent_up = PendingIntent.getBroadcast(context, 0, intent_up, 0);
            views.setImageViewResource(com.wkdgusdn3.soundcontroller.R.id.sound_up, com.wkdgusdn3.soundcontroller.R.drawable.sound_up);
            views.setOnClickPendingIntent(com.wkdgusdn3.soundcontroller.R.id.sound_up, pendingIntent_up);
        } else {
            Intent intent_clear = new Intent(getApplicationContext(), ReceiverClear.class);
            PendingIntent pendingIntent_clear = PendingIntent.getBroadcast(context, 0, intent_clear, 0);
            views.setImageViewResource(com.wkdgusdn3.soundcontroller.R.id.sound_up, com.wkdgusdn3.soundcontroller.R.drawable.clear);
            views.setOnClickPendingIntent(com.wkdgusdn3.soundcontroller.R.id.sound_up, pendingIntent_clear);
        }
        if (InfoManager.boolean_soundMute) {
            Intent intent_mute = new Intent(getApplicationContext(), ReceiverSoundMute.class);
            PendingIntent pendingIntent_mute = PendingIntent.getBroadcast(context, 0, intent_mute, 0);
            views.setImageViewResource(com.wkdgusdn3.soundcontroller.R.id.sound_mute, com.wkdgusdn3.soundcontroller.R.drawable.sound_mute);
            views.setOnClickPendingIntent(com.wkdgusdn3.soundcontroller.R.id.sound_mute, pendingIntent_mute);
        } else {
            Intent intent_clear = new Intent(getApplicationContext(), ReceiverClear.class);
            PendingIntent pendingIntent_clear = PendingIntent.getBroadcast(context, 0, intent_clear, 0);
            views.setImageViewResource(com.wkdgusdn3.soundcontroller.R.id.sound_mute, com.wkdgusdn3.soundcontroller.R.drawable.clear);
            views.setOnClickPendingIntent(com.wkdgusdn3.soundcontroller.R.id.sound_mute, pendingIntent_clear);
        }

        notification.contentView = views;
        notificationManager.notify(3, notification);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}
