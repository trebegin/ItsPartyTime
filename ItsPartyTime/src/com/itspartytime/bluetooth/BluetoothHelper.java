package com.itspartytime.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.UUID;

import android.os.Bundle;
import android.os.Handler;
import android.provider.Telephony;
import android.view.View;

import com.itspartytime.PartyActivity;
import com.itspartytime.R;


/**
 * Created by Trent on 4/23/14.
 */
public class BluetoothHelper
{
    final int MESSAGE_READ = 2;
    final int MESSAGE_WRITE = 3;

    private static final UUID mUUID = UUID.fromString("643E9340-CB33-11E3-9C1A-0800200C9A66");

    private BluetoothAdapter mBluetoothAdapter;
    private Handler mBluetoothHandler;
    private ArrayList<BluetoothDevice> devices;
    private ConnectedThread mConnectedThread;

    public BluetoothHelper(BluetoothAdapter bluetoothAdapter, Handler mHandler)
    {
        mBluetoothAdapter = bluetoothAdapter;
        mBluetoothHandler = mHandler;
        devices = new ArrayList<BluetoothDevice>();
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver()
    {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // Add the name and address to an array adapter to show in a ListView
                devices.add(device);
            }

            else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action))
            {

            }
        }
    };

    private class AcceptThread extends Thread {
        private final BluetoothServerSocket mmServerSocket;

        public AcceptThread() {
            // Use a temporary object that is later assigned to mmServerSocket,
            // because mmServerSocket is final
            BluetoothServerSocket tmp = null;
            try
            {
                // MY_UUID is the app's UUID string, also used by the client code
                tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("brutrooth", mUUID);
            } catch (IOException e) { }
            mmServerSocket = tmp;
        }

        public void run() {
            BluetoothSocket socket = null;
            // Keep listening until exception occurs or a socket is returned
            while (true) {
                try {
                    socket = mmServerSocket.accept();
                } catch (IOException e) {
                    break;
                }
                // If a connection was accepted
                if (socket != null) {
                    // Do work to manage the connection (in a separate thread)
                    manageConnectedSocket(socket);

                    /*
                    try
                    {
                        //mmServerSocket.close();
                    }
                    catch (IOException e) {}
                    break;
                    */
                }
            }
        }

        /** Will cancel the listening socket, and cause the thread to finish */
        public void cancel() {
            try {
                mmServerSocket.close();
            } catch (IOException e) { }
        }
    }

    private class ConnectThread extends Thread
    {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device)
        {
            // Use a temporary object that is later assigned to mmSocket,
            // because mmSocket is final
            BluetoothSocket tmp = null;
            mmDevice = device;

            // Get a BluetoothSocket to connect with the given BluetoothDevice
            try
            {
                // MY_UUID is the app's UUID string, also used by the server code
                tmp = device.createRfcommSocketToServiceRecord(mUUID);

            }
            catch (IOException e) { PartyActivity.toaster("Dat exception douuuu"); }
            mmSocket = tmp;
        }

        public void run()
        {
            // Cancel discovery because it will slow down the connection
            mBluetoothAdapter.cancelDiscovery();

            try
            {
                // Connect the device through the socket. This will block
                // until it succeeds or throws an exception
                mmSocket.connect();
            }
            catch (IOException connectException)
            {
                // Unable to connect; close the socket and get out
                try
                {
                    mmSocket.close();
                }
                catch (IOException closeException) { }
                return;
            }

            // Do work to manage the connection (in a separate thread)
            manageConnectedSocket(mmSocket);
        }

        /** Will cancel an in-progress connection, and close the socket */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }
    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket)
        {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try
            {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            }
            catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;

        }

        public void run()
        {
            // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs
            while (true)
            {
                try
                {
                    byte[] buffer = new byte[4096];  // buffer store for the stream
                    byte[] headerBytes = new byte[4];
                    int bytes;

                    // Read from the InputStream
                    mmInStream.read(headerBytes, 0, 4);
                    ByteBuffer target = ByteBuffer.wrap(headerBytes);
                    int header = target.getInt();

                    bytes = mmInStream.read(buffer);

                    // Send the obtained bytes to the UI activity
                    synchronized (this)
                    {
                        mBluetoothHandler.obtainMessage(header, bytes, -1, buffer).sendToTarget();
                        //wait(10);
                    }

                } catch (IOException e)
                {
                    e.printStackTrace();
                    break;
                }
//                catch (InterruptedException e)
//                {
//                    e.printStackTrace();
//                    break;
//                }
            }
        }

        /* Call this from the main activity to send data to the remote device */
        public void write(byte[] bytes)
        {
            try
            {
                mmOutStream.write(bytes);
            }
            catch (IOException e) { }
        }

        /* Call this from the main activity to shutdown the connection */
        public void cancel()
        {
            try
            {
                mmSocket.close();
            } catch (IOException e) { }
        }
    }

    public ArrayList<BluetoothDevice> discover()
    {
        devices.clear();
        devices.addAll(mBluetoothAdapter.getBondedDevices());


        if(mBluetoothAdapter.isDiscovering())
            mBluetoothAdapter.cancelDiscovery();

        mBluetoothAdapter.startDiscovery();

        return devices;

        /*
        for(BluetoothDevice device : devices)
        {
            deviceAdapter.add(device.getName());
        }
        */
    }

    public void accept()
    {
        AcceptThread acceptThread = new AcceptThread();
        acceptThread.start();
    }

    public void connect(BluetoothDevice device)
    {
        ConnectThread connectThread = new ConnectThread(device);
        connectThread.start();
    }

    private void manageConnectedSocket(BluetoothSocket socket)
    {
        mConnectedThread = new ConnectedThread(socket);
        mConnectedThread.start();
    }

    public void send(byte [] bytes, int messageType)
    {
        byte[] message = new byte[4 + bytes.length];
        ByteBuffer target = ByteBuffer.wrap(message);

        target.putInt(messageType);
        target.put(bytes);

        if(mConnectedThread != null)
        {
            mConnectedThread.write(message);
            synchronized (mConnectedThread)
            {
                try{
                mConnectedThread.wait(10);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
