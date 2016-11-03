package com.wkdgusdn3.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.widget.RemoteViews;

import com.wkdgusdn3.broadcastreceiver.ReceiverClear;
import com.wkdgusdn3.broadcastreceiver.ReceiverMusicPlay;
import com.wkdgusdn3.broadcastreceiver.ReceiverSoundDown;
import com.wkdgusdn3.broadcastreceiver.ReceiverSoundMute;
import com.wkdgusdn3.broadcastreceiver.ReceiverSoundUp;
import com.wkdgusdn3.manager.InfoManager;
import com.wkdgusdn3.soundcontroller.R;

public class SoundService extends Service {

    Context context;
    int[] functionId = {R.id.function1, R.id.function2, R.id.function3, R.id.function4};

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
        NotificationManager notificationManager;
        Notification notification;
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notification = new Notification(com.wkdgusdn3.soundcontroller.R.drawable.sound_icon, "soundcontroller", System.currentTimeMillis());

        if (!InfoManager.boolean_icon) {
            notification.priority = Notification.PRIORITY_MIN;
        }
        notification.flags = Notification.FLAG_NO_CLEAR;

        RemoteViews views = new RemoteViews(this.getPackageName(), com.wkdgusdn3.soundcontroller.R.layout.sound_notification);
        views.setImageViewResource(com.wkdgusdn3.soundcontroller.R.id.sound_icon, com.wkdgusdn3.soundcontroller.R.drawable.sound_icon);

        for (int i = 0; i < 4; i++) {
            switch (InfoManager.functions[i]) {
                case 0:
                    setMusicPlay(views, functionId[i]);
                    break;
                case 1:
                    setSoundUp(views, functionId[i]);
                    break;
                case 2:
                    setSoundDown(views, functionId[i]);
                    break;
                case 3:
                    setSoundMute(views, functionId[i]);
                    break;
            }
        }

//        if (InfoManager.boolean_musicPlay) {
//            Intent intent_play = new Intent(getApplicationContext(), ReceiverMusicPlay.class);
//            PendingIntent pendingIntent_play = PendingIntent.getBroadcast(context, 0, intent_play, 0);
//            views.setImageViewResource(com.wkdgusdn3.soundcontroller.R.id.music_play, com.wkdgusdn3.soundcontroller.R.drawable.music_play);
//            views.setOnClickPendingIntent(com.wkdgusdn3.soundcontroller.R.id.music_play, pendingIntent_play);
//        } else {
//            Intent intent_clear = new Intent(getApplicationContext(), ReceiverClear.class);
//            PendingIntent pendingIntent_clear = PendingIntent.getBroadcast(context, 0, intent_clear, 0);
//            views.setImageViewResource(com.wkdgusdn3.soundcontroller.R.id.music_play, com.wkdgusdn3.soundcontroller.R.drawable.clear);
//            views.setOnClickPendingIntent(com.wkdgusdn3.soundcontroller.R.id.music_play, pendingIntent_clear);
//        }
//        if (InfoManager.boolean_soundDown) {
//            Intent intent_down = new Intent(getApplicationContext(), ReceiverSoundDown.class);
//            PendingIntent pendingIntent_down = PendingIntent.getBroadcast(context, 0, intent_down, 0);
//            views.setImageViewResource(com.wkdgusdn3.soundcontroller.R.id.sound_down, com.wkdgusdn3.soundcontroller.R.drawable.sound_down);
//            views.setOnClickPendingIntent(com.wkdgusdn3.soundcontroller.R.id.sound_down, pendingIntent_down);
//        } else {
//            Intent intent_clear = new Intent(getApplicationContext(), ReceiverClear.class);
//            PendingIntent pendingIntent_clear = PendingIntent.getBroadcast(context, 0, intent_clear, 0);
//            views.setImageViewResource(com.wkdgusdn3.soundcontroller.R.id.sound_down, com.wkdgusdn3.soundcontroller.R.drawable.clear);
//            views.setOnClickPendingIntent(com.wkdgusdn3.soundcontroller.R.id.sound_down, pendingIntent_clear);
//        }
//        if (InfoManager.boolean_soundUp) {
//            Intent intent_up = new Intent(getApplicationContext(), ReceiverSoundUp.class);
//            PendingIntent pendingIntent_up = PendingIntent.getBroadcast(context, 0, intent_up, 0);
//            views.setImageViewResource(com.wkdgusdn3.soundcontroller.R.id.sound_up, com.wkdgusdn3.soundcontroller.R.drawable.sound_up);
//            views.setOnClickPendingIntent(com.wkdgusdn3.soundcontroller.R.id.sound_up, pendingIntent_up);
//        } else {
//            Intent intent_clear = new Intent(getApplicationContext(), ReceiverClear.class);
//            PendingIntent pendingIntent_clear = PendingIntent.getBroadcast(context, 0, intent_clear, 0);
//            views.setImageViewResource(com.wkdgusdn3.soundcontroller.R.id.sound_up, com.wkdgusdn3.soundcontroller.R.drawable.clear);
//            views.setOnClickPendingIntent(com.wkdgusdn3.soundcontroller.R.id.sound_up, pendingIntent_clear);
//        }
//        if (InfoManager.boolean_soundMute) {
//            Intent intent_mute = new Intent(getApplicationContext(), ReceiverSoundMute.class);
//            PendingIntent pendingIntent_mute = PendingIntent.getBroadcast(context, 0, intent_mute, 0);
//            views.setImageViewResource(com.wkdgusdn3.soundcontroller.R.id.sound_mute, com.wkdgusdn3.soundcontroller.R.drawable.sound_mute);
//            views.setOnClickPendingIntent(com.wkdgusdn3.soundcontroller.R.id.sound_mute, pendingIntent_mute);
//        } else {
//            Intent intent_clear = new Intent(getApplicationContext(), ReceiverClear.class);
//            PendingIntent pendingIntent_clear = PendingIntent.getBroadcast(context, 0, intent_clear, 0);
//            views.setImageViewResource(com.wkdgusdn3.soundcontroller.R.id.sound_mute, com.wkdgusdn3.soundcontroller.R.drawable.clear);
//            views.setOnClickPendingIntent(com.wkdgusdn3.soundcontroller.R.id.sound_mute, pendingIntent_clear);
//        }

