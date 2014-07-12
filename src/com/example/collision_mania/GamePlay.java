package com.example.collision_mania;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GamePlay extends Activity {
	
	private ImageView mImageView1;
	private ImageView mImageView2;
	Button player1, player2,start ;
	TextView tv,s1,s2;
	
	boolean clicked,startClicked ;
	static int score1 = 0,score2 = 0;
	static int noOfRounds = 0;
	
	Random rn = new Random();
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		Intent intent = getIntent();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_play);
		
		mImageView1 = (ImageView) findViewById(R.id.icon1);
		mImageView2 = (ImageView) findViewById(R.id.icon2);
		
		player1 = (Button) findViewById(R.id.button1);
		player2 = (Button) findViewById(R.id.button2);
		start = (Button) findViewById(R.id.button3);
		tv = (TextView) findViewById(R.id.textView1);
		s1 = (TextView) findViewById(R.id.score1);
		s2 = (TextView) findViewById(R.id.score2);
		
		start.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!startClicked)
				{	
					translate.run();
					
					//Animation anim = new ArcTranslate(3000,Animation.ABSOLUTE,100,Animation.ABSOLUTE,300,Animation.ABSOLUTE,100);
					//anim.setDuration(3000);
					//mImageView1.startAnimation(anim);
					
					noOfRounds++;
					clicked = false;
					startClicked = true;
				}
				if(startClicked && clicked)
				{
					mImageView1.setY(mImageView1.getTop());
					mImageView2.setY(mImageView2.getTop());
					
					player1.setText("PLAYER 1");
					player2.setText("PLAYER 2");
					
					player1.setBackgroundResource(android.R.drawable.btn_default);
					player2.setBackgroundResource(android.R.drawable.btn_default);
					
					tv.setVisibility(View.VISIBLE);
					
					startClicked = false;
					clicked = false;
				}
			}
		});

		
		player1.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				
				if(!clicked && startClicked)
				{
					stopObjects();
					clicked  = true;
					if(victory())
					{
						player1.setBackgroundColor(0xFF00FF00);
						player1.setText("YOU WON");
						score1++;
						s1.setText(""+score1);
					}
					else
					{
						player1.setBackgroundColor(0xffff0000);
						player1.setText("YOU LOST");
						score2++;
						s2.setText(""+score2);
					}
				}
				return false;
			}
		});
		
		
		player2.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				
				if(!clicked && startClicked)
				{
					stopObjects();
					clicked  = true;
					if(victory())
					{
						player2.setBackgroundColor(0xFF00FF00);
						player2.setText("YOU WON");
						
						score2++;
						s2.setText(""+score2);
					}
					else
					{
						player2.setBackgroundColor(0xffff0000);
						player2.setText("YOU LOST");
						score1++;
						s1.setText(""+score1);
					}
				}
				return false;
			}
		});
		
	}
	/*public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);

		if (hasFocus) {
			translate.run();
		}
	}*/
	
	Runnable translate = new Runnable() {
		public void run() {
			
			int time = rn.nextInt(3001)+ 2000; // generating time of animation
			
			float translation = getResources()
					.getDimension(R.dimen.translation);
			mImageView1.animate().setDuration(time)
					.setInterpolator(new LinearInterpolator())
					.translationYBy(translation);
			mImageView2.animate().setDuration(time)
			.setInterpolator(new LinearInterpolator())
			.translationYBy(-translation);
			//tv.setVisibility(View.INVISIBLE);
			tv.setText("time"+time);
			/*if(pause)
				try {
					this.wait(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
		}
	};
	void stopObjects()
	{
		mImageView1.animate().cancel();
		mImageView2.animate().cancel();
		//tv.setText("x"+mImageView1.getTranslationY()+"  "+(mImageView2.getTop()- mImageView1.getTop())+" "+mImageView1.getHeight());
		tv.setVisibility(View.INVISIBLE);
		//player2.setText("x"+mImageView1.getTranslationY()+"  "+(mImageView2.getTop()- mImageView1.getTop())+" "+mImageView1.getHeight());
	}
	boolean victory()
	{
		float diff = ( mImageView2.getTop()- mImageView1.getTop());
		float trans = mImageView1.getTranslationY()-mImageView2.getTranslationY();
		if(trans+mImageView1.getHeight()>=diff ) return true;
		else return false;
	}
}