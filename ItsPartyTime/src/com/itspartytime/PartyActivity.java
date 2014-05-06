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
 * private static StartFragment mStartFragment:                 Fragment for the Start Page
 * private static PlaylistViewFragment mPlaylistViewFragment:   Fragment for Playlist Page
 * private static FragmentManager mFragmentManager:             Manages Fragments
 * private static Fragment currentFragment:                     Stores the Current Fragment
 *
 * private static Playlist mPlaylist:       The Current Playlist
 * private static String partyName:         The Party Name
 * private static boolean isHost:           Is the User a Host
 * private static boolean loggedIn:         Is the Host Logged in to Google Services
 *
 * private static ArrayList<Song> songs:     An Array of Songs used for Receiving and sending
 *
 * private static BluetoothHelper mBluetoothHelper:     Handler for Bluetooth Connection
 * private static ArrayList<BluetoothDevice> devices:   An Array list of paired bluetooth Devices
 * private Handler mHandler:                            Handles the receiving of messages
 *
 * private static Context mApplicationContext:          The current context of the application
 *
 * private final static int REQUEST_ENABLE_BT = 1:      Enabling Bluetooth
 * private final static int MESSAGE_READ = 2:           Send a message to toast
 * private final static int REQUEST_UPDATE = 3:         Request for a playlist update
 * private final static int RECEIVE_UPDATE = 4:         Receiving an update
 * private final static int VOTE_SONG = 5:              Voting a song up or down
 *
 *
 * Known Faults:
 *
 *
 *
 *
 */




package com.itspartytime;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Menu;

import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.itspartytime.bluetooth.BluetoothHelper;
import com.itspartytime.dialogs.LoginDialog;

import com.itspartytime.fragments.PlaylistViewFragment;
import com.itspartytime.fragments.StartFragment;
import com.itspartytime.helpers.Playlist;

import gmusic.api.model.Song;


public class PartyActivity extends Activity
{
	private static StartFragment mStartFragment;
	private static PlaylistViewFragment mPlaylistViewFragment;
	private static FragmentManager mFragmentManager;
	private static Fragment currentFragment;

    private static Playlist mPlaylist;
    private static String partyName;
    private static boolean isHost;
    private static boolean loggedIn = false;

    private static ArrayList<Song> songs;

    private static BluetoothHelper mBluetoothHelper;
    private static ArrayList<BluetoothDevice> devices;
    private Handler mHandler;

	private static Context mApplicationContext;

    private final static int REQUEST_ENABLE_BT = 1;
    private final static int MESSAGE_READ = 2;
    private final static int REQUEST_UPDATE = 3;
    private final static int RECEIVE_UPDATE = 4;
    private final static int VOTE_SONG = 5;

    private static Thread SendingThread;

	@Override
    /**
     * When the Application is Created:
     *
     * Initializes private variables
     */
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		mApplicationContext = getApplicationContext();
		setContentView(R.layout.activity_main);
		initFragments();
        mPlaylist = new Playlist();

