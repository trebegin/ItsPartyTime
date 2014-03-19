package com.itspartytime;

public class Song 
{
	
	private String songName;
	private double songLength;
	private int songUpVotes;
	private int songDownVotes;
	
	// album art, not sure what format yet
	public String getSongName() {
		return songName;
	}
	public void setSongName(String songName) {
		this.songName = songName;
	}
	public double getSongLength() {
		return songLength;
	}
	public void setSongLength(double songLength) {
		this.songLength = songLength;
	}
	public int getSongDownVotes() {
		return songDownVotes;
	}
	public void setSongDownVotes(int songDownVotes) {
		this.songDownVotes = songDownVotes;
	}
	public int getSongUpVotes() {
		return songUpVotes;
	}
	public void setSongUpVotes(int songUpVotes) {
		this.songUpVotes = songUpVotes;
	}

}
