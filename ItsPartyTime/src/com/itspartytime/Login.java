package com.itspartytime;

import java.io.IOException;
import java.net.URISyntaxException;

import android.os.AsyncTask;

import com.faceture.google.play.LoginResponse;
import com.faceture.google.play.LoginResult;
import com.faceture.google.play.PlayClient;
import com.faceture.google.play.PlayClientBuilder;
import com.faceture.google.play.PlaySession;

public class Login extends AsyncTask<Void, Void, PlaySession> 
{
	private String password;
	
	public void setup(String password)
	{
		this.password = password;
	}
	@Override
	protected PlaySession doInBackground(Void... params) {
		PlayClientBuilder mPlayClientBuilder = new PlayClientBuilder();
		PlayClient mPlayClient = mPlayClientBuilder.create();
		LoginResponse mLoginResponse;
		try {
			mLoginResponse = mPlayClient.login("torrey1028@gmail.com", password);
			assert(LoginResult.SUCCESS == mLoginResponse.getLoginResult());
			// assume login is success
			PlaySession mPlaySession = mLoginResponse.getPlaySession();
			return mPlaySession;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// in future, get from Party
		return null;
	}

}
