package com.example.foodtalks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.animation.Animation;
import android.graphics.drawable.AnimationDrawable;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class Splash_Screen extends Activity implements AnimationListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash__screen);
		
		Thread splash_screen=new Thread(){
			@Override
			public void run(){
				try{
					sleep(5000);
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					Intent i = new Intent(getApplicationContext(), Main.class);
					startActivity(i);
					finish();
				}
			}
			
		};
		splash_screen.start();
		ImageView myAnimation = (ImageView)findViewById(R.id.myanimation);
		final AnimationDrawable myAnimationDrawable = (AnimationDrawable)myAnimation.getDrawable();
		
		myAnimation.post(
				new Runnable(){

				  @Override
				  public void run() {
				   myAnimationDrawable.start();
				  }
				});
		
	}

	@Override
	public void onAnimationEnd(Animation arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAnimationRepeat(Animation arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAnimationStart(Animation arg0) {
		// TODO Auto-generated method stub
		
	}
}