        notification.contentView = views;
        notificationManager.notify(3, notification);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    void notificationJellyBean() {
        NotificationManager notificationManager;
        Notification notification;
        Notification.Builder builder = new Notification.Builder(getApplicationContext());

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        builder.setSmallIcon(R.drawable.sound_icon);
        builder.setTicker("SoundController");
        if (!InfoManager.boolean_icon) {
            builder.setPriority(Notification.PRIORITY_MIN);
        }
        builder.setOngoing(true);
        notification = builder.build();

        RemoteViews views = new RemoteViews(this.getPackageName(), com.wkdgusdn3.soundcontroller.R.layout.sound_notification);
        views.setImageViewResource(com.wkdgusdn3.soundcontroller.R.id.sound_icon, com.wkdgusdn3.soundcontroller.R.drawable.sound_icon);

        for (int i = 0; i < 4; i++) {
            switch (InfoManager.functions[i]) {
                case 0:
                    setMusicPlay(views, functionId[i]);
                    break;
                case 1:
                    setSoundUp(views, functionId[i]);
                    break;
                case 2:
                    setSoundDown(views, functionId[i]);
                    break;
                case 3:
                    setSoundMute(views, functionId[i]);
                    break;
                case 4:
                    break;
            }
        }

//        if (InfoManager.boolean_musicPlay) {
//            Intent intent_play = new Intent(getApplicationContext(), ReceiverMusicPlay.class);
//            PendingIntent pendingIntent_play = PendingIntent.getBroadcast(context, 0, intent_play, 0);
//            views.setImageViewResource(com.wkdgusdn3.soundcontroller.R.id.music_play, com.wkdgusdn3.soundcontroller.R.drawable.music_play);
//            views.setOnClickPendingIntent(com.wkdgusdn3.soundcontroller.R.id.music_play, pendingIntent_play);
//        } else {
//            Intent intent_clear = new Intent(getApplicationContext(), ReceiverClear.class);
//            PendingIntent pendingIntent_clear = PendingIntent.getBroadcast(context, 0, intent_clear, 0);
//            views.setImageViewResource(com.wkdgusdn3.soundcontroller.R.id.music_play, com.wkdgusdn3.soundcontroller.R.drawable.clear);
//            views.setOnClickPendingIntent(com.wkdgusdn3.soundcontroller.R.id.music_play, pendingIntent_clear);
//        }
//        if (InfoManager.boolean_soundDown) {
//            Intent intent_down = new Intent(getApplicationContext(), ReceiverSoundDown.class);
//            PendingIntent pendingIntent_down = PendingIntent.getBroadcast(context, 0, intent_down, 0);
//            views.setImageViewResource(com.wkdgusdn3.soundcontroller.R.id.sound_down, com.wkdgusdn3.soundcontroller.R.drawable.sound_down);
//            views.setOnClickPendingIntent(com.wkdgusdn3.soundcontroller.R.id.sound_down, pendingIntent_down);
//        } else {
//            Intent intent_clear = new Intent(getApplicationContext(), ReceiverClear.class);
//            PendingIntent pendingIntent_clear = PendingIntent.getBroadcast(context, 0, intent_clear, 0);
//            views.setImageViewResource(com.wkdgusdn3.soundcontroller.R.id.sound_down, com.wkdgusdn3.soundcontroller.R.drawable.clear);
//            views.setOnClickPendingIntent(com.wkdgusdn3.soundcontroller.R.id.sound_down, pendingIntent_clear);
//        }
//        if (InfoManager.boolean_soundUp) {
//            Intent intent_up = new Intent(getApplicationContext(), ReceiverSoundUp.class);
//            PendingIntent pendingIntent_up = PendingIntent.getBroadcast(context, 0, intent_up, 0);
//            views.setImageViewResource(com.wkdgusdn3.soundcontroller.R.id.sound_up, com.wkdgusdn3.soundcontroller.R.drawable.sound_up);
//            views.setOnClickPendingIntent(com.wkdgusdn3.soundcontroller.R.id.sound_up, pendingIntent_up);
//        } else {
//            Intent intent_clear = new Intent(getApplicationContext(), ReceiverClear.class);
//            PendingIntent pendingIntent_clear = PendingIntent.getBroadcast(context, 0, intent_clear, 0);
//            views.setImageViewResource(com.wkdgusdn3.soundcontroller.R.id.sound_up, com.wkdgusdn3.soundcontroller.R.drawable.clear);
//            views.setOnClickPendingIntent(com.wkdgusdn3.soundcontroller.R.id.sound_up, pendingIntent_clear);
//        }
//        if (InfoManager.boolean_soundMute) {
//            Intent intent_mute = new Intent(getApplicationContext(), ReceiverSoundMute.class);
//            PendingIntent pendingIntent_mute = PendingIntent.getBroadcast(context, 0, intent_mute, 0);
//            views.setImageViewResource(com.wkdgusdn3.soundcontroller.R.id.sound_mute, com.wkdgusdn3.soundcontroller.R.drawable.sound_mute);
//            views.setOnClickPendingIntent(com.wkdgusdn3.soundcontroller.R.id.sound_mute, pendingIntent_mute);
//        } else {
//            Intent intent_clear = new Intent(getApplicationContext(), ReceiverClear.class);
//            PendingIntent pendingIntent_clear = PendingIntent.getBroadcast(context, 0, intent_clear, 0);
//            views.setImageViewResource(com.wkdgusdn3.soundcontroller.R.id.sound_mute, com.wkdgusdn3.soundcontroller.R.drawable.clear);
//            views.setOnClickPendingIntent(com.wkdgusdn3.soundcontroller.R.id.sound_mute, pendingIntent_clear);
//        }

        notification.contentView = views;
        notificationManager.notify(3, notification);
    }

