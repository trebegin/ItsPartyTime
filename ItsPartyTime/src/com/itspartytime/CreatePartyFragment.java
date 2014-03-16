package com.itspartytime;

import android.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Author: 
 * Date approved:
 * Approved by:
 * 
 * @author Matt
 *
 */

public class CreatePartyFragment extends Fragment
{
	private Button selectPlaylistButton;
	private Button doneCreatingPartyButton;
	private Playlist selectedPlaylist;
	private LayoutInflater inflater;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		this.inflater = inflater;
		RelativeLayout mRelativeLayout = (RelativeLayout) inflater.inflate(R.layout.create_party_fragment_layout, container, false);
		selectPlaylistButton = (Button) mRelativeLayout.findViewById(R.id.select_playlist_button);
		doneCreatingPartyButton = (Button) mRelativeLayout.findViewById(R.id.done_creating_party_button);
	
		selectPlaylistButton.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				openSelectPlaylistView();
			}
		});
		
		doneCreatingPartyButton.setOnClickListener(new View.OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				openPlaylistViewFragment();
			}
		});
		
		return mRelativeLayout;
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
	private void openSelectPlaylistView() 
	{
		openSelectPlaylistDialog();
		
	}
	
	public void openSelectPlaylistDialog()
	{
		Party.openSelectPlaylistDialog();
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
	private void openPlaylistViewFragment()
	{
		Party.openPlaylistViewFragment(this);
	}
	// create button that updates party and moves to playlistViewPage
}
