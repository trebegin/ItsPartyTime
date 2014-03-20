package com.itspartytime;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SongView extends LinearLayout
{
	//private gmusic.api.model.Song song;

	private TextView mTitle;
	private TextView mArtist;
	private ImageView mAlbumArt;
	private boolean isCurrentSong;

	public SongView(Context context, gmusic.api.model.Song song) {
		super(context);
		setOrientation(VERTICAL);
		mTitle = new TextView(context);
		mArtist = new TextView(context);
		mAlbumArt = new ImageView(context);
		
		String title = song.getName();
		mTitle.setText("Title: " + title);
		mTitle.setTextSize(19);
		mTitle.setTextColor(Color.BLACK);
		isCurrentSong = Party.isCurrentSong(song);
		if(isCurrentSong)
			mTitle.setTypeface(null, Typeface.BOLD);
		else
			mTitle.setTypeface(null, Typeface.NORMAL);
		
		String artist = song.getArtistNorm();
		mArtist.setText("Artist: " + artist);
		mArtist.setTextSize(12);
		mArtist.setTextColor(Color.GRAY);
		
		 addView(mTitle, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
	     addView(mArtist, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
	}

	public String getTitle() {
		return mTitle.toString();
	}

	public void setTitle(String title) {
		mTitle.setText("Title: " + title);
	}

	public String getArtist() {
		return mArtist.toString();
	}

	public void setArtist(String artist) {
		mArtist.setText("Artist: " + artist);
	}

	public void setCurrentSong(boolean isCurrentSong){
		this.isCurrentSong = isCurrentSong;
		if(isCurrentSong)
			mTitle.setTypeface(null, Typeface.BOLD);
		else
			mTitle.setTypeface(null, Typeface.NORMAL);
	}
	
}
