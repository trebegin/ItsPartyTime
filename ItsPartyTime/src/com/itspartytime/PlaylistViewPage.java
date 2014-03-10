package com.itspartytime;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PlaylistViewPage extends Fragment 
{

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
	 * @param newList
	 */
	public void update(ArrayList<Song> newList) 
	{

	}

	// create pause/play button, skip button and end/leave party button
}
