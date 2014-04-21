package com.itspartytime.adapters;

import gmusic.api.model.Song;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itspartytime.R;

public class SongView extends RelativeLayout
{
	private TextView mTitle;
	private TextView mArtist;
	private ImageView mAlbumArt;
	private Button voteUpButton;
	private Button voteDownButton;

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
			}
		});
        voteUpButton.setText(Integer.toString(song.getUpVotes()));

		voteDownButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				song.addDownVote();
			}
		});
        voteDownButton.setText(Integer.toString(song.getDownVotes()));


        mTitle.setTextSize(19);
        mTitle.setTextColor(Color.BLACK);
        mTitle.setText(song.getName());

        mArtist.setTextSize(12);
        mArtist.setTextColor(Color.GRAY);
        mArtist.setText(song.getArtist());
	}

}
