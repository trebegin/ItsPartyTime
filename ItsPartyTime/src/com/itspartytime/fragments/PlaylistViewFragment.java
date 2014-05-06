/**
 * PlaylistViewFragment.java
 *
 * Trent Begin, Matt Shore, Becky Torrey
 * 5/5/2014
 *
 * Variables:
 *
 * private Button skipButton:                                   Button to skip song
 * private Button pauseButton:                                  Button to Play/Pause song
 * private Button voteUpButton:                                 Button to vote up song
 * private Button voteDownButton:                               Button to vote down song
 * private TextView mCurrentSongTitle:                          Current Song Title
 * private TextView mCurrentArtist:                             Current Artist Name
 * private ImageView mCurrentAlbumArt:                          Current Album Art URL
 * private PlaylistAdapter mPlaylistAdapter:                    The adapter for holding playlist info
 * private LinearLayout mLinearLayout:                          UI Layout for each song
 *
 * // Integer Codes for Update Handlers
 * public static final int UPDATE_VOTE =          0;            Voting Up Song
 * public static final int UPDATE_CURRENT_SONG =  1;            Update the current song
 * public static final int UPDATE_PAUSE_BUTTON =  2;            Alternates the Play/Pause Button
 * public static final int UPDATE_CURRENT_SONG_LIST = 3;        Update the list of current songs
 *
 *
 * Known Faults:
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
    public static final int UPDATE_VOTE =                   0;
    public static final int UPDATE_CURRENT_SONG =           1;
    public static final int UPDATE_PAUSE_BUTTON =           2;
    public static final int UPDATE_CURRENT_SONG_LIST =      3;
	
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

        /**
         * Only Shows play pause buttons if the device is a host
         */
        if(!PartyActivity.isHost())
        {
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

    /**
     * Initializes the adapter for holding the playlist information
     */
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

    /**
     * Only Changes song on click if the device is a host
     * @param song
     */
	private void changeSong(Song song)
	{
        if(PartyActivity.isHost())
        {
            PartyActivity.getPlaylist().playSong(song);
        }
	}


    /**
     * Sets the Current song to the incoming parameter
     * @param currentSong
     */
    private void setCurrentSong(final Song currentSong)
    {
        new Thread (new Runnable() {
            @Override
            public void run() {
                URL newurl = null;
                Bitmap mIcon_val = null;
                setAlbumArt(mIcon_val);
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

    /**
     * Notifies the UI of an update to the Playlist based on the sent updateMessage
     * @param updateMessage
     */
	public void notifyChange(final int updateMessage)
    {
        this.getActivity().runOnUiThread(new Runnable()
        {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void run()
            {
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

    /**
     * Sets the Current Album Art to the Bitmap received from the Art URL
     * @param mIcon_val
     */
    private void setAlbumArt(final Bitmap mIcon_val)
    {

        getActivity().runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
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
