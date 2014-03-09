package com.itspartytime;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Party extends Activity 
{
	
	private Playlist playlist;
	private String partyName;
	private Device host;
	private DeviceController deviceController;

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
	 * Changes the vote counts in 'song' depending on the value of vote: 
	 * 1 is upvote, 2 is downvote. Calls Playlist
	 * 
	 * @param vote
	 * @param song
	 */
	public void vote(int vote, Song song)
	{
		
	}
	
	/**
	 * Pause/plays current song. Ca
	 */
	public void pauseSong()
	{
		
	}
	
	

}
