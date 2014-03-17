package com.itspartytime;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class PlaylistViewFragment extends Fragment 
{
	private boolean isHost;
	private ArrayList<Song> displayList = null; // do we want to save actual song objects here?
	private Button playButton;
	private Button pauseButton;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		LinearLayout mLinearLayout = (LinearLayout) inflater.inflate(R.layout.playlist_view_fragment, container, false);
		playButton = (Button) mLinearLayout.findViewById(R.id.play_button);
		pauseButton = (Button) mLinearLayout.findViewById(R.id.pause_button);
	
		playButton.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				Party.changeSong(null);
			}
		});
		
		pauseButton.setOnClickListener(new View.OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Party.pauseSong();
			}
		});
		
		return mLinearLayout;
	}

	/**
	 * Refreshes the song order based on newList
	 * 
	 * preconditions:
	 * 		- PlaylistViewPage fragment is visible
	 * 
	 * parameters:
	 * 		- ArrayList<Song> newList	-> new list of songs to refresh the current display
	 * 
	 * postconditions:
	 * 		- displayList is replaced with newList
	 * 
	 * recent changes:
	 * 		- 
	 * 
	 * known bugs:
	 * 		-
	 * 
	 * @param newList
	 */
	public void update(ArrayList<Song> newList) 
	{

	}
	
	
	/**
	 * Creates and calls SongInfoPage
	 * 
	 * preconditions:
	 * 		- PlaylistViewPage fragment is visible
	 * 		- displayList holds a non-null song list
	 * 
	 * parameters:
	 * 		- Song song	-> song selected from displayList
	 * 
	 * postconditions:
	 * 		- PlaylistViewPage fragment is not visible
	 * 		- SongInfoPage fragment(?) is created and visible 
	 * 
	 * recent changes:
	 * 		- 
	 * 
	 * known bugs:
	 * 		-
	 * @param song
	 */
	private void displaySongInfo(Song song) 
	{
		
	}

	// create pause/play button, skip button and end/leave party button
}
