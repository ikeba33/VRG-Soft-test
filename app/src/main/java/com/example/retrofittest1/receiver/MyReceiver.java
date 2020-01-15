package com.example.retrofittest1.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.retrofittest1.managers.InternetConnectionManager;

public class MyReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        String status = InternetConnectionManager.getConnectivityStatusString(context);
        if(status.isEmpty()) {
            status="No Internet Connection";
        }
        if(status.equals("No internet is available"))
            Toast.makeText(context, status, Toast.LENGTH_LONG).show();
    }

}
