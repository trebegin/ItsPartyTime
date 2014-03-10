package com.itspartytime;

import java.util.ArrayList;

public class DeviceController 
{
	private ArrayList<Device> deviceList = null;
	
	/**
	 * Constructor
	 * 
	 * preconditions:
	 * 		- 
	 * 
	 * parameters:
	 * 		- none
	 * 
	 * postconditions:
	 * 		- 
	 * 
	 * recent changes:
	 * 		-
	 * 
	 * known bugs:
	 * 		-
	 */
	public DeviceController()
	{
		
	}
	
	
	/**
	 * Pushes new playlist to the devices in deviceList
	 * 
	 * preconditions:
	 * 		- parameter playlist is non-null
	 * 		- deviceList is non-null
	 * 		- device is a host device
	 * 
	 * parameters:
	 * 		- Playlist playlist	-> Playlist to push to devices in deviceList
	 * 
	 * postconditions:
	 * 		- 
	 * 
	 * recent changes:
	 * 		-
	 * 
	 * known bugs:
	 * 		-
	 * 
	 * @param playlist
	 */
	public void updatePlaylist(Playlist playlist)
	{
		
	}
	
	/**
	 * Pushes vote request to device in deviceList
	 * 
	 * preconditions:
	 * 		- parameter vote is a 1 or 0
	 * 		- parameter song is non-null and exists in mPlaylist
	 * 		- deviceList is non-null
	 * 		- device is guest device
	 * 
	 * parameters:
	 * 		- int vote	-> 1 indicates upvote, 0 indicates downvote for song
	 * 		- Song song	-> song is the object that should be changed
	 * 
	 * postconditions:
	 * 		- host sends out new, up to date playlist
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
	public void updateVote(int vote, Song song)
	{
		
	}
	
	
	/**
	 * Pushes join party request to device in deviceList
	 * 
	 * preconditions:
	 * 		- deviceList is non-null
	 * 		- device is guest device
	 * 
	 * parameters:
	 * 		- none
	 * 
	 * postconditions:
	 * 		- device receives all party information from host
	 * 
	 * recent changes:
	 * 		-
	 * 
	 * known bugs:
	 * 		- How do we get the MAC address of the host before/while joining the party?
	 * 
	 */
	public void joinParty()
	{
		
	}
}
