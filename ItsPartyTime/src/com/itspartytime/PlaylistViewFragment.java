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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class PlaylistViewFragment extends Fragment 
{
	private boolean isHost;
	private Button skipButton;
	private Button pauseButton;
    private TextView mCurrentSongTitle;
    private TextView mCurrentArtist;
    private ImageView mCurrentAlbumArt;
	private PlaylistAdapter mPlaylistAdapter;
	private LinearLayout mLinearLayout;
    public static final int UPDATE_VOTE =          0;
    public static final int UPDATE_CURRENT_SONG =  1;
    public static final int UPDATE_PAUSE_BUTTON =  2;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		//getDisplayList();
		mLinearLayout = (LinearLayout) inflater.inflate(R.layout.playlist_view_fragment, container, false);
        mCurrentArtist = (TextView) mLinearLayout.findViewById(R.id.current_song_artist);
        mCurrentSongTitle = (TextView) mLinearLayout.findViewById(R.id.current_song_title);
        mCurrentAlbumArt = (ImageView) mLinearLayout.findViewById(R.id.current_song_album_art);

		
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


	public void notifyChange(int update_message) {
		switch(update_message)
        {
            case UPDATE_CURRENT_SONG:
                Song currentSong = PartyActivity.getPlaylist().getCurrentSong();
                //mCurrentAlbumArt = ;
                mCurrentArtist.setText(currentSong.getArtist());
                mCurrentSongTitle.setText(currentSong.getTitle());
                break;
            case UPDATE_PAUSE_BUTTON:
                if(PartyActivity.isPlaying())
                    pauseButton.setBackground(getResources().getDrawable(R.drawable.pause_icon));
                else
                    pauseButton.setBackground(getResources().getDrawable(R.drawable.play_icon));
                break;
            case UPDATE_VOTE:
                mPlaylistAdapter.sortCurrentPlaylist();
                mPlaylistAdapter.notifyDataSetChanged();
                break;
            default:
                break;
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
