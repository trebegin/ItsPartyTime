package com.itspartytime;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class StartPage extends Fragment
{
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return super.onCreateView(inflater, container, savedInstanceState);
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
	 * 		-
	 * 
	 * known bugs:
	 * 		-
	 */
	private void createPartyPage()
	{
		
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
	private void joinPartyPage()
	{
		
	}
	
	// make createButton and joinButton
}