package com.itspartytime;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;

import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
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

    private final IntentFilter mIntentFilter = new IntentFilter();

    private static Playlist mPlaylist;
    private static String partyName;
    private static boolean isHost;
    private static boolean loggedIn = false;

    private static ProgressDialog progress;

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
    private final static int RECEIVE_VOTE = 6;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		mApplicationContext = getApplicationContext();
		setContentView(R.layout.activity_main);
		initFragments();
        mPlaylist = new Playlist();

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null)
        {
            toaster("Bluetooth is not Supported");
        }

        if (!mBluetoothAdapter.isEnabled())
        {
            Intent enableBtIntent = new Intent((BluetoothAdapter.ACTION_REQUEST_ENABLE));
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        devices = new ArrayList<BluetoothDevice>();
        songs = new ArrayList<Song>();
        mHandler = new Handler()
        {
            public void handleMessage(Message msg)
            {
//                String byteString = String.valueOf(bytes);
//                StringTokenizer tokens = new StringTokenizer(byteString, ":");
//                String msgType = tokens.nextToken();

                switch (msg.what)
                {

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

                    case REQUEST_UPDATE:
                    {
                        updateSongList();
                        break;
                    }

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch ((item.getItemId()))
        {
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
    protected void onDestroy() {
        if(mPlaylist != null)
            mPlaylist.destroy();
        super.onDestroy();
    }

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
	
	public static void openLoginDialog()
	{
        LoginDialog login = new LoginDialog();
        login.show(mFragmentManager, "login");
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

    public static ArrayList<BluetoothDevice> discoverDevices()
    {
        return mBluetoothHelper.discover();
    }

    public static void deviceConnect(BluetoothDevice device)
    {
        mBluetoothHelper.connect(device);
    }

    public static ArrayList<BluetoothDevice> getDevices()
    {
        return devices;
    }

    public static void bluetoothAccept()
    {
        mBluetoothHelper.accept();
    }

    public static void send(String msg)
    {
        try
        {
            mBluetoothHelper.send(msg.getBytes("UTF-8"), MESSAGE_READ);
        }
        catch (UnsupportedEncodingException e) {}
    }

    public static void requestPlaylist(View view)
    {
        String str = " ";
        try
        {
            mBluetoothHelper.send(str.getBytes("UTF-8"), REQUEST_UPDATE);
        }
        catch (UnsupportedEncodingException e) {}
    }

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

    public static void updateSongList()
    {
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
                Bundle b = new Bundle();
                b.putString("title", playlistPackage.get(i).getTitle());
                b.putString("artist", playlistPackage.get(i).getArtist());
                b.putString("art", playlistPackage.get(i).getAlbumArtUrl());

                out.writeObject(b);
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
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }


}




