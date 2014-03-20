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
	private ArrayList<gmusic.api.model.Song> currentPlaylist = new ArrayList<gmusic.api.model.Song>();
	
	public PlaylistAdapter(Context context, ArrayList<gmusic.api.model.Song> playlist)
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
		SongView v = (SongView) convertView;
		if (convertView == null)
			v = new SongView(mContext, (gmusic.api.model.Song) getItem(position));
		else 
		{
			v.setCurrentSong(Party.isCurrentSong((gmusic.api.model.Song) getItem(position)));
			v.setTitle(((gmusic.api.model.Song)getItem(position)).getName());
			v.setArtist(((gmusic.api.model.Song)getItem(position)).getArtistNorm());
		}
		return v;
	}

}
