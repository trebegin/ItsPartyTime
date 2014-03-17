package com.itspartytime;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;

import android.media.MediaPlayer;
import android.util.Log;

import com.faceture.google.play.LoginResponse;
import com.faceture.google.play.LoginResult;
import com.faceture.google.play.PlayClient;
import com.faceture.google.play.PlayClientBuilder;
import com.faceture.google.play.PlaySession;

public class GoogleMusicInterface 
{
	private boolean isPlaying;
	private MediaPlayer mp;
	private PlayClient mPlayClient;
	private PlaySession mPlaySession;
	private Collection<com.faceture.google.play.domain.Playlist> availablePlaylists;
	private com.faceture.google.play.domain.Playlist currentPlaylist;
	private Collection<com.faceture.google.play.domain.Song> songList;
	private com.faceture.google.play.domain.Song currentSong;
	private Login mLogin;
	
	public void setup(final String password) throws Exception 
	{
		//mLogin = new Login();
		//mLogin.setup(password);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				PlayClientBuilder mPlayClientBuilder = new PlayClientBuilder();
				PlayClient mPlayClient = mPlayClientBuilder.create();
				LoginResponse mLoginResponse;
				try {
					mLoginResponse = mPlayClient.login("trebegin@gmail.com", password);
					assert(LoginResult.SUCCESS == mLoginResponse.getLoginResult());
					// assume login is success
					mPlaySession = mLoginResponse.getPlaySession();
					assert(mPlaySession != null);
					//availablePlaylists = mPlayClient.loadAllPlaylists(mPlaySession);
					//currentPlaylist = availablePlaylists.iterator().next();
					//currentSong = currentPlaylist.getPlaylist().iterator().next();
//					songList = mPlayClient.loadAllTracks(mPlaySession);
//					currentSong = songList.iterator().next();
//					Log.w("ItsPartyTime", "SongTitle = " + currentSong.getTitle());
//					mp = new MediaPlayer();
//					mp.setDataSource(currentSong.getUrl());
//					mp.prepare();
//					mp.start();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (URISyntaxException e) {
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
	 * @param songIdentifier
	 */
	public int playSong(int songIdentifier)
	{
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
		return null;
		
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

}
