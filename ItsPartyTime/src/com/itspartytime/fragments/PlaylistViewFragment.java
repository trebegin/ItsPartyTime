/**
 * PartyActivity.java
 *
 * This class is responsible for controlling the UI and logically linking other classes together.
 *
 * Trent Begin, Matt Shore, Becky Torrey
 * 5/5/2014
 *
 * Variables:
 *
 *
 *
 * Known Faults:
 *
 * The name of the code artifact
 A brief description of what the code artifact does
 The programmer’s name
 The date the code artifact was coded
 The date the code artifact was approved
 The name of the person who approved the code artifact
 The arguments of the code artifact
 A list of the name of each variable of the code artifact, preferably in alphabetical
 order, and a brief description of its use
 The names of any files accessed by this code artifact
 The names of any files changed by this code artifact
 Input–output, if any
 Error-handling capabilities
 The name of the file containing test data (to be used later for regression testing)
 A list of each modification made to the code artifact, the date the modification was
 made, and who approved the modification
 Any known faults
 *
 *
 */
package com.itspartytime.fragments;

import gmusic.api.model.Song;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.itspartytime.PartyActivity;
import com.itspartytime.R;
import com.itspartytime.adapters.PlaylistAdapter;

public class PlaylistViewFragment extends Fragment 
{
	private Button skipButton;
	private Button pauseButton;
    private Button voteUpButton;
    private Button voteDownButton;
    private TextView mCurrentSongTitle;
    private TextView mCurrentArtist;
    private ImageView mCurrentAlbumArt;
	private PlaylistAdapter mPlaylistAdapter;
	private LinearLayout mLinearLayout;
    public static final int UPDATE_VOTE =          0;
    public static final int UPDATE_CURRENT_SONG =  1;
    public static final int UPDATE_PAUSE_BUTTON =  2;
    public static final int UPDATE_CURRENT_SONG_LIST = 3;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		mLinearLayout = (LinearLayout) inflater.inflate(R.layout.playlist_view_fragment, container, false);
        mCurrentArtist = (TextView) mLinearLayout.findViewById(R.id.current_song_artist);
        mCurrentSongTitle = (TextView) mLinearLayout.findViewById(R.id.current_song_title);
        mCurrentAlbumArt = (ImageView) mLinearLayout.findViewById(R.id.current_song_album_art);

		if(PartyActivity.getPlaylist().getCurrentSongList() != null)
            playlistAdapterInit();

        if(PartyActivity.getPlaylist().getCurrentSong() != null)
            setCurrentSong(PartyActivity.getPlaylist().getCurrentSong());

		skipButton = (Button) mLinearLayout.findViewById(R.id.skip_song_button);
		pauseButton = (Button) mLinearLayout.findViewById(R.id.pause_button);
        voteUpButton = (Button) mLinearLayout.findViewById(R.id.current_vote_up_button);
        voteDownButton = (Button) mLinearLayout.findViewById(R.id.current_vote_down_button);

        if(!PartyActivity.isHost())
        {
            //getActivity().findViewById(R.id.action_refresh).setVisibility(View.INVISIBLE);
            skipButton.setVisibility(View.GONE);
            pauseButton.setVisibility(View.GONE);
        }

        skipButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				PartyActivity.getPlaylist().playSong((Song) mPlaylistAdapter.getItem(0));
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
	
	private void playlistAdapterInit()
    {
		ListView mListView = (ListView) mLinearLayout.findViewById(R.id.playlist_listview);
		mPlaylistAdapter = new PlaylistAdapter(getActivity(), PartyActivity.getPlaylist().getCurrentSongList());
		mListView.setAdapter(mPlaylistAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener()
        {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,
					long id) {
                changeSong((Song) adapter.getItemAtPosition(position));
            }
	    });
        mLinearLayout.invalidate();
    }

	@Override
	public void onAttach(Activity activity) {
		if(PartyActivity.getPlaylist().getCurrentSongList() != null && (mPlaylistAdapter == null))
		{
			playlistAdapterInit();
		}
		super.onAttach(activity);
	}

	private void changeSong(Song song)
	{
        if(PartyActivity.isHost())
        {
            PartyActivity.getPlaylist().playSong(song);
        }
	}

    private void setCurrentSong(final Song currentSong)
    {
        new Thread (new Runnable() {
            @Override
            public void run() {
                URL newurl = null;
                Bitmap mIcon_val = null;
                try
                {
                    newurl = new URL(currentSong.getAlbumArtUrl());
                }
                catch (MalformedURLException e)
                {
                    e.printStackTrace();
                }
                try
                {
                    mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                catch (NullPointerException e)
                {
                    PartyActivity.toaster(currentSong.getTitle() + " does not have album art");
                }
                setAlbumArt(mIcon_val);

            }
        }).start();
        mCurrentArtist.setText(currentSong.getArtist());
        mCurrentSongTitle.setText(currentSong.getName().toString());
        voteUpButton.setText(Integer.toString(currentSong.getUpVotes()));
        voteDownButton.setText(Integer.toString(currentSong.getDownVotes()));
    }

	public void notifyChange(final int updateMessage) {
        this.getActivity().runOnUiThread(new Runnable() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void run() {
                switch(updateMessage)
                {
                    case UPDATE_CURRENT_SONG:
                        setCurrentSong(PartyActivity.getPlaylist().getCurrentSong());
                        break;
                    case UPDATE_PAUSE_BUTTON:
                        if(PartyActivity.getPlaylist().isPlaying())
                            pauseButton.setBackground(getResources().getDrawable(R.drawable.pause_icon));
                        else
                            pauseButton.setBackground(getResources().getDrawable(R.drawable.play_icon));
                        break;
                    case UPDATE_VOTE:
                        final Song currentSong2 = PartyActivity.getPlaylist().getCurrentSong();
                        mPlaylistAdapter.sortCurrentPlaylist();
                        mPlaylistAdapter.notifyDataSetChanged();
                        if (currentSong2!= null)
                        {
                            voteUpButton.setText(Integer.toString(currentSong2.getUpVotes()));
                            voteDownButton.setText(Integer.toString(currentSong2.getDownVotes()));
                        }
                        break;
                    case UPDATE_CURRENT_SONG_LIST:
                        if(PartyActivity.getPlaylist().getCurrentSongList() != null)
                        {
                            if(mPlaylistAdapter == null)
                                playlistAdapterInit();
                            mPlaylistAdapter.setCurrentPlaylist(PartyActivity.getPlaylist().getCurrentSongList());
                            mPlaylistAdapter.sortCurrentPlaylist();
                            mPlaylistAdapter.notifyDataSetChanged();
                        }
                        break;
                    default:
                        break;
                }
            }
        });


	}

    private void setAlbumArt(final Bitmap mIcon_val) {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mIcon_val != null)
                {
                    mCurrentAlbumArt.setImageBitmap(mIcon_val);
                    Log.d("Image", "Image found:" + mIcon_val.toString());
                }
                else
                {
                    Bitmap mIcon = BitmapFactory.decodeResource(getResources(), R.drawable.generic_album_artwork);
                    mCurrentAlbumArt.setImageBitmap(mIcon);
                }
            }
        });
    }

}
