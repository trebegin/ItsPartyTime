package com.itspartytime;

import java.util.ArrayList;
import java.util.Collection;

import android.content.Context;

public class Playlist 
{
	
	private GoogleMusicInterface mGoogleMusicInterface;
	private Collection<gmusic.api.model.Song> songList; // probably want queue instead, but not sure which kind
	private Song currentSong;
	
	/**
	 * Constructor, takes a list of songs to manage
	 *
	 * preconditions:
	 * 		- 
	 * 
	 * parameters:
	 * 		- ArrayList<Song> songs	-> list of songs for Playlist to manage
	 * 
	 * postconditions:
	 * 		- 
	 * 
	 * recent changes:
	 * 		-
	 * 
	 * known bugs:
	 * 		-
	 * 
	 * @param collection
	 */
	public Playlist(Context context) 
	{
		mGoogleMusicInterface = new GoogleMusicInterface();
		try 
		{
			mGoogleMusicInterface.setup("rejeto123!", context);
			//mPlaylist = new Playlist(mGoogleMusicInterface.getCurrentPlaylist());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		
	/**
	 * Changes the vote counts in 'song'
	 * 
	 * preconditions:
	 * 		- parameter vote is a 1 or 0
	 * 		- parameter song is non-null and exists in songList
	 * 
	 * parameters:
	 * 		- int vote	-> 1 indicates upvote, 0 indicates downvote for song
	 * 		- Song song	-> song is the object that should be changed
	 * 
	 * postconditions:
	 * 		- either songUpVotes or songDownVotes is changed in song
	 * 		- songList is reordered based on change in song
	 * 
	 * recent changes:
	 * 		-
	 * 
	 * known bugs:
	 * 		-
	 * 
	 * @param vote
	 * @param song
	 */
	public void vote(int vote, Song song)
	{
		
	}
	
	/**
	 * pauses/plays current song depending, calls GoogleMusicInterface
	 * 
	 * preconditions:
	 * 		- A song is being played or has been paused
	 * 
	 * parameters:
	 * 		- none
	 * 
	 * postconditions:
	 * 		- If a song was playing, song is now paused
	 * 		- If a song was paused, it is now playing
	 * 
	 * recent changes:
	 * 		-
	 * 
	 * known bugs:
	 * 		-
	 */
	public void pause()
	{
		mGoogleMusicInterface.pause();
	}
	
	/**
	 * changes the current song to 'song', calls GoogleMusicInterface
	 * 
	 * preconditions:
	 * 		- 
	 * 
	 * parameters:
	 * 		- Song newSong	-> song to be played
	 * 
	 * postconditions:
	 * 		- newSong is playing
	 * 		- currentSong equals newSong
	 * 
	 * recent changes:
	 * 		-
	 * 
	 * known bugs:
	 * 		-
	 * 
	 * @param songID
	 */
	public void changeSong(String songID)
	{
		for(gmusic.api.model.Song song:songList)
		{
			if(song.getId() == songID)
				mGoogleMusicInterface.playSong(song);
		}
	}
	
	/**
	 * changes songList order and calls GoogleMusicInterface
	 * 
	 * preconditions:
	 * 		- parameter song is non-null
	 * 		- parameter song exists in songList
	 * 		- parameter song is not currentSong
	 * 		- parameter song is the only song that has been changed in songList
	 * 
	 * parameters:
	 * 		- Song song	-> song is the object that needs to be moved according to new 
	 * 		  vote counts
	 * 
	 * postconditions:
	 * 		- songList order is changed
	 * 		- GoogleMusic has the updated version of songList
	 * 
	 * recent changes:
	 * 		-
	 * 
	 * known bugs:
	 * 		-
	 * 
	 * @param song
	 */
	private void updatePlaylist(Song song)
	{
		
	}

	public ArrayList<gmusic.api.model.Song> getSongList() {
		ArrayList<gmusic.api.model.Song> list = new ArrayList<gmusic.api.model.Song>();
		if(songList != null)
		{
			for(gmusic.api.model.Song item:songList)
			{
				list.add(item);
			}
		}
		return list;
	}

	public void setSongList(Collection<gmusic.api.model.Song> collection) {
		this.songList = collection;
	}

	public Song getCurrentSong() {
		return currentSong;
	}

	public void setCurrentSong(Song currentSong) {
		this.currentSong = currentSong;
	}

	public void update() {
		setSongList(mGoogleMusicInterface.getCurrentSongList());
		
	}
}
