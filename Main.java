package com.example.foodtalks;

import java.util.ArrayList;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.support.v4.app.FragmentActivity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

	public class Main extends FragmentActivity implements OnClickListener {

		private final String TAG = getClass().getSimpleName();
		Button menu;
		private GoogleMap map;
		private LocationManager locationManager;
		private Location loc;
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_main);
				
			menu = (Button) findViewById(R.id.button1);
			menu.setOnClickListener(this);
			Context context;
			initCompo();
			currentLocation();
			
			final LocationManager manager = (LocationManager)Main.this.getSystemService(Context.LOCATION_SERVICE );

			if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) )
			{
			  Toast.makeText(Main.this, "GPS is disabled!", Toast.LENGTH_LONG).show(); 
			  
			  if(manager.isProviderEnabled( LocationManager.NETWORK_PROVIDER))
			  {				  
				  Toast.makeText(Main.this, "netork available!", Toast.LENGTH_LONG).show(); 
	              manager.requestLocationUpdates( LocationManager.NETWORK_PROVIDER,  0,  0, (LocationListener) this);
				  if (manager != null) {
                	  Location location = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                	  Toast.makeText(Main.this, "Location by network!", Toast.LENGTH_LONG).show();
                	  if (location == null) {
          				locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener);
          			} else 
          			{
          			  double      latitude = location.getLatitude();
                      double  longitude = location.getLongitude();
                      Toast.makeText(
  							Main.this,
  							"Mobile Location (GPS): \nLatitude: " + latitude
  									+ "\nLongitude: " + longitude,
  							Toast.LENGTH_LONG).show();
          				loc = location;
          				new GetPlaces().execute();
          				Log.e(TAG, "location : " + location);
          			}
              
                  }
				  else 
					  Toast.makeText(Main.this, "manager is null!", Toast.LENGTH_LONG).show();
					          
				   
			  }
			  else{
                	  Toast.makeText(Main.this, "Network is disabled!", Toast.LENGTH_LONG).show();
                	 //startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));

			  }
			}
			else
				//if(manager.isProviderEnabled( LocationManager.NETWORK_PROVIDER ))
			  Toast.makeText(Main.this, "GPS is enabled!", Toast.LENGTH_LONG).show();

			
			if (loc != null) {
				map.clear();
				new GetPlaces().execute();
			}
		}
		
		@Override
		public boolean onCreateOptionsMenu(Menu menu){
			MenuInflater rif = getMenuInflater();
			rif.inflate(R.menu.action_bar, menu);
			return super.onCreateOptionsMenu(menu);
			
		}
		

		@Override
		public void onClick(View v) {
			if(v.getId() == R.id.button1)
			{
				Intent launchmenu = new Intent(this, Select.class);
				this.startActivity(launchmenu);
			}
		}
	
	
	private class GetPlaces extends AsyncTask<Void, Void, ArrayList<Place>> {

		
		@Override
		protected void onPostExecute(ArrayList<Place> result) {
			super.onPostExecute(result);
			
			
			for (int i = 0; i < result.size(); i++) {
				map.addMarker(new MarkerOptions()
						.title(result.get(i).getName())
						.position(
								new LatLng(result.get(i).getLatitude(), result
										.get(i).getLongitude()))
						.snippet(result.get(i).getVicinity()));
			}
			
		}

		@Override
		protected ArrayList<Place> doInBackground(Void... params) {
			PlacesService service = new PlacesService(
					"AIzaSyDe4Zt3RzXGy0qBz4IFTX74OzMY5jodTFQ");
			ArrayList<Place> findPlaces = service.findPlaces(loc.getLatitude(), // 28.632808
					loc.getLongitude(), "restaurant"); // 77.218276

			for (int i = 0; i < findPlaces.size(); i++) {

				Place placeDetail = findPlaces.get(i);
				Log.e(TAG, "places : " + placeDetail.getName());
			}
			return findPlaces;			
		}
	}
	
		private void initCompo() {
			map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
			map.setMyLocationEnabled(true);
		}
		
		private void currentLocation() {
			locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

			String provider = locationManager
					.getBestProvider(new Criteria(), false);

			Location location = locationManager.getLastKnownLocation(provider);

			if (location == null) {
				locationManager.requestLocationUpdates(provider, 0, 0, listener);
			} else {
				loc = location;
				new GetPlaces().execute();
				Log.e(TAG, "location : " + location);
			}

		}
		
		private LocationListener listener = new LocationListener() {

			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {

			}

			@Override
			public void onProviderEnabled(String provider) {

			}

			@Override
			public void onProviderDisabled(String provider) {

			}

			@Override
			public void onLocationChanged(Location location) {
				Log.e(TAG, "location update : " + location);
				loc = location;
				locationManager.removeUpdates(listener);
			}
		};
	}