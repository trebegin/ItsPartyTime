package com.itspartytime;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

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
				openCreatePartyFragment();
			}
		});
		
		joinPartyButton.setOnClickListener(new View.OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				openJoinPartyFragment();
			}
		});
		
		
		
		return mRelativeLayout;
	}

	private void openCreatePartyFragment()
	{
		PartyActivity.openCreatePartyFragment(this);
        PartyActivity.setHost(true);
	}

	private void openJoinPartyFragment()
	{
		PartyActivity.openJoinPartyFragment(this);
        PartyActivity.setHost(false);
	}

}