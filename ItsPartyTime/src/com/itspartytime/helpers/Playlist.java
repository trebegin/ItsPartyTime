package com.itspartytime.helpers;

import gmusic.api.impl.GoogleMusicAPI;
import gmusic.api.impl.InvalidCredentialsException;
import gmusic.api.model.Song;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;

import android.media.MediaPlayer;
import android.util.Log;

import com.itspartytime.PartyActivity;
import com.itspartytime.fragments.PlaylistViewFragment;

public class Playlist 
{
    private MediaPlayer mMediaPlayer;
    private GoogleMusicAPI api;
    private ArrayList<Song> currentSongList;
	private Song currentSong;
    private boolean ableToPause = false;
	

	public Playlist()
	{
        api = new GoogleMusicAPI();
        mMediaPlayer = new MediaPlayer();
	}

	public void pause()
	{
        if(ableToPause)
        {
            if (mMediaPlayer.isPlaying())
                mMediaPlayer.pause();
            else
                mMediaPlayer.start();
        PartyActivity.notifyChange(PlaylistViewFragment.UPDATE_PAUSE_BUTTON);
        }

    }

	public void playSong (final Song song)
	{
        ableToPause = false;
		currentSong = song;
        currentSongList.remove(currentSong);
		PartyActivity.notifyChange(PlaylistViewFragment.UPDATE_CURRENT_SONG);
        PartyActivity.notifyChange(PlaylistViewFragment.UPDATE_PAUSE_BUTTON);
        PartyActivity.notifyChange(PlaylistViewFragment.UPDATE_CURRENT_SONG_LIST);
        PartyActivity.updateSongList();


        new Thread(new Runnable()
        {

            @Override
            public void run()
            {
                try {
                    mMediaPlayer.reset();
                    mMediaPlayer.setDataSource(api.getSongURL(song).toString());
                    mMediaPlayer.prepareAsync();
                    mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
                    {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            mMediaPlayer.start();
                            PartyActivity.notifyChange(PlaylistViewFragment.UPDATE_PAUSE_BUTTON);
                            ableToPause = true;
                        }
                    });
                    mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
                    {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            playSong(currentSongList.get(0));
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        }).start();
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
        //PartyActivity.notifyChange(PlaylistViewFragment.UPDATE_CURRENT_SONG_LIST);
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

    public void setCurrentSongList(ArrayList<Song> songs)
    {
        currentSongList = (ArrayList<Song>)songs.clone();
        PartyActivity.notifyChange(PlaylistViewFragment.UPDATE_CURRENT_SONG_LIST);
    }

    public void setCurrentSong(Song currSong)
    {
        currentSong = currSong;
        PartyActivity.notifyChange(PlaylistViewFragment.UPDATE_CURRENT_SONG);
    }

    public Song findSongByName(String name)
    {
        for(Song s: currentSongList)
        {
            if(s.getName().compareTo(name) == 0)
                return s;
        }
        return null;
    }
	
}
