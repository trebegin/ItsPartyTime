package com.itspartytime;

import android.media.MediaPlayer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Becky on 4/7/14.
 */
public class MediaPlayerHandler {
    //private Future currentSong;
    private final MediaPlayer mMediaPlayer;
    private final ExecutorService mExecutorService;

    public MediaPlayerHandler(){
        mMediaPlayer = new MediaPlayer();
        mExecutorService = Executors.newSingleThreadExecutor();
        startMediaPlayerThread();
    }

    private void startMediaPlayerThread() {
        new Thread(new Runnable() {
            private Future currentSong;
            @Override
            public void run() {
                while(true){
                    if(currentSong == null || currentSong.isDone())
                    {

                    }
                }
            }
        }).start();
    }
}
