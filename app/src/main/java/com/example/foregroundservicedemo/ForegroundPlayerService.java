package com.example.foregroundservicedemo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

public class ForegroundPlayerService extends Service {
        public static final int NOTIFICATION_ID = 100;//>0
        public static final String CHANNEL_ID = "ChannelId";
        private MediaPlayer player;

        @Override
        public void onCreate() {
                super.onCreate();
                createNotificationChannel();
        }

        @Override
        public IBinder onBind(Intent intent) {
                return null;
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
                Notification notification = createNotification();
        //NOTIFICATION_ID는 0보다 커야한다
                startForeground(NOTIFICATION_ID, notification);
                play(intent.getIntExtra("MUSIC", 0));
                return super.onStartCommand(intent, flags, startId);
        }

        @Override
        public void onDestroy() {
                if (player.isPlaying())
                        player.stop();
                super.onDestroy();
        }

        private void play(int musicId) {
                player = MediaPlayer.create(this, musicId);
                player.setOnCompletionListener(
                        new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                        stopSelf();
                                }
                        });
                player.start();
        }

        //알림채널등록
        private void createNotificationChannel() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        NotificationChannel channel = new NotificationChannel(
                                CHANNEL_ID,
                                "Foreground Player Channel",
                                NotificationManager.IMPORTANCE_DEFAULT
                        );
                        NotificationManager manager =
                                getSystemService(NotificationManager.class);
                        manager.createNotificationChannel(channel);
                }
        }

        //알림생성
        private Notification createNotification() {
                Intent intent = new Intent(this, MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                        intent, 0);
                return new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setContentTitle("Foreground Player")
                        .setContentText("Music is playing...")
                        .setSmallIcon(R.drawable.icon)
                        .setContentIntent(pendingIntent)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setAutoCancel(true)
                        .build();

        }
}