package com.itspartytime.fragments;

import android.app.Fragment;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.itspartytime.dialogs.ListDialog;
import com.itspartytime.PartyActivity;
import com.itspartytime.R;

import java.util.ArrayList;
import java.util.List;

public class JoinPartyFragment extends Fragment
{
	private Button selectPartyButton;
	private EditText userNameField;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		RelativeLayout mRelativeLayout = (RelativeLayout) inflater.inflate(R.layout.join_party_fragment_layout, container, false);
		selectPartyButton = (Button) mRelativeLayout.findViewById(R.id.select_party_button);

        //Needs to be conditional on correct party name
		selectPartyButton.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
                // make ArrayList to pass ListDialog
                ArrayList<String> devices = new ArrayList<String>();

                final List peers = PartyActivity.discoverDevices();

                for(int i = 0; i < peers.size(); i++)
                {
                    BluetoothDevice currentPeer = (BluetoothDevice) peers.get(i);
                    devices.add(currentPeer.getName());
                }

                // initialize ListDialog, must be final if you want to access dialog in OnItemClickListener as seen below
				final ListDialog mListDialog = new ListDialog();
                // create ListDialog, takes an ArrayList<String> to display, a string for the title, and an OnItemClickListener
                mListDialog.createDialog(devices, "Select a Party", new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                    {
                        // Toasts string name
                        PartyActivity.toaster(String.valueOf(adapterView.getItemAtPosition(i)));

                        if(peers.size() >= i && i > 0)
                        {
                            PartyActivity.deviceConnect((BluetoothDevice)peers.get(i));
                        }

                        // exit dialog
                        mListDialog.dismiss();
                        openPlaylistViewFragment();
                    }
                });
                mListDialog.show(getFragmentManager(), "Test Dialog");

			}
		});
		
		return mRelativeLayout;
	}

	/**
	 * Creates and moves to PlaylistViewPage View, called if party has been connected to
	 * 
	 * preconditions:
	 * 		- JoinPartyPage fragment is visible
	 * 		- Playlist holds a non-null song list
	 * 
	 * parameters:
	 * 		- none
	 * 
	 * postconditions:
	 * 		- JoinPartyPage fragment is destroyed
	 * 		- PlaylistViewPage fragment is created and visible 
	 * 
	 * recent changes:
	 * 		- 
	 * 
	 * known bugs:
	 * 		- 
	 */
	private void openPlaylistViewFragment()
	{
		PartyActivity.openPlaylistViewFragment(this);
	}
	
	/**
	 * Connects to the host device to receive party information, called by connectButton
	 * 
	 * preconditions:
	 * 		- JoinPartyPage fragment is visible
	 * 
	 * parameters:
	 * 		- String partyName 	-> title of the party to search
	 * 
	 * postconditions:
	 * 		- returns 1 if party was found, 0 if party does not exist
	 * 		- PartyActivity is updated with found party info if party was found
	 * 
	 * recent changes:
	 * 		- 
	 * 
	 * known bugs:
	 * 		- 
	 * 
	 * @param partyName
	 */
	private int connectToParty(String partyName)
	{
		return 1;
	}
}
