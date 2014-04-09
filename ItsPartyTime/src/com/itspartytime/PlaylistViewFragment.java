package com.itspartytime;

import gmusic.api.model.Song;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
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
		mLinearLayout = (LinearLayout) inflater.inflate(R.layout.playlist_view_fragment, container, false);
        mCurrentArtist = (TextView) mLinearLayout.findViewById(R.id.current_song_artist);
        mCurrentSongTitle = (TextView) mLinearLayout.findViewById(R.id.current_song_title);
        mCurrentAlbumArt = (ImageView) mLinearLayout.findViewById(R.id.current_song_album_art);

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

	public void notifyChange(int updateMessage) {
		switch(updateMessage)
        {
            case UPDATE_CURRENT_SONG:
                final Song currentSong = PartyActivity.getPlaylist().getCurrentSong();
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
                mCurrentSongTitle.setText(currentSong.getTitle());
                break;
            case UPDATE_PAUSE_BUTTON:
                if(PartyActivity.getPlaylist().isPlaying())
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
