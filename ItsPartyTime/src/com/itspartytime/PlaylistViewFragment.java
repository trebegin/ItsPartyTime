package com.itspartytime;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PlaylistViewFragment extends Fragment 
{
	private boolean isHost;
	private ArrayList<Song> displayList = null; // do we want to save actual song objects here?

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		return super.onCreateView(inflater, container, savedInstanceState);
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
