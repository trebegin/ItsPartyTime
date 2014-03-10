package com.itspartytime;

import java.util.ArrayList;

public class Playlist 
{
	
	private ArrayList<Song> songList; // probably want queue instead, but not sure which kind
	private Song currentSong;
	
	/**
	 * Constructor, takes a list of songs to manage
	 * 
	 * @param songs
	 */
	public Playlist(ArrayList<Song> songs) 
	{
		
	}
		
	/**
	 * Changes the vote counts in 'song' depending on the value of vote: 
	 * 1 is upvote, 0 is downvote
	 * 
	 * @param vote
	 * @param song
	 */
	public void vote(int vote, Song song)
	{
		
	}
	
	/**
	 * changes songList order, assumes 'song' was just upvoted or downvoted 
	 * calls GoogleMusicInterface
	 * 
	 * @param song
	 */
	private void updatePlaylist(Song song)
	{
		
	}
	
	/**
	 * pauses/plays current song depending, calls GoogleMusicInterface
	 */
	public void pause()
	{
		
	}
	
	/**
	 * changes the current song to 'song', calls GoogleMusicInterface
	 * 
	 * @param song
	 */
	public void changeSong(Song song)
	{
		
	}


}
