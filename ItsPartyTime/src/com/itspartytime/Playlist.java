package com.itspartytime;

import gmusic.api.impl.GoogleMusicAPI;
import gmusic.api.impl.InvalidCredentialsException;
import gmusic.api.model.Song;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;

import android.media.MediaPlayer;
import android.util.Log;

public class Playlist 
{
    private MediaPlayer mMediaPlayer;
    private GoogleMusicAPI api;
    private ArrayList<Song> currentSongList;
	private Song currentSong;
	

	public Playlist()
	{
        api = new GoogleMusicAPI();
        mMediaPlayer = new MediaPlayer();
	}

	public void pause()
	{
        if (mMediaPlayer.isPlaying())
            mMediaPlayer.pause();
        else
            mMediaPlayer.start();
        PartyActivity.notifyChange(PlaylistViewFragment.UPDATE_PAUSE_BUTTON);

    }

	public void playSong (final Song song)
	{
		currentSong = song;
		PartyActivity.notifyChange(PlaylistViewFragment.UPDATE_CURRENT_SONG);

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    mMediaPlayer.reset();
                    mMediaPlayer.setDataSource(api.getSongURL(song).toString());
                    mMediaPlayer.prepareAsync();
                    mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mp.start();
                            PartyActivity.notifyChange(PlaylistViewFragment.UPDATE_PAUSE_BUTTON);
                        }
                    });
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

            }
        }).start();
	}

	public void nextSong() 
	{
		currentSong = currentSongList.get(currentSongList.indexOf(currentSong) + 1);
		PartyActivity.notifyChange(PlaylistViewFragment.UPDATE_CURRENT_SONG);
		playSong(currentSong);
	}

	public void login(final String email, final String password)
    {
            new Thread(new Runnable() {

                @Override
                public void run()
                {
                    try
                    {
                        PartyActivity.toaster("Logging in...");
                        api.login(email, password);
                        setCurrentSongList(api.getAllSongs());
                        Log.d("Done", "Songs done loading");
                        PartyActivity.toaster("Login Success");
                        PartyActivity.setLoggedIn(true);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    catch (URISyntaxException e)
                    {
                        e.printStackTrace();
                    }
                    catch (InvalidCredentialsException e)
                    {
                        PartyActivity.toaster("Invalid Credentials");
                        e.printStackTrace();
                    }
                }
            }).start();
	}

    public boolean isPlaying()
    {
        if (mMediaPlayer == null)
            return false;
        else
            return mMediaPlayer.isPlaying();
    }

    public ArrayList<Song> getCurrentSongList()
    {
        return currentSongList;
    }

    private void setCurrentSongList(Collection<Song> currentSongList)
    {
        if (this.currentSongList == null)
            this.currentSongList = new ArrayList<Song>();
        // limits size to 100 songs for now
        int count = 0;
        for (Song song : currentSongList) {
            if (count++ < 100)
                this.currentSongList.add(song);
            else
                break;
        }
    }

    public Song getCurrentSong()
    {
        return currentSong;
    }

    public void destroy()
    {
        if(mMediaPlayer != null)
            mMediaPlayer.release();
    }
	
}
