package com.android.attention;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	private Button mCalculation;

	private Button mDistance;

	private Button mAge;

	private Button mFlavor;

	private Button mColor;

	private TextView mTextResult;

	private static final int CALCULATE_NUMBER = 101;

	private static final int GET_DISTANCE_FROM_HOME = 102;

	private static final int GET_YOUR_AGE = 103;

	private static final int GET_YOUR_FLAVOR = 104;

	private static final int GET_FAVORITE_COLOR = 105;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mTextResult = (TextView) findViewById(R.id.answer);
		initButton();
	}

	private void initButton() {
		//Buttons
		mCalculation = (Button) findViewById(R.id.first_splash_screen);
		mDistance = (Button) findViewById(R.id.second_splash_screen);
		mAge = (Button) findViewById(R.id.third_splash_screen);
		mFlavor = (Button) findViewById(R.id.fourth_splash_screen);
		mColor = (Button) findViewById(R.id.fifth_splash_screen);
		//Listeners
		mCalculation.setOnClickListener(mCalListener);
		mDistance.setOnClickListener(mDistListener);
		mAge.setOnClickListener(mAgeListener);
		mFlavor.setOnClickListener(mFlavorListener);
		mColor.setOnClickListener(mColorListener);
	}

	private View.OnClickListener mCalListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intentCalculate = new Intent(MainActivity.this,
					Calculator.class);
			startActivityForResult(intentCalculate, CALCULATE_NUMBER);

		}
	};

	private View.OnClickListener mDistListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intentDist = new Intent(MainActivity.this, Distance.class);
			startActivityForResult(intentDist, GET_DISTANCE_FROM_HOME);

		}
	};

	private View.OnClickListener mAgeListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intentAge = new Intent(MainActivity.this, YourAge.class);
			startActivityForResult(intentAge, GET_YOUR_AGE);

		}
	};

	private View.OnClickListener mFlavorListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intentFlavor = new Intent(MainActivity.this, Flavor.class);
			startActivityForResult(intentFlavor, GET_YOUR_FLAVOR);

		}
	};

	private View.OnClickListener mColorListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intentFlavor = new Intent(MainActivity.this,
					FavoriteColor.class);
			startActivityForResult(intentFlavor, GET_FAVORITE_COLOR);

		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == CALCULATE_NUMBER) {
				Bundle extras = data.getExtras();
				if (extras != null) {
					String info = data.getExtras().getString("answer_info");
					mTextResult.setText("The calculation result is " + info);
				}
			} else if (requestCode == GET_DISTANCE_FROM_HOME) {
				Bundle extras = data.getExtras();
				if (extras != null) {
					int progress = data.getExtras().getInt("distance");
					mTextResult.setText("The distance to home is "
							+ Integer.toString(progress) + " km");
				}
			} else if (requestCode == GET_YOUR_AGE) {
				Bundle extras = data.getExtras();
				if (extras != null) {
					String age = data.getExtras().getString("age");
					mTextResult.setText("My age is " + age);
				}
			} else if (requestCode == GET_YOUR_FLAVOR) {
				Bundle extras = data.getExtras();
				if (extras != null) {
					String flavor = data.getExtras().getString("flavor");
					mTextResult.setText("Ice cream Flavor I like " + flavor);
				}
			} else if (requestCode == GET_FAVORITE_COLOR) {
				Bundle extras = data.getExtras();
				if (extras != null) {
					String color = data.getExtras().getString("radio");
					mTextResult.setText("My Favorite Color is " + color);
				}
			}

		}
	}

}
