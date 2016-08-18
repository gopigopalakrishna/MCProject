package com.example.foodtalks;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class Questionnaire extends Activity implements OnClickListener {

	TextView food_item;
	String first,sec,mes;
	RatingBar ratingBar;
	int abc;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_survey);
		
		Button but = (Button) findViewById(R.id.button1);
		but.setOnClickListener(this);
	    
		food_item = (TextView) findViewById(R.id.item);
		food_item.setText(getIntent().getExtras().getString("restaurent"));
		
		ratingBar =(RatingBar) findViewById(R.id.ratingBar1);;
		
	}
	
	public void onRadioButtonClicked(View view) {
	    // Is the button now checked?
	    boolean checked = ((RadioButton) view).isChecked();
	    
	    // Check which radio button was clicked
	    switch(view.getId()) {
	        case R.id.radioButton1:
	            if (checked)
	                first="yes";
	            break;
	        case R.id.radioButton2:
	            if (checked)
	                first="no";
	            break;
	        case R.id.RadioButton01:
	            if (checked)
	                sec="yes";
	            break;
	        case R.id.RadioButton02:
	            if (checked)
	                sec="no";
	            break;
	    }
	    
	}
	
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.button1)
		{
			abc=(int) ratingBar.getRating();
	    
			mes=first+"."+sec+"."+String.valueOf(abc)+" stars";
			MyClientTask myClientTask = new MyClientTask(mes);
			   myClientTask.execute();
		}
	}
	public class MyClientTask extends AsyncTask<Void, Void, Void> {

		  String dstAddress;
		  int dstPort;
		  String response = "";
		  String msgToServer;

		  MyClientTask(String msgTo) {
			  if(getIpAddress().equals("192.168.161.213"))
				  dstAddress = "192.168.161.211";
			  else
				  dstAddress = "192.168.161.213";
			  dstPort = 8000;
			  msgToServer = msgTo;
		  }

		  @Override
		  protected Void doInBackground(Void... arg0) {

		   Socket socket = null;
		   DataOutputStream dataOutputStream = null;
		   DataInputStream dataInputStream = null;

		   try {
		    socket = new Socket(dstAddress, dstPort);
		    dataOutputStream = new DataOutputStream(
		      socket.getOutputStream());
		    dataInputStream = new DataInputStream(socket.getInputStream());
		    
		    if(msgToServer != null){
		     dataOutputStream.writeUTF(msgToServer);
		    }

		   } catch (UnknownHostException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		    response = "UnknownHostException: " + e.toString();
		   } catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		    response = "IOException: " + e.toString();
		   } finally {
		    if (socket != null) {
		     try {
		      socket.close();
		     } catch (IOException e) {
		      // TODO Auto-generated catch block
		      e.printStackTrace();
		     }
		    }

		    if (dataOutputStream != null) {
		     try {
		      dataOutputStream.close();
		     } catch (IOException e) {
		      // TODO Auto-generated catch block
		      e.printStackTrace();
		     }
		    }

		    if (dataInputStream != null) {
		     try {
		      dataInputStream.close();
		     } catch (IOException e) {
		      // TODO Auto-generated catch block
		      e.printStackTrace();
		     }
		    }
		    Intent i = new Intent(Questionnaire.this,Select.class);
		    startActivity(i);
		   }
		   return null;
		  }

		  @Override
		  protected void onPostExecute(Void result) {
		   super.onPostExecute(result);
		   Toast.makeText(Questionnaire.this, "Message Sent", Toast.LENGTH_LONG).show();
		  }

		 }
	private String getIpAddress() {
		  String ip = "";
		  try {
		   Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
		     .getNetworkInterfaces();
		   while (enumNetworkInterfaces.hasMoreElements()) {
		    NetworkInterface networkInterface = enumNetworkInterfaces
		      .nextElement();
		    Enumeration<InetAddress> enumInetAddress = networkInterface
		      .getInetAddresses();
		    while (enumInetAddress.hasMoreElements()) {
		     InetAddress inetAddress = enumInetAddress.nextElement();

		     if (inetAddress.isSiteLocalAddress()) {
		      ip +=  inetAddress.getHostAddress();
		     }
		     
		    }

		   }

		  } catch (SocketException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		   ip += "Something Wrong! " + e.toString() + "\n";
		  }

		  return ip;
		 }
}
