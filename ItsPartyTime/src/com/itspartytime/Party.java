package com.itspartytime;

import gmusic.api.model.Song;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Party extends Activity 
{
	
	private static Playlist mPlaylist;
	private Device hostDevice;
	private DeviceController mDeviceController;
	
	private static StartFragment mStartFragment;
	private static CreatePartyFragment mCreatePartyFragment;
	private static JoinPartyFragment mJoinPartyFragment;
	private static PlaylistViewFragment mPlaylistViewFragment;
	private static FragmentManager mFragmentManager;
	private static SelectPlaylistFragment mSelectPlaylistFragment;
	private static Fragment currentFragment;
	
	private static String email;
	private static String password;
	private static String partyName;
	
	private static boolean isHost;
	private static boolean loggedIn = false;
	private static Context mApplicationContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		mApplicationContext = getApplicationContext();
		setContentView(R.layout.activity_main);
		initFragments();
		mPlaylist = new Playlist(this);
		openStartFragment();
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
	 * 		- initialized FragmentMangager
	 * 		- create fragments
	 * 		- add fragments to FragmentManager
	 * 
	 * known bugs:
	 * 		- 
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
		mFragmentManager.beginTransaction().add(R.id.fragmentFrame, mCreatePartyFragment, "CreatePartyPage")
			.detach(mStartFragment).commit();
		mFragmentManager.beginTransaction().add(R.id.fragmentFrame, mJoinPartyFragment, "JoinPartyPage")
			.detach(mCreatePartyFragment).commit();
		mFragmentManager.beginTransaction().add(R.id.fragmentFrame, mPlaylistViewFragment, "PlaylistViewPage")
			.detach(mJoinPartyFragment).commit();
		mFragmentManager.beginTransaction().detach(mPlaylistViewFragment).commit();
	}
	
	
	/**
	 * Opens login screen
	 * 
	 * preconditions:
	 * 		- Not already logged in to google
	 * 		- mFragmentManager has been initialized
	 * 
	 * parameters:
	 * 		- 
	 * 
	 * postconditions:
	 * 		- Login dialog is open and is in focus
	 *  
	 * recent changes:
	 * 		- create LoginDialog
	 * 		- show LoginDialog
	 * 
	 * known bugs:
	 * 		- 
	 * 
	 */
	public static void openLoginDialog()
	{
		LoginDialog login = new LoginDialog();
		login.show(mFragmentManager, "login");
	}
	
	/**
	 * Detaches currentFragment if not null, attaches StartFragment fragment
	 * 
	 * preconditions:
	 * 		- StartFragment is not currently attached
	 * 
	 * parameters:
	 * 		- 
	 * 
	 * postconditions:
	 * 		- StartFragment is attached
	 *  
	 * recent changes:
	 * 		- check for currentFragment null
	 * 		- attach StartFragment
	 * 		- add fragment to backstack
	 * 		- replace currentFragment parameter with this.currentFragment
	 * 		- update currentFragment
	 * 
	 * known bugs:
	 * 		- 
	 */
	public static void openStartFragment() 
	{
		if(currentFragment != null)
		{
			mFragmentManager.saveFragmentInstanceState(currentFragment);
			mFragmentManager.beginTransaction().detach(currentFragment).attach(mStartFragment)
				.addToBackStack(currentFragment.getTag()).commit();
		}
		else
			mFragmentManager.beginTransaction().attach(mStartFragment).commit();
		currentFragment = mStartFragment;
	}
	
	/**
	 * Detaches currentFragment if not null, attaches CreatePartyFragment
	 * 
	 * preconditions:
	 * 		- CreatePartyFragment is not currently attached
	 * 
	 * parameters:
	 * 		- 
	 * 
	 * postconditions:
	 * 		- CreatePartyFragment is attached
	 *  
	 * recent changes:
	 * 		- check for currentFragment null
	 * 		- attach CreatePartyFragment
	 * 		- add fragment to backstack 
	 * 		- replace currentFragment parameter with this.currentFragment
	 * 		- update currentFragment
	 * 
	 * known bugs:
	 * 		- 
	 * 
	 */
	public static void openCreatePartyFragment() 
	{
		if(currentFragment != null)
		{
			mFragmentManager.saveFragmentInstanceState(currentFragment);
			mFragmentManager.beginTransaction().detach(currentFragment).attach(mCreatePartyFragment)
				.addToBackStack(currentFragment.getTag()).commit();
		}
		else
			mFragmentManager.beginTransaction().attach(mCreatePartyFragment).commit();
		
		currentFragment = mCreatePartyFragment;
	}
	
	/**
	 * Detaches currentFragment if not null, attaches JoinPartyFragment
	 * 
	 * preconditions:
	 * 		- JoinPartyFragment is not currently attached
	 * 
	 * parameters:
	 * 		- 
	 * 
	 * postconditions:
	 * 		- JoinPartyFragment is attached
	 *  
	 * recent changes:
	 * 		- check for currentFragment null
	 * 		- attach JoinPartyFragment
	 * 		- add fragment to backstack
	 * 		- replace currentFragment parameter with this.currentFragment
	 * 		- update currentFragment
	 * 
	 * known bugs:
	 * 		- 
	 * 
	 */
	public static void openJoinPartyFragment() 
	{
		if(currentFragment != null)
		{
			mFragmentManager.saveFragmentInstanceState(currentFragment);
			mFragmentManager.beginTransaction().detach(currentFragment).attach(mJoinPartyFragment)
				.addToBackStack(currentFragment.getTag()).commit();
		}
		else
			mFragmentManager.beginTransaction().attach(mJoinPartyFragment).commit();
		
		currentFragment = mJoinPartyFragment;
	}
	
	/**
	 * Detaches currentFragment if not null, attaches PlaylistViewFragment
	 * 
	 * preconditions:
	 * 		- PlaylistViewFragment is not currently attached
	 * 
	 * parameters:
	 * 		-
	 * 
	 * postconditions:
	 * 		- PlaylistViewFragment is attached
	 *  
	 * recent changes:
	 * 		- check for currentFragment null
	 * 		- attach PlaylistViewFragment
	 * 		- add fragment to backstack
	 * 		- 
	 * 
	 * known bugs:
	 * 		- 
	 * 
	 */
	public static void openPlaylistViewFragment() 
	{	
		if(currentFragment != null)
		{
			mFragmentManager.saveFragmentInstanceState(currentFragment);
			mFragmentManager.beginTransaction().detach(currentFragment).attach(mPlaylistViewFragment)
				.addToBackStack(currentFragment.getTag()).commit();
		}
		else
			mFragmentManager.beginTransaction().attach(mPlaylistViewFragment).commit();
		
		currentFragment = mPlaylistViewFragment;
	}	
	
	/**
	 * Creates and opens SelectPlaylistDialog 
	 * 
	 * preconditions:
	 * 		- mFragmentManager is not null
	 * 
	 * parameters:
	 * 		- 
	 * 
	 * postconditions:
	 * 		- SelectPlaylistDialog is opened
	 *  
	 * recent changes:
	 * 		- create new SelectPlaylistDialog
	 * 		- show SelectPlaylistDialog
	 * 
	 * known bugs:
	 * 		- 
	 * 
	 */
	public static void openSelectPlaylistDialog()
	{
		SelectPlaylistDialog mSelectPlaylistDialog = new SelectPlaylistDialog();
		mSelectPlaylistDialog.show(mFragmentManager, "SelectPlaylistDialog");
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
	public static void pauseSong()
	{
		mPlaylist.pause();
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
	 * @param song
	 */
	public static void changeSong(Song song)
	{
		mPlaylist.changeSong(song);
	}
	
	/**
	 * Create toast based on parameter
	 * 
	 * preconditions:
	 * 		- 
	 * 
	 * parameters:
	 * 		- String message 	-> string of text to display
	 * 
	 * postconditions:
	 * 		- toast is displayed for long length
	 *  
	 * recent changes:
	 * 		- find activity and run toast on UI thread
	 * 
	 * known bugs:
	 * 		- 
	 * 
	 */
	public static void toaster(final String message)
	{
		currentFragment.getActivity().runOnUiThread(new Runnable(){
			@Override
			public void run()
			{
				Toast.makeText(mApplicationContext, message, Toast.LENGTH_LONG).show();
			}
		});
	}
	
	// call down to Playlist class
	public static void nextSong() 
	{
		mPlaylist.nextSong();
	}
	
	// call down to Playlist class
	public static void login() 
	{
		mPlaylist.login();		
	}
	
	// call down to Playlist class
	public static boolean isCurrentSong(Song song) 
	{
		return mPlaylist.isCurrentSong(song);
	}
	
	// refreshes playlist, returns song list
	public static  ArrayList<Song> getCurrentPlaylist()
	{
		mPlaylist.update();
		return mPlaylist.getSongList();
	}
	
	// call up to PlaylistViewFragment class
	public static void notifyChange() 
	{
		mPlaylistViewFragment.notifyChange();
	}
	
	// call up to PlaylistViewFragment class
	public static void updatePauseButton(boolean playing) 
	{
		mPlaylistViewFragment.updatePauseButton(playing);
	}
	
	// set party name
	public static void setPartyName(String name)
	{
		partyName = name;
	}

	// get email
	public static String getEmail() {
		return email;
	}

	// get password
	public static String getPassword() {
		return password;
	}

	// set email
	public static void setEmail(String text) {
		email = text;
	}

	// set password
	public static void setPassword(String text) {
		password = text;
		
	}
	
	// get logged in state
	public static boolean isLoggedIn() {
		return loggedIn;
	}

	// set logged in stated
	public static void setLoggedIn(boolean login) {
		loggedIn = login;
	}


	// get if host
	public static boolean isHost() {
		return isHost;
	}

	// set if host
	public static void setHost(boolean isHost) {
		Party.isHost = isHost;
	}

}
