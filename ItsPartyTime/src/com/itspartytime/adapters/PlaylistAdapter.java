/**
 * PlaylistAdapter.java
 *
 * The class is a UI adapter for the Playlist object and contains the logic for interacting with the UI.
 *
 * Trent Begin, Matt Shore, Becky Torrey
 * 5/5/2014
 *
 * Variables:
 * private Context mContext:                            The UI application context
 * private static ArrayList<Song> currentPlaylist:      The current Arraylist of Songs
 *
 * Known Faults:
 *
 */


package com.itspartytime.adapters;

import gmusic.api.model.Song;

import java.util.ArrayList;
import java.util.Collections;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.itspartytime.adapters.SongView;

public class PlaylistAdapter extends BaseAdapter
{

	private Context mContext;
	private static ArrayList<Song> currentPlaylist = new ArrayList<Song>();

    /**
     * Creates a new PlaylistAdapter that takes the current playlist and the context from which
     *  it was created
     * @param context
     * @param playlist
     */
	public PlaylistAdapter(Context context, ArrayList<Song> playlist)
	{
		mContext = context;
		currentPlaylist = playlist;
    }

    /**
     * Getters and setters for Playlist Adapter
     */
    public void sortCurrentPlaylist(){
        Collections.sort(currentPlaylist);
    }

    public void setCurrentPlaylist(ArrayList<Song> songs)
    {
        currentPlaylist = songs;
    }
	
	@Override
	public int getCount() 
	{
		return currentPlaylist.size();
	}

	@Override
	public Object getItem(int position) 
	{
		return currentPlaylist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
    {
		return new SongView(mContext, (Song) getItem(position));
	}

}
