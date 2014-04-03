package com.itspartytime;

import gmusic.api.model.Song;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SongView extends RelativeLayout
{
	//private gmusic.api.model.Song song;

	private TextView mTitle;
	private TextView mArtist;
	private ImageView mAlbumArt;
	private Button voteUpButton;
	private Button voteDownButton;
	private boolean isCurrentSong;

	public SongView(Context context, final Song song) {
		super(context);
		inflate(context, R.layout.song_view_layout, this);
		mTitle = (TextView) findViewById(R.id.song_title);
		mArtist = (TextView) findViewById(R.id.song_artist);
		voteUpButton = (Button) findViewById(R.id.vote_up_button);
		voteDownButton = (Button) findViewById(R.id.vote_down_button);
		
		voteUpButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				song.addUpVote();
				voteUpButton.setText("Vote Up (" + song.getUpVotes() + ")");
				
			}
		});
		
		voteDownButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				song.addDownVote();
				voteDownButton.setText("Vote Down (" + song.getDownVotes() + ")");
			}
		});
		
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

	public void setUpVotes(int upVotes) {
		voteUpButton.setText("Vote Up (" + upVotes + ")");
	}

	public void setDownVotes(int downVotes) {
		voteDownButton.setText("Vote Down (" + downVotes + ")");
		
	}
	
	
	
}
