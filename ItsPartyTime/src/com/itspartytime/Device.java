package com.itspartytime;


public class Device 
{
	private static String mMacAddress;
	private static String mUsername;
	private static Boolean isHost;
	
	public Device(String macAddress, String username, boolean Host)
	{
		mMacAddress = macAddress;
		mUsername = username;
		isHost = Host;
	}
	
	public boolean isHost()
	{
		return isHost;
	}
	
	public String getUsername()
	{
		return mUsername;
	}
	
	public void setUsername(String newName)
	{
		mUsername = newName;
	}
	
	public String getMacAddress()
	{
		return mMacAddress;
	}
	
}
