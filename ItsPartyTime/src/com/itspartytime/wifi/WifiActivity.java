package com.itspartytime.wifi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.itspartytime.R;

import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;

/**
 * Works but only when this module is the main activity. All the code is here that should connect a device to another.
 */

public class WifiActivity extends Activity
{
    private final IntentFilter mIntentFilter = new IntentFilter();
    private static WifiP2pManager.Channel mChannel;
    private static WifiP2pManager mManager;
    private static WiFiDirectBroadcastReceiver mReceiver;
    private static WifiP2pManager.PeerListListener mListener;
    private static List peers = new ArrayList();
    private static WifiP2pConfig config;
    private static ProgressDialog progress;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // A List of Intents that will allow us to tell what has changed or not

        //  Indicates a change in the Wi-Fi P2P status.
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);

        // Indicates a change in the list of available peers.
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);

        // Indicates the state of Wi-Fi P2P connectivity has changed.
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);

        // Indicates this device's details have changed.
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
        setContentView(R.layout.activity_main);


        // Three most important parts of the Wifi communication
        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this, getMainLooper(), null);


       // Need to resolve with the PartyActivity Activity
       // mReceiver = new WiFiDirectBroadcastReceiver(mManager, mChannel, PartyActivity);

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

                if(peers.size() == 0)
                {
                    Toast.makeText(getApplicationContext(), "No Peers", Toast.LENGTH_LONG).show();
                }

                else
                {
                    // Shows the first peer (Hardcoded)
                    WifiP2pDevice testDevice = (WifiP2pDevice) peers.get(0);
                    Toast.makeText(getApplicationContext(), testDevice.toString(), Toast.LENGTH_LONG).show();
                }
            }
        };

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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
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

    // Previously used for a button
    public void getPeers(View v)
    {
        mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener()
        {
            @Override
            public void onSuccess()
            {
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
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
                Toast.makeText(getApplicationContext(), output , Toast.LENGTH_LONG).show();



            }
        });

        Toast.makeText(getApplicationContext(), "Default", Toast.LENGTH_LONG);
    }

    // Also used for a button
    public void peerConnect(View v)
    {
        config = new WifiP2pConfig();
        config.deviceAddress = ((WifiP2pDevice) peers.get(0)).deviceAddress;
        mManager.connect(mChannel, config, new WifiP2pManager.ActionListener()
        {
            @Override
            public void onSuccess()
            {
                Toast.makeText(getApplicationContext(), "Connected to Device", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int reason)
            {
                Toast.makeText(getApplicationContext(), "Failed for Failure: ", Toast.LENGTH_LONG).show();
            }
        });


    }


    // This is the code that will open a socket and allow us to send data. We have to send and process the strings how we see fit
//
//    public static class FileServerAsyncTask extends AsyncTask<Void, Void, String>
//    {
//        private Context context;
//        private TextView statusText;
//
//        /**
//         * @param context
//         */
//        public FileServerAsyncTask(Context context)
//        {
//            this.context = context;
//        }
//
//        @Override
//        protected String doInBackground(Void... params) {
//            try {
//                ServerSocket serverSocket = new ServerSocket(8988);
//                Log.d("test", "Server: Socket opened");
//                Socket client = serverSocket.accept();
//                Log.d("test", "Server: connection done");
//                final File f = new File(Environment.getExternalStorageDirectory() + "/"
//                        + context.getPackageName() + "/wifip2pshared-" + System.currentTimeMillis()
//                        + ".jpg");
//
//                File dirs = new File(f.getParent());
//                if (!dirs.exists())
//                    dirs.mkdirs();
//                f.createNewFile();
//
//                Log.d("test", "server: copying files " + f.toString());
//                InputStream inputstream = client.getInputStream();
//                copyFile(inputstream, new FileOutputStream(f));
//                serverSocket.close();
//                return f.getAbsolutePath();
//            } catch (IOException e) {
//                Log.e("test", e.getMessage());
//                return null;
//            }
//        }
//
//        /*
//         * (non-Javadoc)
//         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
//         */
//        @Override
//        protected void onPostExecute(String result) {
//            if (result != null) {
//                statusText.setText("File copied - " + result);
//                Intent intent = new Intent();
//                intent.setAction(android.content.Intent.ACTION_VIEW);
//                intent.setDataAndType(Uri.parse("file://" + result), "image/*");
//                context.startActivity(intent);
//            }
//
//        }
//
//        /*
//         * (non-Javadoc)
//         * @see android.os.AsyncTask#onPreExecute()
//         */
//        @Override
//        protected void onPreExecute() {
//            statusText.setText("Opening a server socket");
//        }
//
//    }
//
//    public static boolean copyFile(InputStream inputStream, OutputStream out) {
//        byte buf[] = new byte[1024];
//        int len;
//        try
//        {
//            while ((len = inputStream.read(buf)) != -1) {
//                out.write(buf, 0, len);
//
//            }
//            out.close();
//            inputStream.close();
//        } catch (IOException e) {
//            Log.d("test", e.toString());
//            return false;
//        }
//        return true;
//    }

}
