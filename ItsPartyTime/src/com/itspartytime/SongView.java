package com.itspartytime;

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

public class SongView extends RelativeLayout
{
	//private gmusic.api.model.Song song;

	private TextView mTitle;
	private TextView mArtist;
//    private TextView mUpVoteCount;
//    private TextView mDownVoteCount;
	private ImageView mAlbumArt;
	private Button voteUpButton;
	private Button voteDownButton;
	private boolean isCurrentSong;
    private Context mContext;

	public SongView(Context context, final Song song) {
		super(context);
        mContext = context;
		inflate(context, R.layout.song_view_layout, this);
		mTitle = (TextView) findViewById(R.id.song_title);
		mArtist = (TextView) findViewById(R.id.song_artist);
        mAlbumArt = (ImageView) findViewById(R.id.song_album_art);
//        mUpVoteCount = (TextView) findViewById(R.id.up_vote_count);
//        mDownVoteCount = (TextView) findViewById(R.id.down_vote_count);
		voteUpButton = (Button) findViewById(R.id.vote_up_button);
		voteDownButton = (Button) findViewById(R.id.vote_down_button);
		
		voteUpButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				song.addUpVote();
                setUpVotes(song.getUpVotes());
			}
		});

		voteDownButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				song.addDownVote();
                setDownVotes(song.getDownVotes());
			}
		});

        mTitle.setTextSize(19);
        mTitle.setTextColor(Color.BLACK);

        mArtist.setTextSize(12);
        mArtist.setTextColor(Color.GRAY);

        update(song);
	}

    public void update(final Song song) {

        setTitle(song.getName());
        setArtist(song.getArtist());
        setUpVotes(song.getUpVotes());
        setDownVotes(song.getDownVotes());
//        new Thread (new Runnable() {
//            @Override
//            public void run() {
//
//        URL newurl = null;
//        Bitmap mIcon_val = null;
//        try {
//            newurl = new URL(song.getAlbumArtUrl());
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        try {
//            mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//                setAlbumArt(mIcon_val);
//
//            }
//        }).start();
//        //mAlbumArt.set(song.getAlbumArtUrl());

    }

    private void setAlbumArt(final Bitmap mIcon_val) {

        ((Activity)mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAlbumArt.setImageBitmap(mIcon_val);
                Log.d("Image", "Image found:" + mIcon_val.toString());
            }
        });
    }

    public void setTitle(String title)
    {
		mTitle.setText(title);
	}

	public void setArtist(String artist)
    {
		mArtist.setText(artist);
	}

	public void setIsCurrentSong(boolean isCurrentSong){
		this.isCurrentSong = isCurrentSong;
		if(isCurrentSong)
			mTitle.setTypeface(null, Typeface.BOLD);
		else
			mTitle.setTypeface(null, Typeface.NORMAL);
	}

	public void setUpVotes(int upVotes)
    {
		voteUpButton.setText(Integer.toString(upVotes));
	}

	public void setDownVotes(int downVotes)
    {
		voteDownButton.setText(Integer.toString(downVotes));
	}
	
	
	
}
