package com.itspartytime;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class JoinPartyFragment extends Fragment
{
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	/**
	 * Creates and moves to PlaylistViewPage View, called if party has been connected to
	 * 
	 * preconditions:
	 * 		- JoinPartyPage fragment is visible
	 * 		- Playlist holds a non-null song list
	 * 
	 * parameters:
	 * 		- none
	 * 
	 * postconditions:
	 * 		- JoinPartyPage fragment is destroyed
	 * 		- PlaylistViewPage fragment is created and visible 
	 * 
	 * recent changes:
	 * 		- 
	 * 
	 * known bugs:
	 * 		- 
	 */
	private void createPlaylistView()
	{
		
	}
	
	/**
	 * Connects to the host device to receive party information, called by connectButton
	 * 
	 * preconditions:
	 * 		- JoinPartyPage fragment is visible
	 * 
	 * parameters:
	 * 		- String partyName 	-> title of the party to search
	 * 
	 * postconditions:
	 * 		- returns 1 if party was found, 0 if party does not exist
	 * 		- Party is updated with found party info if party was found 
	 * 
	 * recent changes:
	 * 		- 
	 * 
	 * known bugs:
	 * 		- 
	 * 
	 * @param partyName
	 */
	private int connectToParty(String partyName)
	{
		return 1;
	}
	
	// make createButton
	
}
