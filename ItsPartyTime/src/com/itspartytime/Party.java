package com.itspartytime;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentManager.BackStackEntry;
import android.os.Bundle;
import android.view.Menu;

public class Party extends Activity 
{
	
	private Playlist mPlaylist;
	private String partyName;
	private Device hostDevice;
	private boolean isHost;
	private DeviceController mDeviceController;
	private static StartFragment mStartFragment;
	private static CreatePartyFragment mCreatePartyFragment;
	private static JoinPartyFragment mJoinPartyFragment;
	private static PlaylistViewFragment mPlaylistViewFragment;
	private static FragmentManager mFragmentManager;
	private static SelectPlaylistFragment mSelectPlaylistFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initFragments();
		openStartFragment(null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/**
	 * Initializes fragments and fragment manager
	 * 
	 * preconditions:
	 * 		- fragments have not already been created
	 * 
	 * parameters:
	 * 		- none
	 * 
	 * postconditions:
	 * 		- fragments have been created and stored
	 *  
	 * recent changes:
	 * 		- initialized FragmentMangager (Becky)
	 * 		- created fragments (Becky)
	 * 
	 * known bugs:
	 * 		- 
	 * 
	 * 
	 */
	private void initFragments()
	{
		mFragmentManager = getFragmentManager();
		mStartFragment = new StartFragment();
		mCreatePartyFragment = new CreatePartyFragment();
		mJoinPartyFragment = new JoinPartyFragment();
		mPlaylistViewFragment = new PlaylistViewFragment();
		mSelectPlaylistFragment = new SelectPlaylistFragment();
		
		
		mFragmentManager.beginTransaction().add(R.id.fragmentFrame, mStartFragment, "StartPage").commit();
		mFragmentManager.beginTransaction().add(R.id.fragmentFrame, mCreatePartyFragment, "CreatePartyPage").commit();
		mFragmentManager.beginTransaction().add(R.id.fragmentFrame, mJoinPartyFragment, "JoinPartyPage").commit();
		mFragmentManager.beginTransaction().add(R.id.fragmentFrame, mPlaylistViewFragment, "PlaylistViewPage").commit();
	}
	
	/**
	 * Detaches currentFragment if not null, attaches StartFragment fragment
	 * 
	 * preconditions:
	 * 		- StartFragment is not currently attached
	 * 
	 * parameters:
	 * 		- Fragment currentFragment	-> fragment that is currently attached
	 * 
	 * postconditions:
	 * 		- StartFragment is attached
	 *  
	 * recent changes:
	 * 		- check for currentFragment null (Becky)
	 * 		- attach StartFragment (Becky)
	 * 		- add fragment to backstack (Becky)
	 * 
	 * known bugs:
	 * 		- 
	 * 
	 * @param currentFragment
	 */
	public static void openStartFragment(Fragment currentFragment) 
	{
		if(currentFragment != null)
		{
			mFragmentManager.saveFragmentInstanceState(currentFragment);
			mFragmentManager.beginTransaction().detach(currentFragment).attach(mStartFragment)
				.addToBackStack(currentFragment.getTag()).commit();
		}
		else
			mFragmentManager.beginTransaction().attach(mStartFragment).commit();
	}
	
	/**
	 * Detaches currentFragment if not null, attaches CreatePartyFragment
	 * 
	 * preconditions:
	 * 		- CreatePartyFragment is not currently attached
	 * 
	 * parameters:
	 * 		- Fragment currentFragment	-> fragment that is currently attached
	 * 
	 * postconditions:
	 * 		- CreatePartyFragment is attached
	 *  
	 * recent changes:
	 * 		- check for currentFragment null (Becky)
	 * 		- attach CreatePartyFragment (Becky)
	 * 		- add fragment to backstack (Becky)
	 * 
	 * known bugs:
	 * 		- 
	 * 
	 * @param currentFragment
	 */
	public static void openCreatePartyFragment(Fragment currentFragment) 
	{
		if(currentFragment != null)
		{
			mFragmentManager.saveFragmentInstanceState(currentFragment);
			mFragmentManager.beginTransaction().detach(currentFragment).attach(mCreatePartyFragment)
				.addToBackStack(currentFragment.getTag()).commit();
		}
		else
			mFragmentManager.beginTransaction().attach(mCreatePartyFragment).commit();
	}
	
	/**
	 * Detaches currentFragment if not null, attaches JoinPartyFragment
	 * 
	 * preconditions:
	 * 		- JoinPartyFragment is not currently attached
	 * 
	 * parameters:
	 * 		- Fragment currentFragment	-> fragment that is currently attached
	 * 
	 * postconditions:
	 * 		- JoinPartyFragment is attached
	 *  
	 * recent changes:
	 * 		- check for currentFragment null (Becky)
	 * 		- attach JoinPartyFragment (Becky)
	 * 		- add fragment to backstack (Becky)
	 * 
	 * known bugs:
	 * 		- 
	 * 
	 * @param currentFragment
	 */
	public static void openJoinPartyFragment(Fragment currentFragment) 
	{
		if(currentFragment != null)
		{
			mFragmentManager.saveFragmentInstanceState(currentFragment);
			mFragmentManager.beginTransaction().detach(currentFragment).attach(mJoinPartyFragment)
				.addToBackStack(currentFragment.getTag()).commit();
		}
		else
			mFragmentManager.beginTransaction().attach(mJoinPartyFragment).commit();
	}
	
	/**
	 * Detaches currentFragment if not null, attaches PlaylistViewFragment
	 * 
	 * preconditions:
	 * 		- PlaylistViewFragment is not currently attached
	 * 
	 * parameters:
	 * 		- Fragment currentFragment	-> fragment that is currently attached
	 * 
	 * postconditions:
	 * 		- PlaylistViewFragment is attached
	 *  
	 * recent changes:
	 * 		- check for currentFragment null (Becky)
	 * 		- attach PlaylistViewFragment (Becky)
	 * 		- add fragment to backstack (Becky)
	 * 
	 * known bugs:
	 * 		- 
	 * 
	 * @param currentFragment
	 */
	public static void openPlaylistViewFragment(Fragment currentFragment) 
	{
		if(currentFragment != null)
		{
			mFragmentManager.saveFragmentInstanceState(currentFragment);
			mFragmentManager.beginTransaction().detach(currentFragment).attach(mPlaylistViewFragment)
				.addToBackStack(currentFragment.getTag()).commit();
		}
		else
			mFragmentManager.beginTransaction().attach(mPlaylistViewFragment).commit();
	}
	
	/**
	 * Changes the vote counts in 'song'
	 * 
	 * preconditions:
	 * 		- parameter vote is a 1 or 0
	 * 		- parameter song is non-null and exists in mPlaylist
	 * 
	 * parameters:
	 * 		- int vote	-> 1 indicates upvote, 0 indicates downvote for song
	 * 		- Song song	-> song is the object that should be changed
	 * 
	 * postconditions:
	 * 		- either songUpVotes or songDownVotes is changed in song
	 * 		- mPlaylist is reordered based on change in song
	 * 		- PlaylistViewPage fragment is updated
	 * 		- if device is not host, vote is sent to host
	 * 
	 * recent changes:
	 * 		-
	 * 
	 * known bugs:
	 * 		-
	 * 
	 * @param vote
	 * @param song
	 */
	public void vote(int vote, Song song)
	{
		
	}
	
	/**
	 * Pause/play current song
	 *
	 * preconditions:
	 * 		- device is the host device
	 * 		- there is a song that is playing or has been paused
	 * 
	 * parameters:
	 * 		- none
	 * 
	 * postconditions:
	 * 		- If there is a song playing, song is paused.
	 * 		- If there is a song paused, song is resumed
	 * 
	 * recent changes:
	 * 		-
	 * 
	 * known bugs:
	 * 		-
	 */
	public void pauseSong()
	{
		
	}
	
	/**
	 * Changes current song to the new song
	 * 
	 * preconditions:
	 * 		- parameter newSong is non-null and exists in mPlaylist 
	 * 
	 * parameters:
	 * 		- Song newSong	-> song to be started
	 * 
	 * postconditions:
	 * 		- newSong is playing
	 * 		- mPlaylist order is updated
	 * 		- PlaylistViewPage fragment is updated if visible
	 * 
	 * recent changes:
	 * 		-
	 * 
	 * known bugs:
	 * 		-
	 * 
	 * @param newSong
	 */
	public void changeSong(Song newSong)
	{
		
	}
}
