package com.kuaifa.lib.kfplayer.media;

import android.view.SurfaceHolder;

import java.io.IOException;



/**
 * Created by Administrator on 2019/3/13.
 */

public interface KMediaPlayer {

    int MEDIA_INFO_VIDEO_RENDERING_START = 3;

    void setDisplay(SurfaceHolder holder);

    void setAudioStreamType(int streamtype);

    void setScreenOnWhilePlaying(boolean screenOn);

    void setDataSource(String path) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException;

    void prepareAsync() throws IllegalStateException;

    long getCurrentPosition();

    long getDuration();

    boolean isPlaying();

    void setVolume(float leftVolume, float rightVolume);

    void start() throws IllegalStateException;

    void seekTo(long msec) throws IllegalStateException;

    void stop() throws IllegalStateException;

    void pause() throws IllegalStateException;

    void reset();

    void release();

    void setOnPreparedListener(KMediaPlayer.OnPreparedListener listener);

    void setOnCompletionListener(KMediaPlayer.OnCompletionListener listener);

    void setOnBufferingUpdateListener(
            KMediaPlayer.OnBufferingUpdateListener listener);

    void setOnSeekCompleteListener(
            KMediaPlayer.OnSeekCompleteListener listener);

    void setOnVideoSizeChangedListener(
            KMediaPlayer.OnVideoSizeChangedListener listener);

    void setOnErrorListener(KMediaPlayer.OnErrorListener listener);

    void setOnInfoListener(KMediaPlayer.OnInfoListener listener);

    void setOnTimedTextListener(KMediaPlayer.OnTimedTextListener listener);

    /*--------------------
     * Listeners
     */
    interface OnPreparedListener {
        void onPrepared(KMediaPlayer mp);
    }

    interface OnCompletionListener {
        void onCompletion(KMediaPlayer mp);
    }

    interface OnBufferingUpdateListener {
        void onBufferingUpdate(KMediaPlayer mp, int percent);
    }

    interface OnSeekCompleteListener {
        void onSeekComplete(KMediaPlayer mp);
    }

    interface OnVideoSizeChangedListener {
        void onVideoSizeChanged(KMediaPlayer mp, int width, int height,
                                int sar_num, int sar_den);
    }

    interface OnErrorListener {
        boolean onError(KMediaPlayer mp, int what, int extra);
    }

    interface OnInfoListener {
        boolean onInfo(KMediaPlayer mp, int what, int extra);
    }

    interface OnTimedTextListener {
        void onTimedText(KMediaPlayer mp);
    }

}
