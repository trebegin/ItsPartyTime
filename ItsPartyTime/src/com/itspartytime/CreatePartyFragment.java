package com.itspartytime;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
	private Button loginButton;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		this.inflater = inflater;
		RelativeLayout mRelativeLayout = (RelativeLayout) inflater.inflate(R.layout.create_party_fragment_layout, container, false);
		selectPlaylistButton = (Button) mRelativeLayout.findViewById(R.id.select_playlist_button);
		doneCreatingPartyButton = (Button) mRelativeLayout.findViewById(R.id.done_creating_party_button);
		loginButton = (Button) mRelativeLayout.findViewById(R.id.login_button);
	
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
		
		loginButton.setOnClickListener(new View.OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				openLoginDialog();
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
	
	private void openLoginDialog()
	{
		Party.openLoginDialog();
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
		
		if(Party.isLoggedIn())
		{
			EditText partyNameTxt = (EditText) this.getActivity().findViewById(R.id.partyNameTxt);
			Party.setPartyName(partyNameTxt.getText().toString());
			TextView topRunner = (TextView) this.getActivity().findViewById(R.id.top_runner);
			topRunner.setText(partyNameTxt.getText().toString());
			Party.openPlaylistViewFragment(this);
		}
		else
		{
			Context context = this.getActivity().getApplicationContext();
			Toast.makeText(context, "Please Login First", Toast.LENGTH_LONG).show();
		}
		
		
	}
	// create button that updates party and moves to playlistViewPage
}
