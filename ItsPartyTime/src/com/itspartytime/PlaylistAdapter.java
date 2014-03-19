package com.itspartytime;

import gmusic.api.model.Song;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class PlaylistAdapter extends BaseAdapter
{

	private Context mContext;
	private List<gmusic.api.model.Song> currentPlaylist = new ArrayList<gmusic.api.model.Song>();
	
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
		if (convertView == null)
			convertView = new SongView(mContext, (Song) getItem(position));
		else 
		{
			((SongView) convertView).setTitle(((Song)getItem(position)).getTitle());
			((SongView) convertView).setArtist(((Song)getItem(position)).getArtist());
		}
		return convertView;
	}

}
