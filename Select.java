package com.example.foodtalks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Select extends Activity implements OnClickListener {

	Button ask, answer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select);
	
		ask = (Button) findViewById(R.id.button1);
		ask.setOnClickListener(this);
	    
		answer = (Button) findViewById(R.id.button2);
		answer.setOnClickListener(this);
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.select, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		
			if(v.getId() == R.id.button1)
			{
				Intent launchmenu = new Intent(this, Main_menu.class);
				this.startActivity(launchmenu);
			}
		
		    else if(v.getId() == R.id.button2)
		    {		
				Intent launchmenu = new Intent(this, Server.class);
				this.startActivity(launchmenu);
		    }
		
	}
}
