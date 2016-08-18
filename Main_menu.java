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

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Main_menu extends ActionBarActivity {

	private ListView options;
	String mes;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
        
		options = (ListView)findViewById(R.id.listView1);
		
		populateList();
		registerClickCallback();
	
	 }
	
    private void populateList() {
		
	    String[] menuItems= new String[] {"Americano          15 INR","Capuccino          20 INR","Expresso             25 INR","Lemon tea          32 INR"};
			
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, menuItems);
		ListView list_items=(ListView)findViewById(R.id.listView1);
		list_items.setAdapter(adapter);
		
	 }

    private void registerClickCallback() {
    	
    	 options.setOnItemClickListener(new AdapterView.OnItemClickListener() {

	     @Override
	     public void onItemClick(AdapterView<?> parent, final View view,
	     int position, long id) {
	                
	           if(position == 0)
	             {
	            		mes="AMERICANO";
	             }
	           
	           else if(position == 1)
	             {
	            		mes="CAPPUCHINO";
	             }
	            	
	           else if(position == 2)
	             {
	            		mes="EXPRESSO";
	             }
	            	
	           else if(position == 3)
	             {
	            		mes="LEMON TEA";
	             }
	           
	           MyClientTask myClientTask = new MyClientTask(mes);
	           myClientTask.execute();
	     }
	         
	  });
    }
	
    	 public class MyClientTask extends AsyncTask<Void, Void, Void> {

	          String dstAddress;
	          int dstPort;
	          String response = "";
	          String msgToServer;

	          MyClientTask(String msgTo) {
	           if(getIpAddress().equals("192.168.161.213")){
	        	   Toast.makeText(Main_menu.this, "dest:rohan", Toast.LENGTH_LONG).show();
	        	   dstAddress = "192.168.161.211";
	           }
	           else{
	        	   Toast.makeText(Main_menu.this, getIpAddress(), Toast.LENGTH_LONG).show();
	        	   dstAddress = "192.168.161.213";
	           }
	           dstPort = 8080;
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
	            Intent i =new Intent(Main_menu.this,Receiver.class);
		           startActivity(i);
	           }
	           return null;
	          }

	          @Override
	          protected void onPostExecute(Void result) {
	           super.onPostExecute(result);
	          }

	         }
	    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
		      ip += inetAddress.getHostAddress();
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
