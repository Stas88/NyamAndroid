package com.st.nyam.broadcast_receivers;

import com.st.nyam.NyamApplication;
import com.st.nyam.NyamApplication.SetUpCookies;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;


public class ConnectionChangeReceiver extends BroadcastReceiver
{
  @Override
  public void onReceive(Context context, Intent intent )
  {
    ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService( Context.CONNECTIVITY_SERVICE );
    //NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(     ConnectivityManager.TYPE_MOBILE );
    State wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();

    if ((wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING) && NyamApplication.isApplicationSentToBackground(context)); 
	    {
	    	if (NyamApplication.isPasswordExists()) {
		    	Object[] params = new Object[] {};
				new NyamApplication.SetUpCookies().execute(params);
	    	}
	    }
    }
}
