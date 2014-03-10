package com.itspartytime;

public class LANComInterface 
{
	/**
	 * Pushes packet to the specified MAC address
	 * 
	 * preconditions:
	 * 		- parameter MacAddress represents valid MAC address
	 * 
	 * parameters:
	 * 		- int MacAddress	-> MAC address to push message to
	 * 		- String packet		-> packet to be pushed to device with the correct MAC address
	 * 
	 * postconditions:
	 * 		- target device recieve message
	 * 
	 * recent changes:
	 * 		-
	 * 
	 * known bugs:
	 * 		- Is packet supposed to be a String?
	 * 
	 * @param MacAddress
	 * @param packet
	 */
	public void push(int MacAddress, String packet)
	{

	}
}
