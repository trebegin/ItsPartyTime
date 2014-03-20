package com.itspartytime;

import gmusic.api.impl.GoogleMusicAPI;
import gmusic.api.impl.InvalidCredentialsException;
import gmusic.api.model.Playlists;
import gmusic.api.model.QueryResponse;
import gmusic.api.model.Song;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

public class GoogleMusicInterface 
{
	private boolean isPlaying;
	private MediaPlayer mp;
	private Context context;
	private GoogleMusicAPI api;
	private Collection<gmusic.api.model.Playlist> availablePlaylists;
	private ArrayList<Song> currentSongList;
	private gmusic.api.model.Song currentSong;
	
	public void setup(final String password, final Context context) throws Exception 
	{
		api = new GoogleMusicAPI();
		this.context = context;
		//mLogin = new Login();
		//mLogin.setup(password);
		new Thread(new Runnable() {

			@Override
			public void run() {
				
				try {
					api.login("trebegin@gmail.com", password);
					//Playlists playlists = api.getAllPlaylists();
					//availablePlaylists = playlists.getPlaylists();
					//currentPlaylist = availablePlaylists.iterator().next();
					setCurrentSongList(api.getAllSongs());
					//currentSong = api.getAllSongs().iterator().next();//currentPlaylist.getPlaylist().iterator().next();
					Log.w("Done", "Songs done loading");
					//QueryResponse q = api.search("Lorde");
					//currentSong = q.getResults().getArtists().iterator().next();
//					URI songURI = api.getSongURL(currentSong);
					
//					mp = new MediaPlayer();
//					mp.setDataSource(songURI.toString());
//					mp.prepare();
//					mp.start();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvalidCredentialsException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}).start();
		//mPlaySession = mLogin.doInBackground(null);
		
	}
	
	/**
	 * Plays song specified by songIdentifier
	 * 
	 * preconditions:
	 * 		- songIdentifier indicates a valid song
	 * 
	 * parameters:
	 * 		- int songIdentifier	-> ID of song to be played
	 * 
	 * postconditions:
	 * 		- returns 1 if successful, 0 if song is not found
	 * 		- song indicated by songIdentifier is playing
	 * 
	 * recent changes:
	 * 		-
	 * 
	 * known bugs:
	 * 		-
	 * 
	 * @param song
	 */
	public int playSong(final Song song)
	{
		if(mp == null) mp = new MediaPlayer();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					mp.reset();
					mp.setDataSource(api.getSongURL(song).toString());
					mp.prepare();
					mp.start();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}).start();
		
		
//		if(mp == null)
//		{
//			mp = MediaPlayer.create(context, R.raw.no_satisfaction_test_song);
//			mp.start();
//			isPlaying = true;
//		}
//		if(!mp.isPlaying())
//		{
//			mp.start();
//		}
		
		return 1;
	}
	
	
	/**
	 * Plays/pauses music depending on if music is currently playing
	 * 
	 * preconditions:
	 * 		- Google has songs in queue
	 * 
	 * parameters:
	 * 		- none
	 * 
	 * postconditions:
	 * 		- isPlaying is updated
	 * 		- If song was playing, it is paused
	 * 		- If song was paused, it is playing
	 * 
	 * recent changes:
	 * 		-
	 * 
	 * known bugs:
	 * 		-
	 * 
	 */
	public void pause() 
	{
		if(mp != null)
		{
			if(mp.isPlaying()) mp.pause();
			else mp.start();
		}
	}
	
	/**
	 * Pushes new playlist to Google Music
	 * 
	 * preconditions:
	 * 		- parameter playlist is non-null
	 * 
	 * parameters:
	 * 		- Playlist playlist	-> list to be pushed to Google
	 * 
	 * postconditions:
	 * 		- GoogleMusic is playing most current version of the playlist
	 * 
	 * recent changes:
	 * 		-
	 * 
	 * known bugs:
	 * 		- Should we pass and ArrayList instead of the whole Playlist?
	 * 
	 * @param playlist
	 */
	public void updatePlaylist(Playlist playlist)
	{
		
	}
	
	/**
	 * Returns available playlists for host to choose from
	 * 
	 * preconditions:
	 * 		- device is host device
	 * 
	 * parameters:
	 * 		- none
	 * 
	 * postconditions:
	 * 		- returns an ArrayList<String> of all possible playlists to choose from
	 * 
	 * recent changes:
	 * 		-
	 * 
	 * known bugs:
	 * 		-
	 * 
	 * @return
	 */
	public ArrayList<String> getAvailablePlaylists()
	{
		ArrayList<String> temp = new ArrayList<String>();
		temp.add("Current Playlist");
		return temp;
		
	}
	
	/**
	 * Gets the correct playlist from GoogleMusic
	 * 
	 * preconditions:
	 * 		- parameter playlistName is non-null
	 * 
	 * parameters:
	 * 		- String playlistName	-> used to fetch the correct playlist from GoogleMusic
	 * 
	 * postconditions:
	 * 		- Returns the song list associated with the playlistName
	 * 
	 * recent changes:
	 * 		-
	 * 
	 * known bugs:
	 * 		-
	 * 
	 * @param playlistName
	 * @return
	 */
	public ArrayList<Song> getPlaylist(String playlistName)
	{
		return null;
	}

	public ArrayList<Song> getCurrentSongList() 
	{
		return currentSongList;
	}

	public void setCurrentSongList(Collection<Song> currentSongList) 
	{
		if(this.currentSongList == null)
			this.currentSongList = new ArrayList<Song>();
		int count = 0;
		for(Song song:currentSongList)
		{
			if(count++ < 100)
				this.currentSongList.add(song);
			else break;
		}
		
	}
}
