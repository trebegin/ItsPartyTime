package com.itspartytime;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SongView extends LinearLayout
{
	//private gmusic.api.model.Song song;

	private TextView mTitle;
	private TextView mArtist;
	private ImageView mAlbumArt;

	public SongView(Context context, gmusic.api.model.Song song) {
		super(context);
		setOrientation(VERTICAL);
		mTitle = new TextView(context);
		mArtist = new TextView(context);
		mAlbumArt = new ImageView(context);
		
		String title = song.getTitle();
		mTitle.setText("Title: " + title);
		mTitle.setTextSize(19);
		mTitle.setTextColor(Color.BLACK);
		
		String artist = song.getArtist();
		mArtist.setText("Artist: " + artist);
		mArtist.setTextSize(12);
		
		 addView(mTitle, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	     addView(mArtist, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	}

	public String getTitle() {
		return mTitle.toString();
	}

	public void setTitle(String title) {
		mTitle.setText(title);
	}

	public String getArtist() {
		return mArtist.toString();
	}

	public void setArtist(String artist) {
		mArtist.setText(artist);
	}


}