    void setMusicPlay(RemoteViews views, int id) {
        Intent intent = new Intent(getApplicationContext(), ReceiverMusicPlay.class);
        PendingIntent pedingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        views.setImageViewResource(id, R.drawable.music_play);
        views.setOnClickPendingIntent(id, pedingIntent);
    }

    void setSoundUp(RemoteViews views, int id) {
        Intent intent = new Intent(getApplicationContext(), ReceiverSoundUp.class);
        PendingIntent pedingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        views.setImageViewResource(id, R.drawable.sound_up);
        views.setOnClickPendingIntent(id, pedingIntent);
    }

    void setSoundDown(RemoteViews views, int id) {
        Intent intent = new Intent(getApplicationContext(), ReceiverSoundDown.class);
        PendingIntent pedingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        views.setImageViewResource(id, R.drawable.sound_down);
        views.setOnClickPendingIntent(id, pedingIntent);
    }

    void setSoundMute(RemoteViews views, int id) {
        Intent intent = new Intent(getApplicationContext(), ReceiverSoundMute.class);
        PendingIntent pedingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        views.setImageViewResource(id, R.drawable.sound_mute);
        views.setOnClickPendingIntent(id, pedingIntent);
    }

    void setClear(RemoteViews views, int id) {
        Intent intent_clear = new Intent(getApplicationContext(), ReceiverClear.class);
        PendingIntent pendingIntent_clear = PendingIntent.getBroadcast(context, 0, intent_clear, 0);
        views.setImageViewResource(id, R.drawable.clear);
        views.setOnClickPendingIntent(id, pendingIntent_clear);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}
