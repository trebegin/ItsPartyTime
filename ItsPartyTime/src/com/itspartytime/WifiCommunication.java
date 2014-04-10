package com.itspartytime;

import android.os.Message;
import android.os.Messenger;

/**
 * Created by Trent on 4/10/14.
 */
public class WifiCommunication
{
    private Messenger mMessenger;

    public static final int MSG_CONNECT = 2;
    public static final int MSG_FULL_PLAYLIST = 10;
    public static final int MSG_VOTE_SONG = 11;

    public WifiCommunication()
    {

    }

    public void write()
    {
        Message m = Message.obtain(null, 1);

    }


}
