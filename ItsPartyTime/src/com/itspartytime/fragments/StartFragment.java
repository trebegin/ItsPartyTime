/**
 * StartFragment.java
 *
 * This fragment is the first fragment in the app. It gives users a choice to create a party or to
 *  join a party.
 *
 * Trent Begin, Matt Shore, Becky Torrey
 * 5/5/2014
 *
 * Variables:
 * private Button createPartyButton:        Button to Create a Party
 * private Button joinPartyButton:          Button to Join a Party
 *
 *
 * Known Faults:
 *
 *
 *
 */
package com.itspartytime.fragments;

import android.app.Fragment;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.itspartytime.PartyActivity;
import com.itspartytime.R;
import com.itspartytime.dialogs.ListDialog;

import java.util.ArrayList;
import java.util.List;

public class StartFragment extends Fragment
{
	
	private Button createPartyButton;
	private Button joinPartyButton;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		RelativeLayout mRelativeLayout = (RelativeLayout) inflater.inflate(R.layout.start_fragment_layout, container, false);
		createPartyButton = (Button) mRelativeLayout.findViewById(R.id.create_party_button);
		joinPartyButton = (Button) mRelativeLayout.findViewById(R.id.join_party_button);

		createPartyButton.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				login();
			}
		});
		
		joinPartyButton.setOnClickListener(new View.OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				openJoinParty();
			}
		});
		
		
		
		return mRelativeLayout;
	}

    // Opens the login Dialog and sets the device's host status to true
	private void login ()
	{
        PartyActivity.openLoginDialog();
        PartyActivity.setHost(true);
	}

    // Creates and inflates a list of potential parties to join
	private void openJoinParty ()
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
                PartyActivity.toaster("Connecting to: " + String.valueOf(adapterView.getItemAtPosition(i)));

                if(peers.size() >= i && i > 0)
                {
                    PartyActivity.deviceConnect((BluetoothDevice)peers.get(i));
                }

                // exit dialog
                mListDialog.dismiss();
            }
        });
        mListDialog.show(getFragmentManager(), "Test Dialog");

        PartyActivity.setHost(false);
	}

}