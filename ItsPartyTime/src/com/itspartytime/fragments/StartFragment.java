/**
 * PartyActivity.java
 *
 * This class is responsible for controlling the UI and logically linking other classes together.
 *
 * Trent Begin, Matt Shore, Becky Torrey
 * 5/5/2014
 *
 * Variables:
 *
 *
 *
 * Known Faults:
 *
 * The name of the code artifact
 A brief description of what the code artifact does
 The programmer’s name
 The date the code artifact was coded
 The date the code artifact was approved
 The name of the person who approved the code artifact
 The arguments of the code artifact
 A list of the name of each variable of the code artifact, preferably in alphabetical
 order, and a brief description of its use
 The names of any files accessed by this code artifact
 The names of any files changed by this code artifact
 Input–output, if any
 Error-handling capabilities
 The name of the file containing test data (to be used later for regression testing)
 A list of each modification made to the code artifact, the date the modification was
 made, and who approved the modification
 Any known faults
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

	private void login ()
	{
        PartyActivity.openLoginDialog();
        PartyActivity.setHost(true);
	}

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