package com.android.attention;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

public class Flavor extends Activity {
	
	private static final String TAG = "Flavour";
	
	private Spinner mIceCream;
	
	private Button mDone;
	
	private String mIceCreamFlavor = null;
	
	private long mStartTime = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ice_cream_falvor);
		mStartTime = System.currentTimeMillis();
		mIceCream = (Spinner) findViewById(R.id.spinner1);
		mIceCream.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				mIceCreamFlavor = parent.getItemAtPosition(pos).toString();				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		mDone = (Button) findViewById(R.id.done);
		mDone.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				long endTime = System.currentTimeMillis();
				Log.d(TAG, "Time taken from start of Activity to pressing of done: "+(endTime - mStartTime) + " milliseconds");
				Intent intent = new Intent();
				if (mIceCreamFlavor != null) {
					intent.putExtra("flavor", mIceCreamFlavor);
				}
				setResult(RESULT_OK, intent);
				finish();
				
			}
		});
	}

}
