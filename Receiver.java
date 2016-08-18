package com.example.foodtalks;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class Receiver extends Activity {

	ServerSocket serverSocket;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_receiver);
		
		Thread socketServerThread = new Thread(new SocketServerThread());
		socketServerThread.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.receiver, menu);
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
	 protected void onDestroy() {
	  super.onDestroy();

	  if (serverSocket != null) {
	   try {
	    serverSocket.close();
	   } catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	   }
	  }
	 }

	 private class SocketServerThread extends Thread {

	  static final int SocketServerPORT = 8000;

	  @Override
	  public void run() {
	   Socket socket = null;
	   DataInputStream dataInputStream = null;
	   DataOutputStream dataOutputStream = null;

	   try {
	    serverSocket = new ServerSocket(SocketServerPORT);

	    while (true) {
	     socket = serverSocket.accept();
	     dataInputStream = new DataInputStream(
	       socket.getInputStream());
	     dataOutputStream = new DataOutputStream(
	       socket.getOutputStream());

	     String messageFromClient = "";
	     
	     //If no message sent from client, this code will block the program
	     messageFromClient = dataInputStream.readUTF();
	     intentit(messageFromClient);

	    }
	   } catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    
	   } finally {
	    if (socket != null) {
	     try {
	      socket.close();
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

	    if (dataOutputStream != null) {
	     try {
	      dataOutputStream.close();
	     } catch (IOException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	     }
	    }
	   }
	  }

	 }
	 
	 public void intentit(String mes){
		 Intent in = new Intent(Receiver.this, Questionnaire1.class);
		 in.putExtra("restaurent", mes);
	     this.startActivity(in);
	 }

}
