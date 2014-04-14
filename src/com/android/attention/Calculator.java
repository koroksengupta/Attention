package com.android.attention;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Calculator extends Activity{
	
	private static final String TAG = "Attention";
	
	private Button mFirst;
	
	private Button mSecond;
	
	private Button mThird;
	
	private Button mFourth;
	
	private long mStartTime = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calculate);
		mStartTime = System.currentTimeMillis();
		initButton();
	}
	
	private void initButton(){
		mFirst = (Button) findViewById(R.id.answer1);
		
		mSecond = (Button) findViewById(R.id.answer2);
		
		mThird = (Button) findViewById(R.id.answer3);
		
		mFourth = (Button) findViewById(R.id.answer4);
		mFirst.setOnClickListener(mButtonListener);
		mSecond.setOnClickListener(mButtonListener);
		mThird.setOnClickListener(mButtonListener);
		mFourth.setOnClickListener(mButtonListener);
	}
	
	private View.OnClickListener mButtonListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			String info = (String) ((Button)v).getText();
			long endTime = System.currentTimeMillis();
			Log.d(TAG, "Time taken from start of Activity to pressing of done: "+(endTime - mStartTime) + " milliseconds");
			if(info != null){
				intent.putExtra("answer_info", info);
			}
			setResult(RESULT_OK, intent);
			finish();
			
		}
	};
}
