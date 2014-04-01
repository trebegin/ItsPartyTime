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
	private Button loginButton;
	private Playlist selectedPlaylist;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		RelativeLayout mRelativeLayout = (RelativeLayout) inflater.inflate(R.layout.create_party_fragment_layout, container, false);
		selectPlaylistButton = (Button) mRelativeLayout.findViewById(R.id.select_playlist_button);
		doneCreatingPartyButton = (Button) mRelativeLayout.findViewById(R.id.done_creating_party_button);
		loginButton = (Button) mRelativeLayout.findViewById(R.id.login_button); 
	
		selectPlaylistButton.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				openSelectPlaylistDialog();
			}
		});
		
		doneCreatingPartyButton.setOnClickListener(new View.OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				if(Party.isLoggedIn())
					openPlaylistViewFragment();
				else
				{
					Context context = getActivity().getApplicationContext();
					CharSequence text = "Not logged in yet!";
					int duration = Toast.LENGTH_SHORT;

					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
				}
			}
		});
		
		loginButton.setOnClickListener(new View.OnClickListener() {
			
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
	private void openSelectPlaylistDialog()
	{
		Party.openSelectPlaylistDialog();
	}
	
	/**
	 * Creates and moves to playlistViewPage
	 * 
	 * preconditions:
	 * 		- CreatePartyPage fragment is visible
	 * 		- User has selected desired playlist
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
		EditText partyNameTxt = (EditText) this.getActivity().findViewById(R.id.partyNameTxt);
		Party.setPartyName(partyNameTxt.getText().toString());
		TextView topRunner = (TextView) this.getActivity().findViewById(R.id.top_runner);
		topRunner.setText(partyNameTxt.getText().toString());
		Party.openPlaylistViewFragment(this);
	}
	
	private void openLoginDialog()
	{
		Party.openLoginDialog();
	}
}
