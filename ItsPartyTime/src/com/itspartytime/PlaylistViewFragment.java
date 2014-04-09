package com.itspartytime;

import gmusic.api.model.Song;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
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
		if(PartyActivity.isLoggedIn())
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
				PartyActivity.getPlaylist().nextSong();
			}
		});
		
		pauseButton.setOnClickListener(new View.OnClickListener() 
		{	

			@Override
			public void onClick(View v) 
			{
				PartyActivity.getPlaylist().pause();
			}
		});
		
		return mLinearLayout;
	}
	
	
	
	private void playlistAdapterInit() {
		ListView mListView = (ListView) mLinearLayout.findViewById(R.id.playlist_listview);
		mLinearLayout.findViewById(R.id.not_logged_in).setVisibility(View.GONE);
		mPlaylistAdapter = new PlaylistAdapter(getActivity(), PartyActivity.getPlaylist().getCurrentSongList());
		mListView.setAdapter(mPlaylistAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,
					long id) {
                changeSong((Song) adapter.getItemAtPosition(position));
            }
	    });
    }



	@Override
	public void onAttach(Activity activity) {
		if(PartyActivity.isLoggedIn() && (mPlaylistAdapter == null))
		{
			playlistAdapterInit();
		}
		super.onAttach(activity);
	}

	
	private void changeSong(Song song)
	{
		PartyActivity.getPlaylist().playSong(song);
	}


	public void notifyChange(Object o) {
		if(mPlaylistAdapter != null)
        {
            if (o instanceof Song) mPlaylistAdapter.sortCurrentPlaylist();
			mPlaylistAdapter.notifyDataSetChanged();
        }
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
}