        // Checks if phone has bluetooth capabilities, if not exits app
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null)
        {
            toaster("Bluetooth is not Supported");
            System.exit(0);
        }

        // Checks if  Bluetooth is enabled
        if (!mBluetoothAdapter.isEnabled())
        {
            Intent enableBtIntent = new Intent((BluetoothAdapter.ACTION_REQUEST_ENABLE));
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        devices = new ArrayList<BluetoothDevice>();
        songs = new ArrayList<Song>();

        // Creates Message Bundle Handler for Bluetooth Communication
        mHandler = new Handler()
        {
            public void handleMessage(Message msg)
            {

                switch (msg.what)
                {

                    // Sending a message over toast
                    case MESSAGE_READ:
                    {
                        String data = "";
                        try
                        {
                            data = new String((byte[]) msg.obj, "UTF-8");
                        }
                        catch (UnsupportedEncodingException e) {}

                       toaster(data);

                        break;
                    }

                    // Another phone has requested an Update
                    case REQUEST_UPDATE:
                    {
                        updateSongList();
                        break;
                    }

                    // This phone is receiving an update
                    case RECEIVE_UPDATE:
                    {
                        try
                        {
                            ByteArrayInputStream bis = new ByteArrayInputStream((byte []) msg.obj);
                            ObjectInputStream in = new ObjectInputStream(bis);
                            Object o = in.readObject();
                            if(o instanceof String)
                            {
                                if(songs.get(0) != null)
                                {
                                    mPlaylist.setCurrentSong(songs.get(0));
                                    songs.remove(0);
                                    mPlaylist.setCurrentSongList((ArrayList<Song>) songs.clone());
                                }
                                else
                                {
                                    toaster("The Party Hasn't Started Yet!");
                                    songs.remove(0);
                                    mPlaylist.setCurrentSongList((ArrayList<Song>) songs.clone());
                                }
                                songs.clear();
                                break;
                            }
                            else
                            {
                                songs.add((Song) o);
                            }
                            in.close();
                        }
                        catch (IOException e) {e.printStackTrace();}
                        catch (ClassNotFoundException f) {}

                        break;
                    }

                    // Either a guest of host is voting a song up or down
                    case VOTE_SONG:
                    {
                        try
                        {
                            ByteArrayInputStream bis = new ByteArrayInputStream((byte []) msg.obj);
                            ObjectInputStream in = new ObjectInputStream(bis);
                            Song inSong = (Song) in.readObject();
                            Song voteSong = mPlaylist.findSongByName(inSong.getName());

                            if(voteSong != null)
                            {
                                voteSong.setUpVotes(inSong.getUpVotes());
                                voteSong.setDownVotes(inSong.getDownVotes());
                                if(isHost())
                                    sendVote(voteSong);
                            }
                            in.close();
                        }
                        catch (IOException e) {toaster("Receive IO Exception"); Log.d("IOEXCEPTION", e.toString()); e.printStackTrace();}
                        catch (ClassNotFoundException f) {}
                        break;
                    }

                }
            }
        };

        mBluetoothHelper = new BluetoothHelper(mBluetoothAdapter, mHandler);
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
     * When a button is pushed on the action bar, handle based on which button is pressed
     * @param item:     The button that was pushed
     * @return:         Boolean if the event was handled or not
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch ((item.getItemId()))
        {
            // Refresh Button for Guests
            case(R.id.action_refresh):
            {
                if(!isHost)
                {
                    songs.clear();
                    requestPlaylist(null);
                    toaster("Requesting Playlist");
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    /**
     * If the Activity is destroyed, destroy the current playlist
     */
    protected void onDestroy()
    {
        if(mPlaylist != null)
            mPlaylist.destroy();
        super.onDestroy();
    }

    /**
     * Function for initializing all fragments
     */
	private void initFragments()
	{
		mFragmentManager = getFragmentManager();
		mStartFragment = new StartFragment();
		mPlaylistViewFragment = new PlaylistViewFragment();
		
		mFragmentManager.beginTransaction().add(R.id.fragmentFrame, mStartFragment, "StartPage").commit();
		mFragmentManager.beginTransaction().add(R.id.fragmentFrame, mPlaylistViewFragment, "PlaylistViewPage")
			.detach(mStartFragment).commit();
		mFragmentManager.beginTransaction().detach(mPlaylistViewFragment).commit();
	}

    /**
     * Opens the Login Dialog
     */
	public static void openLoginDialog()
	{
        LoginDialog login = new LoginDialog();
        login.show(mFragmentManager, "login");
	}

    /**
     * Opens the Start Page Fragment
     * @param currentFragment
     */
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

    /**
     * Opens the Playlist view fragment. If Device is hosting, begins to accept bluetooth connections,
     * otherwise it requests the playlist from the host
     *
     * @param currentFragment
     */
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
        if(isHost())
            bluetoothAccept();
        else
            requestPlaylist(null);
	}

    /**
     * Signals the Playlist Fragment that a change has been made from the UI
     * @param updateMessage:    Type of Update
     */
	public static void notifyChange(int updateMessage)
    {
		mPlaylistViewFragment.notifyChange(updateMessage);
	}


    /**
     * Toasts a message to the device
     * @param message
     */
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
     * Invokes the Bluetooth helper to return a list of paired devices
     * @return ArrayList of Bluetooth Devices
     */
    public static ArrayList<BluetoothDevice> discoverDevices()
    {
        return mBluetoothHelper.discover();
    }

    /**
     * Invokes the Bluetooth helper to connect to a new Bluetooth Device
     * @param device
     */
    public static void deviceConnect(BluetoothDevice device)
    {
        mBluetoothHelper.connect(device);
    }

    /**
     * Invokes the Bluetooth helper to begin accepting connections for other devices
     */
    public static void bluetoothAccept()
    {
        mBluetoothHelper.accept();
    }

    /**
     * Send a message to connected devices through bluetooth
     * @param msg
     */
    public static void send(String msg)
    {
        try
        {
            mBluetoothHelper.send(msg.getBytes("UTF-8"), MESSAGE_READ);
        }
        catch (UnsupportedEncodingException e) {}
    }

    /**
     * Called by guests to request the latest version of the playlist
     *
     * @param view:     Refresh Button
     */
    public static void requestPlaylist(View view)
    {
        String str = " ";
        try
        {
            mBluetoothHelper.send(str.getBytes("UTF-8"), REQUEST_UPDATE);
        }
        catch (UnsupportedEncodingException e) {}
    }

    /**
     * Called by either host or guest to signal a voted song, sends updated song
     * @param song
     */
    public static void sendVote(Song song)
    {
        try
        {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream out = new ObjectOutputStream(bos);
                out.flush();
                bos.flush();
                out.writeObject(song);
                byte[] data = bos.toByteArray();
                mBluetoothHelper.send(bos.toByteArray(), VOTE_SONG);
                out.close();
                bos.close();
        }
        catch (IOException e) {toaster("Request IO Exception"); e.printStackTrace();}
    }

    /**
     *  Called by the host device to create an updated playlist and send to guests
     */
    public static void updateSongList()
    {
        if(SendingThread == null || !SendingThread.isAlive())
        {
            SendingThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try
                    {
                        if(!loggedIn)
                        {
                            return;
                        }
                        ArrayList<Song> playlistPackage = (ArrayList<Song>) mPlaylist.getCurrentSongList().clone();
                        playlistPackage.add(0, mPlaylist.getCurrentSong());

                        int packageSize = playlistPackage.size();
                        for(int i = 0; i < packageSize; i++)
                        {
                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            ObjectOutputStream out = new ObjectOutputStream(bos);
                            out.writeObject(playlistPackage.get(i));
                            byte[] data = bos.toByteArray();
                            mBluetoothHelper.send(data, RECEIVE_UPDATE);
                            out.flush();
                            bos.flush();
                            out.close();
                            bos.close();

                        }

                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        ObjectOutputStream out = new ObjectOutputStream(bos);

                        String s = "done";
                        out.writeObject(s);
                        byte[] data = bos.toByteArray();
                        mBluetoothHelper.send(bos.toByteArray(), RECEIVE_UPDATE);
                        out.flush();
                        bos.flush();
                        out.close();
                        bos.close();
                    }
                    catch (IOException e) {toaster("Request IO Exception"); e.printStackTrace();}
                }
            });
            SendingThread.start();
        }

    }

    /**
     * Getters and Setters for local variables of the Activity
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
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }


}




