package com.android.attention;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class YourAge extends Activity {
	
	private static final String TAG = "Attention";

	private EditText mAge;

	private Button mDone;
	
	private long mStartTime = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.your_age);
		mStartTime = System.currentTimeMillis();
		mAge = (EditText) findViewById(R.id.add_age);
		mDone = (Button) findViewById(R.id.done);
		mDone.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				long endTime = System.currentTimeMillis();
				Log.d(TAG, "Time taken from start of Activity to pressing of done: "+(endTime - mStartTime) + " milliseconds");
				Intent intent = new Intent();
				String age = mAge.getText().toString();
				if (age != null) {
					intent.putExtra("age", age);
				}
				setResult(RESULT_OK, intent);
				finish();
			}
		});
	}
}
