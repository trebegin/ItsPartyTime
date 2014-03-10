package com.itspartytime;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Party extends Activity 
{
	
	private Playlist mPlaylist;
	private String partyName;
	private Device hostDevice;
	private boolean isHost;
	private DeviceController mDeviceController;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/**
	 * Changes the vote counts in 'song'
	 * 
	 * preconditions:
	 * 		- parameter vote is a 1 or 0
	 * 		- parameter song is non-null and exists in mPlaylist
	 * 
	 * parameters:
	 * 		- int vote	-> 1 indicates upvote, 0 indicates downvote for song
	 * 		- Song song	-> song is the object that should be changed
	 * 
	 * postconditions:
	 * 		- either songUpVotes or songDownVotes is changed in song
	 * 		- mPlaylist is reordered based on change in song
	 * 		- PlaylistViewPage fragment is updated
	 * 		- if device is not host, vote is sent to host
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
	 * Pause/play current song
	 *
	 * preconditions:
	 * 		- device is the host device
	 * 		- there is a song that is playing or has been paused
	 * 
	 * parameters:
	 * 		- none
	 * 
	 * postconditions:
	 * 		- If there is a song playing, song is paused.
	 * 		- If there is a song paused, song is resumed
	 * 
	 * recent changes:
	 * 		-
	 * 
	 * known bugs:
	 * 		-
	 */
	public void pauseSong()
	{
		
	}
	
	/**
	 * Changes current song to the new song
	 * 
	 * preconditions:
	 * 		- parameter newSong is non-null and exists in mPlaylist 
	 * 
	 * parameters:
	 * 		- Song newSong	-> song to be started
	 * 
	 * postconditions:
	 * 		- newSong is playing
	 * 		- mPlaylist order is updated
	 * 		- PlaylistViewPage fragment is updated if visible
	 * 
	 * recent changes:
	 * 		-
	 * 
	 * known bugs:
	 * 		-
	 * 
	 * @param newSong
	 */
	public void changeSong(Song newSong)
	{
		
	}
}
