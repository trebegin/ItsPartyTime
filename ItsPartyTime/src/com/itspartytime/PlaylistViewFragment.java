package com.itspartytime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class PlaylistViewFragment extends Fragment 
{
	private boolean isHost;
	private Map<String, String> displayList = new HashMap<String, String>(); // do we want to save actual song objects here?
	private Button playButton;
	private Button pauseButton;
	private PlaylistAdapter mPlaylistAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		//getDisplayList();
		LinearLayout mLinearLayout = (LinearLayout) inflater.inflate(R.layout.playlist_view_fragment, container, false);
		ListView mListView = (ListView) mLinearLayout.findViewById(R.id.playlist_listview);
		
		//if(displayList != null)
		//	addSongsToDisplay((LinearLayout) mLinearLayout.findViewById(R.id.playlist_song_view_holder));
		mPlaylistAdapter = new PlaylistAdapter(getActivity(), Party.getCurrentPlaylist());
		
		mListView.setAdapter(mPlaylistAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				changeSong((gmusic.api.model.Song) arg0.getItemAtPosition(arg2));
				
			}
		});
		
		playButton = (Button) mLinearLayout.findViewById(R.id.skip_song_button);
		pauseButton = (Button) mLinearLayout.findViewById(R.id.pause_button);
	
		playButton.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				Party.nextSong();
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
	
	private void getDisplayList()
	{
		int songCount = 0;
		Collection<gmusic.api.model.Song> tempList = Party.getCurrentPlaylist();
		if(tempList != null)
		{
			for (gmusic.api.model.Song song:tempList)
			{
				if(songCount++ < 10)
					displayList.put(song.getId(), song.getName());
			}
		}
	}
	
	
	
	private void changeSong(gmusic.api.model.Song song)
	{
		Party.changeSong(song);
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
