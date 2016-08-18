package com.example.foodtalks;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.util.Log;

public class BatteryReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		
		//Creating an intent-filter
    	IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
    	
    	Intent batteryStatus = context.registerReceiver(null, ifilter);
    	int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
    	Log.i("badttery level", String.valueOf(level));
    	int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
    	Log.i("battery scale", String.valueOf(scale));
    	float batteryPct = level / (float)scale;
    	Log.i("battery Pct", String.valueOf(batteryPct));
    	
    	// Are we charging / charged? 
    	int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
    	boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
    	                     status == BatteryManager.BATTERY_STATUS_FULL;
    	Log.i("isCharging:", String.valueOf(isCharging));
    	 
    	WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE); 
    
    	boolean batteryLow = intent.getAction().equals(Intent.ACTION_BATTERY_LOW);
        if (batteryLow){ 
               
                level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1); 
                if ((level<10)&&(!isCharging)) { 
                  //call your code 
                  wifiManager.setWifiEnabled(false); 
                } 
                else if(isCharging){
                	wifiManager.setWifiEnabled(true);
                	
                }
        } 
    	
		
	}

}
