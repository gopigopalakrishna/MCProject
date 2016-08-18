package com.example.foodtalks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Questionnaire1 extends Activity implements OnClickListener {

	TextView t1,t2,t3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_questionnaire1);
		t1= (TextView) findViewById(R.id.textView4);
		t2= (TextView) findViewById(R.id.textView6);
		t3= (TextView) findViewById(R.id.textView7);
		String mes = getIntent().getExtras().getString("restaurent");
		String[] parts = mes.split("\\."); // String array, each element is text between dots
		t1.setText(parts[0]);
		t2.setText(parts[1]);
		t3.setText(parts[2]);
		Button but = (Button) findViewById(R.id.button1);
		but.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		Intent i = new Intent(Questionnaire1.this,Select.class);
		startActivity(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.questionnaire1, menu);
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
}
