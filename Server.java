package com.example.foodtalks;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Server extends Activity {

 ServerSocket serverSocket;

 @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_server);
  
  Thread socketServerThread = new Thread(new SocketServerThread());
  socketServerThread.start();
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

  static final int SocketServerPORT = 8080;

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
	 Intent in = new Intent(this, Questionnaire.class);
	 in.putExtra("restaurent", mes);
     this.startActivity(in);
 }


}