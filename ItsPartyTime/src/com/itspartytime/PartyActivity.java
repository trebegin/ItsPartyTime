package com.itspartytime;

import gmusic.api.model.Song;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

public class PartyActivity extends Activity
{
	private static StartFragment mStartFragment;
	private static CreatePartyFragment mCreatePartyFragment;
	private static JoinPartyFragment mJoinPartyFragment;
	private static PlaylistViewFragment mPlaylistViewFragment;
	private static FragmentManager mFragmentManager;
	private static SelectPlaylistFragment mSelectPlaylistFragment;
	private static Fragment mFragment;

    private static Playlist mPlaylist;
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
        mPlaylist = new Playlist(mApplicationContext);
		openStartFragment(null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

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
	
	public static void openLoginDialog()
	{
		LoginDialog login = new LoginDialog();
		login.show(mFragmentManager, "login");
	}

	public static void openStartFragment(Fragment currentFragment) 
	{
		mFragment = mStartFragment;
		if(currentFragment != null)
		{
			mFragmentManager.saveFragmentInstanceState(currentFragment);
			mFragmentManager.beginTransaction().detach(currentFragment).attach(mStartFragment)
				.addToBackStack(currentFragment.getTag()).commit();
		}
		else
			mFragmentManager.beginTransaction().attach(mStartFragment).commit();
	}

	public static void openCreatePartyFragment(Fragment currentFragment) 
	{
		mFragment = mCreatePartyFragment;
		if(currentFragment != null)
		{
			mFragmentManager.saveFragmentInstanceState(currentFragment);
			mFragmentManager.beginTransaction().detach(currentFragment).attach(mCreatePartyFragment)
				.addToBackStack(currentFragment.getTag()).commit();
		}
		else
			mFragmentManager.beginTransaction().attach(mCreatePartyFragment).commit();
	}

	public static void openJoinPartyFragment(Fragment currentFragment) 
	{
		mFragment = mJoinPartyFragment;
		if(currentFragment != null)
		{
			mFragmentManager.saveFragmentInstanceState(currentFragment);
			mFragmentManager.beginTransaction().detach(currentFragment).attach(mJoinPartyFragment)
				.addToBackStack(currentFragment.getTag()).commit();
		}
		else
			mFragmentManager.beginTransaction().attach(mJoinPartyFragment).commit();
	}

	public static void openPlaylistViewFragment(Fragment currentFragment) 
	{	
		mFragment = mPlaylistViewFragment;
		if(currentFragment != null)
		{
			mFragmentManager.saveFragmentInstanceState(currentFragment);
			mFragmentManager.beginTransaction().detach(currentFragment).attach(mPlaylistViewFragment)
				.addToBackStack(currentFragment.getTag()).commit();
		}
		else
			mFragmentManager.beginTransaction().attach(mPlaylistViewFragment).commit();
	}
	
	
	public static void openSelectPlaylistDialog()
	{
		SelectPlaylistDialog mSelectPlaylistDialog = new SelectPlaylistDialog();
		mSelectPlaylistDialog.show(mFragmentManager, "SelectPlaylistDialog");
	}


	public static void notifyChange(int update_message)
    {
		mPlaylistViewFragment.notifyChange(update_message);
	}

    public static void updatePauseButton(boolean playing)
    {
        mPlaylistViewFragment.updatePauseButton(playing);
    }
	
	public static void toaster(final String message)
	{
		mFragment.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mApplicationContext, message, Toast.LENGTH_LONG).show();
            }
        });
	}

    /**
     * Getters and Setters
     * @param name
     */
    public static void setPartyName(String name)
    {
        partyName = name;
    }

    public static String getPartyName()
    {
        return partyName;
    }


    public static boolean isLoggedIn()
    {
        return loggedIn;
    }

    public static void setLoggedIn(boolean login)
    {
        loggedIn = login;
    }

    public static boolean isHost()
    {
        return isHost;
    }

    public static void setHost(boolean isHost)
    {
        PartyActivity.isHost = isHost;
    }

    public static void login(String email, String password)
    {
        mPlaylist.login(email, password);
    }
    
    public static boolean isPlaying()
    {
        return mPlaylist.isPlaying();
    }

    public static Playlist getPlaylist()
    {
        return mPlaylist;
    }
}
