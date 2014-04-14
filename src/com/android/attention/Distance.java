package com.android.attention;

import com.android.attention.CircularSeekBar.OnSeekChangeListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Distance extends Activity{
	
	private static final String TAG = "Distance";
	
	private static final int MAX_DISTANCE_TO_HOME = 100;
	private static final int MIN_DISTANCE_TO_HOME = 0;
	
	private CircularSeekBar mCirSeekbar;
	
	private Button mDone;
	
	private long mStartTime = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.distance_to_home);
		mStartTime = System.currentTimeMillis();

		mCirSeekbar = (CircularSeekBar) findViewById(R.id.distance_to_home);
		mCirSeekbar.setMaxProgress(MAX_DISTANCE_TO_HOME);
		mCirSeekbar.setProgress(MIN_DISTANCE_TO_HOME);
		mCirSeekbar.setBarWidth(50);
		mCirSeekbar.invalidate();
		mCirSeekbar.setSeekBarChangeListener(new OnSeekChangeListener(){

			@Override
			public void onProgressChange(CircularSeekBar view, int newProgress) {
				Log.d("Welcome", "Progress:" + view.getProgress() + "/" + view.getMaxProgress());
				mCirSeekbar.setText(Integer.toString(view.getProgress()));
			}
			
		});
		mDone = (Button) findViewById(R.id.done);
		mDone.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				long endTime = System.currentTimeMillis();
				Log.d(TAG, "Time taken from start of Activity to pressing of done: "+(endTime - mStartTime) + " milliseconds");
				Intent intent = new Intent();
				intent.putExtra("distance", mCirSeekbar.getProgress());
				setResult(RESULT_OK, intent);
				finish();
			}
		});
	}
}
