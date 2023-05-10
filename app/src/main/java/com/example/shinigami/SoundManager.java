package com.example.shinigami;

import android.media.MediaPlayer;
import android.content.Context;


public class SoundManager {
    public static void playButtonSound(Context context) {
        MediaPlayer mp = MediaPlayer.create(context, R.raw.sound);
        mp.seekTo(300);
        mp.start();
    }

    public static void playIntroSound(Context context) {
        MediaPlayer mp = MediaPlayer.create(context, R.raw.sound2);
        mp.start();
    }
}