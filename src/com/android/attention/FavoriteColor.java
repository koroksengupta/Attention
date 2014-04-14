package com.android.attention;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class FavoriteColor extends Activity {
	
	private static final String TAG = "Favourite Color";

	private RadioGroup mColorGrp;

	private RadioButton mColorBtn;

	private Button mDone;

	private long mStartTime = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.favorite_color);
		mStartTime = System.currentTimeMillis();
		initButton();
	}

	private void initButton() {
		mColorGrp = (RadioGroup) findViewById(R.id.radioColor);
		mDone = (Button) findViewById(R.id.done);
		mDone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				long endTime = System.currentTimeMillis();
				Log.d(TAG,
						"Time taken from start of Activity to pressing of done: "
								+ (endTime - mStartTime) + " milliseconds");
				int selectedId = mColorGrp.getCheckedRadioButtonId();

				mColorBtn = (RadioButton) findViewById(selectedId);
				intent.putExtra("radio", mColorBtn.getText().toString());
				setResult(RESULT_OK, intent);
				finish();
			}

		});
	}
}
