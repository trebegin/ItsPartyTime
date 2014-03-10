package com.itspartytime;

import java.util.ArrayList;

public class GoogleMusicInterface 
{
	boolean isPlaying;
	
	/**
	 * Plays song specified by songID
	 * 
	 * preconditions:
	 * 		- songID indicates a valid song
	 * 
	 * parameters:
	 * 		- int songID	-> ID of song to be played
	 * 
	 * postconditions:
	 * 		- returns 1 if successful, 0 if song is not found
	 * 		- song indicated by songID is playing
	 * 
	 * recent changes:
	 * 		-
	 * 
	 * known bugs:
	 * 		-
	 * 
	 * @param songID
	 */
	public int playSong(int songID)
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
