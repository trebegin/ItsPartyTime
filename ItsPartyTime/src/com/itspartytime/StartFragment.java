package com.itspartytime;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

public class StartFragment extends Fragment
{
	
	private Button createPartyButton;
	private Button joinPartyButton;
	private Button loginButton;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		RelativeLayout mRelativeLayout = (RelativeLayout) inflater.inflate(R.layout.start_fragment_layout, container, false);
		createPartyButton = (Button) mRelativeLayout.findViewById(R.id.create_party_button);
		joinPartyButton = (Button) mRelativeLayout.findViewById(R.id.join_party_button);

	
		createPartyButton.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				openCreatePartyFragment();
			}
		});
		
		joinPartyButton.setOnClickListener(new View.OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				openJoinPartyFragment();
			}
		});
		
		
		
		return mRelativeLayout;
	}

	/**
	 * Creates and moves to CreatePartyPage View
	 * 
	 * preconditions:
	 * 		- StartPage fragment is visible
	 * 		- Called by createPartyButton
	 * 
	 * parameters:
	 * 		- none
	 * 
	 * postconditions:
	 * 		- StartPage fragment is destroyed
	 * 		- CreatePartyPage fragment is created and visible
	 * 
	 * recent changes:
	 * 		- called openCreatePartyFragment from PartyActivity
	 * 
	 * known bugs:
	 * 		-
	 */
	private void openCreatePartyFragment()
	{
		PartyActivity.openCreatePartyFragment(this);
	}
	
	/**
	 * Creates and moves to JoinPartyPage View, called by joinButton
	 * 
	 * preconditions:
	 * 		- StartPage fragment is visible
	 * 		- Called by createPartyButton
	 * 
	 * parameters:
	 * 		- none
	 * 
	 * postconditions:
	 * 		- StartPage fragment is destroyed
	 * 		- JoinPartyPage fragment is created and visible
	 * 
	 * recent changes:
	 * 		- 
	 * 
	 * known bugs:
	 * 		-
	 */
	private void openJoinPartyFragment()
	{
		PartyActivity.openJoinPartyFragment(this);
	}
	
	
	
	// make createButton and joinButton
}