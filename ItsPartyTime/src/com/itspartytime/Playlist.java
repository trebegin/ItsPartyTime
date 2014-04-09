package com.itspartytime;

import gmusic.api.impl.GoogleMusicAPI;
import gmusic.api.impl.InvalidCredentialsException;
import gmusic.api.model.Song;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

public class Playlist 
{
    private MediaPlayer mp;
    private GoogleMusicAPI api;
    private ArrayList<Song> currentSongList;
	private Song currentSong;
	

	public Playlist(Context context) 
	{
        api = new GoogleMusicAPI();
        mp = new MediaPlayer();
	}

	public void pause()
	{
        if (mp.isPlaying())
            mp.pause();
        else
            mp.start();
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
                    mp.reset();
                    mp.setDataSource(api.getSongURL(song).toString());
                    mp.prepareAsync();
                    mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
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

	public ArrayList<Song> getSongList()
	{
		ArrayList<Song> list = new ArrayList<Song>();
		if(currentSongList != null)
		{
			for(Song item:currentSongList)
			{
				list.add(item);
			}
		}
		return list;
	}

	public void nextSong() 
	{
		currentSong = currentSongList.get(currentSongList.indexOf(currentSong) + 1);
		PartyActivity.notifyChange(PlaylistViewFragment.UPDATE_CURRENT_SONG);
		playSong(currentSong);
	}

	public Song getCurrentSong()
    {
		return currentSong;
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

	public static void updatePauseButton(boolean playing)
    {
		PartyActivity.updatePauseButton(playing);
	}


    public void setCurrentSongList(Collection<Song> currentSongList)
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

    public ArrayList<Song> getCurrentSongList()
    {
        return currentSongList;
    }

    public boolean isPlaying()
    {
        if (mp == null)
            return false;
        else
            return mp.isPlaying();
    }

    public void destroy()
    {
        if(mp != null)
            mp.release();
    }
	
}
