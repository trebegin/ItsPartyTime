package com.itspartytime;

import gmusic.api.model.Song;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

public class PlaylistViewFragment extends Fragment 
{
	private boolean isHost;
	private Map<String, String> displayList = new HashMap<String, String>(); // do we want to save actual song objects here?
	private Button skipButton;
	private Button pauseButton;
	private PlaylistAdapter mPlaylistAdapter;
	private LinearLayout mLinearLayout;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		//getDisplayList();
		mLinearLayout = (LinearLayout) inflater.inflate(R.layout.playlist_view_fragment, container, false);
		
		//if(displayList != null)
		//	addSongsToDisplay((LinearLayout) mLinearLayout.findViewById(R.id.playlist_song_view_holder));
		if(Party.isLoggedIn()) 
		{
			playlistAdapterInit();
			mLinearLayout.findViewById(R.id.not_logged_in).setVisibility(View.GONE);
		}

		skipButton = (Button) mLinearLayout.findViewById(R.id.skip_song_button);
		pauseButton = (Button) mLinearLayout.findViewById(R.id.pause_button);
	
		skipButton.setOnClickListener(new View.OnClickListener() 
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
	
	
	
	private void playlistAdapterInit() {
		ListView mListView = (ListView) mLinearLayout.findViewById(R.id.playlist_listview);
		mLinearLayout.findViewById(R.id.not_logged_in).setVisibility(View.GONE);
		mPlaylistAdapter = new PlaylistAdapter(getActivity(), Party.getCurrentPlaylist());
		mListView.setAdapter(mPlaylistAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,
					long id) {
                switch (view.getId())
                {
                    case R.id.vote_up_button:
                        ((Song) adapter.getItemAtPosition(position)).addUpVote();
                        Log.d("Playlist Fragment", "up button!");
                        break;
                    case R.id.vote_down_button:
                        ((Song) adapter.getItemAtPosition(position)).addDownVote();
                        Log.d("Playlist Fragment", "down button!");
                        break;
                    default:
                        changeSong((Song) adapter.getItemAtPosition(position));
                        Log.d("Playlist Fragment", "default");
                        Log.d("Playlist Fragment", String.valueOf(view.getId()));
                        break;
                }

			}
		});
	}



	@Override
	public void onAttach(Activity activity) {
		if(Party.isLoggedIn() && (mPlaylistAdapter == null)) 
		{
			playlistAdapterInit();
		}
		super.onAttach(activity);
	}



//	private void getDisplayList()
//	{
//		int songCount = 0;
//		Collection<gmusic.api.model.Song> tempList = Party.getCurrentPlaylist();
//		if(tempList != null)
//		{
//			for (gmusic.api.model.Song song:tempList)
//			{
//				if(songCount++ < 10)
//					displayList.put(song.getId(), song.getName());
//			}
//		}
//	}
//	
//	
	
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

	public void notifyChange() {
		if(mPlaylistAdapter != null)
			mPlaylistAdapter.notifyDataSetChanged();
	}
	
	public void updatePauseButton(final boolean playing) 
	{
		getActivity().runOnUiThread(new Runnable () {
			@Override
			public void run()
			{
				if(playing)
					pauseButton.setBackground(getResources().getDrawable(R.drawable.pause_icon));
				else
					pauseButton.setBackground(getResources().getDrawable(R.drawable.play_icon));
			}
		});
		
	}

	

	// create pause/play button, skip button and end/leave party button
}
