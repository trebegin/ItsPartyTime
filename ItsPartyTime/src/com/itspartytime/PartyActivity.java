package com.itspartytime;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class PartyActivity extends Activity
{
	private static StartFragment mStartFragment;
	private static CreatePartyFragment mCreatePartyFragment;
	private static JoinPartyFragment mJoinPartyFragment;
	private static PlaylistViewFragment mPlaylistViewFragment;
	private static FragmentManager mFragmentManager;
	private static Fragment currentFragment;

    private final IntentFilter mIntentFilter = new IntentFilter();
    private static WifiP2pManager.Channel mChannel;
    private static WifiP2pManager mManager;
    private static WiFiDirectBroadcastReceiver mReceiver;
    private static WifiP2pManager.PeerListListener mListener;
    private static List peers = new ArrayList();
    private static WifiP2pConfig config;

    private static Playlist mPlaylist;
    private static String partyName;
    private static boolean isHost;
    private static boolean loggedIn = false;
    private static boolean loginAttempted = false;

    private static ProgressDialog progress;

	private static Context mApplicationContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		mApplicationContext = getApplicationContext();
		setContentView(R.layout.activity_main);
		initFragments();
        mPlaylist = new Playlist();

        //  Indicates a change in the Wi-Fi P2P status.
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);

        // Indicates a change in the list of available peers.
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);

        // Indicates the state of Wi-Fi P2P connectivity has changed.
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);

        // Indicates this device's details have changed.
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        // Registers the receiver
        registerReceiver(mReceiver, mIntentFilter);

        // Gets a list of peers
        mListener = new WifiP2pManager.PeerListListener()
        {
            @Override
            public void onPeersAvailable (WifiP2pDeviceList peerList)
            {
                // Clears the array list and adds the list of peers
                peers.clear();
                peers.addAll(peerList.getDeviceList());
                toaster("Peers Added");

            }
        };

        // Three most important parts of the Wifi communication
        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this, getMainLooper(), null);
        mReceiver = new WiFiDirectBroadcastReceiver(mManager, mChannel, this);

		openStartFragment(null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


    @Override
    protected void onDestroy() {
        if(mPlaylist != null)
            mPlaylist.destroy();
        super.onDestroy();
    }

	private void initFragments()
	{
		mFragmentManager = getFragmentManager();
		mStartFragment = new StartFragment();
		mCreatePartyFragment = new CreatePartyFragment();
		mJoinPartyFragment = new JoinPartyFragment();
		mPlaylistViewFragment = new PlaylistViewFragment();
		
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
        if(loginAttempted || loggedIn) toaster("Already logging in...");
        else
        {
            loginAttempted = true;
		    LoginDialog login = new LoginDialog();
		    login.show(mFragmentManager, "login");
        }
	}

	public static void openStartFragment(Fragment currentFragment) 
	{
		PartyActivity.currentFragment = mStartFragment;
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
		PartyActivity.currentFragment = mCreatePartyFragment;
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
		PartyActivity.currentFragment = mJoinPartyFragment;
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
		PartyActivity.currentFragment = mPlaylistViewFragment;
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


	public static void notifyChange(int updateMessage)
    {
		mPlaylistViewFragment.notifyChange(updateMessage);
	}
	
	public static void toaster(final String message)
	{
		currentFragment.getActivity().runOnUiThread(new Runnable()
        {
            @Override
            public void run ()
            {
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

    @Override
    protected void onResume()
    {
        super.onResume();
        registerReceiver(mReceiver, mIntentFilter);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        unregisterReceiver(mReceiver);
    }

    public static void discoverPeers()
    {
        mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener()
        {
            @Override
            public void onSuccess()
            {
                mManager.requestPeers(mChannel, mListener);
            }

            @Override
            public void onFailure(int reasonCode)
            {
                String output = "null";
                switch (reasonCode)
                {
                    case WifiP2pManager.NO_SERVICE_REQUESTS:
                        output = "No Service Requests";
                    case WifiP2pManager.P2P_UNSUPPORTED:
                        output = "P2P Unsupported";
                }
                toaster(output);
            }
        });
    }

    public static List getPeers()
    {
        return peers;
    }

    // Also used for a button
    public static void peerConnect(WifiP2pDevice peer)
    {
        config = new WifiP2pConfig();
        config.deviceAddress = peer.deviceAddress;
        mManager.connect(mChannel, config, new WifiP2pManager.ActionListener()
        {
            @Override
            public void onSuccess()
            {
               toaster("Connected to Device");
            }

            @Override
            public void onFailure(int reason)
            {
               toaster("Failed for Failure: " + reason);
            }
        });
    }

    public static void startProgress(String str1, String str2)
    {
        if(progress != null && progress.isShowing())
            progress.dismiss();

        ProgressDialog.show(currentFragment.getActivity(), str1, str2, true, true, new DialogInterface.OnCancelListener()
        {

            @Override
            public void onCancel(DialogInterface dialog)
            {

            }
        });

    }
}




