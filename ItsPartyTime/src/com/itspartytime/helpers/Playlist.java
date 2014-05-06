/**
 * Playlist.java
 *
 * This class is a wrapper for the Google Music Interface. It keeps track of the songs in a playlist,
 *  and provides logic for interacting with them.
 *
 * Trent Begin, Matt Shore, Becky Torrey
 * 5/5/2014
 *
 * Variables:
 * private MediaPlayer mMediaPlayer:            Object that is responsible for playing songs through phone
 * private GoogleMusicAPI api:                  API Object for the Google Music Interface
 * private ArrayList<Song> currentSongList:     The current playlist of songs
 * private Song currentSong:                    The song currently playing
 * private boolean ableToPause:                 If the device is able to play and pause the music
 *
 *
 * Known Faults:
 *
 *
 *
 */
package com.itspartytime.helpers;

import gmusic.api.impl.GoogleMusicAPI;
import gmusic.api.impl.InvalidCredentialsException;
import gmusic.api.model.Song;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;

import android.media.MediaPlayer;
import android.provider.Telephony;
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


    /**
     * Constructor that creates a new MediaPlayer and instantiates a Google Music API
     */
	public Playlist()
	{
        api = new GoogleMusicAPI();
        mMediaPlayer = new MediaPlayer();
	}

    /**
     * Pauses and Plays a song, dependent on the ableToPause Boolean
     */
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

    /**
     * Plays the parameter song and then updates the current playlist
     * @param song
     */
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

    /**
     * The login attempt for the Google Music Interface. If Successful, the open the playlist view,
     * otherwise notify the device for invalid credentials
     * @param email
     * @param password
     */
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
                        PartyActivity.openPlaylistViewFragment(null);
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

    /**
     * Returns the boolean if the device is playing music
     * @return mMediaPlayer.isPlaying() or false if not instantiated
     */
    public boolean isPlaying()
    {
        if (mMediaPlayer == null)
            return false;
        else
            return mMediaPlayer.isPlaying();
    }

    /**
     * Returns the current song list
     * @return currentSongList
     */
    public ArrayList<Song> getCurrentSongList()
    {
        return currentSongList;
    }

    /**
     * Sets the current song list to the first 100 songs in the Google Music Account. Passes the song
     *  list to add from
     * @param currentSongList
     */
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

    /**
     * Getters and setters for Playlist Class
     */
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

    /**
     * Finds a song object in the currentSongList by name and returns it, returns null otherwise
     * @param name
     * @return
     */
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
