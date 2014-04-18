package com.itspartytime.dialogs;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.itspartytime.R;

public class SelectPlaylistDialog extends DialogFragment
{
	private ArrayList<String> playlistTitles;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Call getPlaylistTitles() or something like that to populate list with correct titles
		// Fake names for testing purposes
		playlistTitles = new ArrayList<String>();
		playlistTitles.add("Playlist 1");
		playlistTitles.add("Playlist 2");
		playlistTitles.add("Playlist 3");
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.select_playlist_dialog_text);
		builder.setItems(R.array.select_playlist_titles, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
			
		});
		return builder.create();
	}

}
