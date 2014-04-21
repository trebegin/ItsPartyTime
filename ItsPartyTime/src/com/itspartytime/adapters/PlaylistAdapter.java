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
	
	public PlaylistAdapter(Context context, ArrayList<Song> playlist)
	{
		mContext = context;
		currentPlaylist = playlist;
    }

    public void sortCurrentPlaylist(){
        Collections.sort(currentPlaylist);
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
