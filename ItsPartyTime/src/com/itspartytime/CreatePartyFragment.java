package com.itspartytime;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Author: 
 * Date approved:
 * Approved by:
 * 
 * @author Becky
 *
 */

public class CreatePartyFragment extends Fragment
{
	private Playlist selectedPlaylist;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	/**
	 * Displays a list of Playlists and records user's choice
	 * 
	 * preconditions:
	 * 		- CreatePartyPage fragment is visible
	 * 
	 * parameters:
	 * 		- none
	 * 
	 * postconditions:
	 * 		- selectedPlaylist holds the Playlist selected by the user 
	 * 
	 * recent changes:
	 * 		- 
	 * 
	 * known bugs:
	 * 		- 
	 */
	private void selectPlaylist() 
	{
		
	}
	
	/**
	 * Creates and moves to playlistViewPage
	 * 
	 * preconditions:
	 * 		- CreatePartyPage fragment is visible
	 * 		- Playlist holds a non-null song list
	 * 
	 * parameters:
	 * 		- none
	 * 
	 * postconditions:
	 * 		- CreatePartyPage fragment is destroyed
	 * 		- PlaylistViewPage fragment is created and visible 
	 * 
	 * recent changes:
	 * 		- 
	 * 
	 * known bugs:
	 * 		- 
	 */
	private void playlistViewPage()
	{
		
	}
	// create button that updates party and moves to playlistViewPage
}
