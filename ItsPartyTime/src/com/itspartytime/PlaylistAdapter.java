package com.itspartytime;

import gmusic.api.model.Song;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class PlaylistAdapter extends BaseAdapter
{

	private Context mContext;
	private ArrayList<Song> currentPlaylist = new ArrayList<Song>();
	
	public PlaylistAdapter(Context context, ArrayList<Song> playlist)
	{
		mContext = context;
		currentPlaylist = playlist;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		SongView v = new SongView(mContext, (Song) getItem(position));
		return v;
	}

}
