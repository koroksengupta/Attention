/**
 * @author Raghav Sood
 * @version 1
 * @date 26 January, 2013
 */
package com.android.attention;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * The Class CircularSeekBar.
 */
public class CircularSeekBar extends View {
	
	/** TAG */
	private static final String TAG = "Circular Seek Bar";
	
	/** The context */
	private Context mContext;

	/** The listener to listen for changes */
	private OnSeekChangeListener mListener;

	/** The color of the progress ring */
	private Paint circleColor;

	/** the color of the inside circle. Acts as background color */
	private Paint innerColor;

	/** The progress circle ring background */
	private Paint circleRing;
	
	private Paint circleText;

	/** The angle of progress */
	private int angle = 0;

	/** The start angle (12 O'clock */
	private int startAngle = 270;

	/** The width of the progress ring */
	private int barWidth = 5;

	/** The width of the view */
	private int width;

	/** The height of the view */
	private int height;

	/** The maximum progress amount */
	private int maxProgress = 100;

	/** The current progress */
	private int progress;

	/** The progress percent */
	private int progressPercent;
	
	private String distance;

	/** The radius of the inner circle */
	private float innerRadius;

	/** The radius of the outer circle */
	private float outerRadius;

	/** The circle's center X coordinate */
	private float cx;

	/** The circle's center Y coordinate */
	private float cy;

	/** The left bound for the circle RectF */
	private float left;

	/** The right bound for the circle RectF */
	private float right;

	/** The top bound for the circle RectF */
	private float top;

	/** The bottom bound for the circle RectF */
	private float bottom;

	/** The X coordinate for the top left corner of the marking drawable */
	private float dx;

	/** The Y coordinate for the top left corner of the marking drawable */
	private float dy;

	/** The X coordinate for 12 O'Clock */
	private float startPointX;

	/** The Y coordinate for 12 O'Clock */
	private float startPointY;

	/**
	 * The X coordinate for the current position of the marker, pre adjustment
	 * to center
	 */
	private float markPointX;

	/**
	 * The Y coordinate for the current position of the marker, pre adjustment
	 * to center
	 */
	private float markPointY;

	/**
	 * The adjustment factor. This adds an adjustment of the specified size to
	 * both sides of the progress bar, allowing touch events to be processed
	 * more user friendlily (yes, I know that's not a word)
	 */
	private float adjustmentFactor = 100;

	/** The progress mark when the view isn't being progress modified */
	private Bitmap progressMark;

	/** The progress mark when the view is being progress modified. */
	private Bitmap progressMarkPressed;

	/** The flag to see if view is pressed */
	private boolean IS_PRESSED = false;

	/**
	 * The flag to see if the setProgress() method was called from our own
	 * View's setAngle() method, or externally by a user.
	 */
	private boolean CALLED_FROM_ANGLE = false;

	/** The rectangle containing our circles and arcs. */
	private RectF rect = new RectF();

	{
		mListener = new OnSeekChangeListener() {

			@Override
			public void onProgressChange(CircularSeekBar view, int newProgress) {

			}
		};

		circleColor = new Paint();
		innerColor = new Paint();
		circleRing = new Paint();
		circleText = new Paint();

		circleColor.setColor(Color.parseColor("#ff33b5e5")); // Set default
																// progress
																// color to holo
																// blue.
		innerColor.setColor(Color.BLACK); // Set default background color to
											// black
		circleRing.setColor(Color.GRAY);// Set default background color to Gray
		circleText.setColor(Color.WHITE);

		circleColor.setAntiAlias(true);
		innerColor.setAntiAlias(true);
		circleRing.setAntiAlias(true);

		circleColor.setStrokeWidth(5);
		innerColor.setStrokeWidth(5);
		circleRing.setStrokeWidth(5);
		circleText.setTextSize(50);
		circleText.setTypeface(Typeface.DEFAULT_BOLD);

		circleColor.setStyle(Paint.Style.FILL);
	}

	/**
	 * Instantiates a new circular seek bar.
	 * 
	 * @param context
	 *            the context
	 * @param attrs
	 *            the attrs
	 * @param defStyle
	 *            the def style
	 */
	public CircularSeekBar(Context context, AttributeSet attrs, int defStyle) {
		
		super(context, attrs, defStyle);
		mContext = context;
		initDrawable();
		
		Log.d(TAG, "Exiting Constructor: Context, Attribute, Style");
	}

	/**
	 * Instantiates a new circular seek bar.
	 * 
	 * @param context
	 *            the context
	 * @param attrs
	 *            the attrs
	 */
	public CircularSeekBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		initDrawable();
		
		Log.d(TAG, "Exiting Constructor: Context, Attribute");
	}

	/**
	 * Instantiates a new circular seek bar.
	 * 
	 * @param context
	 *            the context
	 */
	public CircularSeekBar(Context context) {
		super(context);
		mContext = context;
		initDrawable();
		
		Log.d(TAG, "Exiting Constructor: Context");
	}

	/**
	 * Inits the drawable.
	 */
	public void initDrawable() {
		Log.d(TAG, "initDrawable:Progressmark");
		progressMark = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.scrubber_control_normal_holo);
		
		Log.d(TAG, "initDrawable:ProgressmarkPressed");
		progressMarkPressed = BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.scrubber_control_pressed_holo);
		
		Log.d(TAG, "Exiting initDrawable");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View#onMeasure(int, int)
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		Log.d(TAG, "onMeasure: Getting Width and Height");
		width = getWidth(); // Get View Width
		height = getHeight();// Get View Height

		int size = (width > height) ? height : width; // Choose the smaller
														// between width and
														// height to make a
														// square

		cx = width / 2; // Center X for circle
		cy = height / 2; // Center Y for circle
		outerRadius = size / 2; // Radius of the outer circle

		innerRadius = outerRadius - barWidth; // Radius of the inner circle

		left = cx - outerRadius; // Calculate left bound of our rect
		right = cx + outerRadius;// Calculate right bound of our rect
		top = cy - outerRadius;// Calculate top bound of our rect
		bottom = cy + outerRadius;// Calculate bottom bound of our rect

		startPointX = cx; // 12 O'clock X coordinate
		startPointY = cy - outerRadius;// 12 O'clock Y coordinate
		markPointX = startPointX;// Initial locatino of the marker X coordinate
		markPointY = startPointY;// Initial locatino of the marker Y coordinate

		rect.set(left, top, right, bottom); // assign size to rect
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		
		Log.d(TAG, "Inside OnDraw");
		dx = getXFromAngle();
		dy = getYFromAngle();

		canvas.drawCircle(cx, cy, outerRadius, circleRing);
		canvas.drawArc(rect, startAngle, angle, true, circleColor);
		canvas.drawCircle(cx, cy, innerRadius, innerColor);
		canvas.drawText(Integer.toString(getProgress())+" km", (cx - 100), cy, circleText);
		drawMarkerAtProgress(canvas);

		super.onDraw(canvas);
	}

	/**
	 * Draw marker at the current progress point onto the given canvas.
	 * 
	 * @param canvas
	 *            the canvas
	 */
	public void drawMarkerAtProgress(Canvas canvas) {
		Log.d(TAG, "Inside drawMrkeratProgress");
		if (IS_PRESSED) {
			canvas.drawBitmap(progressMarkPressed, dx, dy, null);
		} else {
			canvas.drawBitmap(progressMark, dx, dy, null);
		}
	}

	/**
	 * Gets the X coordinate of the arc's end arm's point of intersection with
	 * the circle
	 * 
	 * @return the X coordinate
	 */
	public float getXFromAngle() {
		Log.d(TAG, "getXFromAngle: size1");
		int size1 = progressMark.getWidth();
		
		Log.d(TAG, "getXFromAngle: size2");
		int size2 = progressMarkPressed.getWidth();
		
		Log.d(TAG, "getXFromAngle: adjust");
		int adjust = (size1 > size2) ? size1 : size2;
		
		
		float x = markPointX - (adjust / 2);
		return x;
	}

	/**
	 * Gets the Y coordinate of the arc's end arm's point of intersection with
	 * the circle
	 * 
	 * @return the Y coordinate
	 */
	public float getYFromAngle() {
		
		Log.d(TAG, "getXFromAngle: size1");
		int size1 = progressMark.getHeight();
		
		Log.d(TAG, "getXFromAngle: size2");
		int size2 = progressMarkPressed.getHeight();
		
		Log.d(TAG, "getXFromAngle: adjust");
		int adjust = (size1 > size2) ? size1 : size2;
		float y = markPointY - (adjust / 2);
		return y;
	}

	/**
	 * Get the angle.
	 * 
	 * @return the angle
	 */
	public int getAngle() {
		Log.d(TAG, "inside: getAngle");
		return angle;
	}

	/**
	 * Set the angle.
	 * 
	 * @param angle
	 *            the new angle
	 */
	public void setAngle(int angle) {
		
		Log.d(TAG, "Inside setAngle");
		this.angle = angle;
		float donePercent = (((float) this.angle) / 360) * 100;
		float progress = (donePercent / 100) * getMaxProgress();
		setProgressPercent(Math.round(donePercent));
		CALLED_FROM_ANGLE = true;
		setProgress(Math.round(progress));
	}
	
	public void setText(String text){
		Log.d(TAG, "Inside setText");
		this.distance = text;
	}
	
	public String getText(){
		Log.d(TAG, "Inside getText");
		return distance;
	}

	/**
	 * Sets the seek bar change listener.
	 * 
	 * @param listener
	 *            the new seek bar change listener
	 */
	public void setSeekBarChangeListener(OnSeekChangeListener listener) {
		Log.d(TAG, "Inside setSeekBarChangeListener");
		mListener = listener;
	}

	/**
	 * Gets the seek bar change listener.
	 * 
	 * @return the seek bar change listener
	 */
	public OnSeekChangeListener getSeekBarChangeListener() {
		Log.d(TAG, "Inside OnSeekChangeListener");
		return mListener;
	}

	/**
	 * Gets the bar width.
	 * 
	 * @return the bar width
	 */
	public int getBarWidth() {
		Log.d(TAG, "Inside getBarWidth");
		return barWidth;
	}

	/**
	 * Sets the bar width.
	 * 
	 * @param barWidth
	 *            the new bar width
	 */
	public void setBarWidth(int barWidth) {
		Log.d(TAG, "Inside setBarWidth");
		this.barWidth = barWidth;
	}

	/**
	 * The listener interface for receiving onSeekChange events. The class that
	 * is interested in processing a onSeekChange event implements this
	 * interface, and the object created with that class is registered with a
	 * component using the component's
	 * <code>setSeekBarChangeListener(OnSeekChangeListener)<code> method. When
	 * the onSeekChange event occurs, that object's appropriate
	 * method is invoked.
	 * 
	 * @see OnSeekChangeEvent
	 */
	public interface OnSeekChangeListener {
		
		/**
		 * On progress change.
		 * 
		 * @param view
		 *            the view
		 * @param newProgress
		 *            the new progress
		 */
		public void onProgressChange(CircularSeekBar view, int newProgress);
	}

	/**
	 * Gets the max progress.
	 * 
	 * @return the max progress
	 */
	public int getMaxProgress() {
		Log.d(TAG, "Inside getMaxProgress");
		return maxProgress;
	}

	/**
	 * Sets the max progress.
	 * 
	 * @param maxProgress
	 *            the new max progress
	 */
	public void setMaxProgress(int maxProgress) {
		Log.d(TAG, "Inside setMaxProgress");
		this.maxProgress = maxProgress;
	}

	/**
	 * Gets the progress.
	 * 
	 * @return the progress
	 */
	public int getProgress() {
		Log.d(TAG, "Inside getProgress");
		return progress;
	}

	/**
	 * Sets the progress.
	 * 
	 * @param progress
	 *            the new progress
	 */
	public void setProgress(int progress) {
		Log.d(TAG, "Inside setProgress");
		if (this.progress != progress) {
			this.progress = progress;
			if (!CALLED_FROM_ANGLE) {
				Log.d(TAG, "setProgress: !CalledFromAngle");
				int newPercent = (this.progress / this.maxProgress) * 100;
				int newAngle = (newPercent / 100) * 360;
				this.setAngle(newAngle);
				this.setProgressPercent(newPercent);
			}
			mListener.onProgressChange(this, this.getProgress());
			CALLED_FROM_ANGLE = false;
		}
	}

	/**
	 * Gets the progress percent.
	 * 
	 * @return the progress percent
	 */
	public int getProgressPercent() {
		Log.d(TAG, "getProgressPercent");
		return progressPercent;
	}

	/**
	 * Sets the progress percent.
	 * 
	 * @param progressPercent
	 *            the new progress percent
	 */
	public void setProgressPercent(int progressPercent) {
		Log.d(TAG, "setProgressPercent");
		this.progressPercent = progressPercent;
	}

	/**
	 * Sets the ring background color.
	 * 
	 * @param color
	 *            the new ring background color
	 */
	public void setRingBackgroundColor(int color) {
		Log.d(TAG, "setRingBackgroundColor");
		circleRing.setColor(color);
	}

	/**
	 * Sets the back ground color.
	 * 
	 * @param color
	 *            the new back ground color
	 */
	public void setBackGroundColor(int color) {
		Log.d(TAG, "setBackgroundColor");
		innerColor.setColor(color);
	}

	/**
	 * Sets the progress color.
	 * 
	 * @param color
	 *            the new progress color
	 */
	public void setProgressColor(int color) {
		Log.d(TAG, "setprogressColor");
		circleColor.setColor(color);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		Log.d(TAG, "inside onTouchEvent");
		float x = event.getX();
		float y = event.getY();
		boolean up = false;
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			moved(x, y, up);
			break;
		case MotionEvent.ACTION_MOVE:
			moved(x, y, up);
			break;
		case MotionEvent.ACTION_UP:
			up = true;
			moved(x, y, up);
			break;
		}
		return true;
	}

	/**
	 * Moved.
	 * 
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param up
	 *            the up
	 */
	private void moved(float x, float y, boolean up) {
		Log.d(TAG, "inside moved");
		float distance = (float) Math.sqrt(Math.pow((x - cx), 2) + Math.pow((y - cy), 2));
		
		if (distance < outerRadius + adjustmentFactor && distance > innerRadius - adjustmentFactor && !up) {
			Log.d(TAG, "moved : if block");
			IS_PRESSED = true;

			markPointX = (float) (cx + outerRadius * Math.cos(Math.atan2(x - cx, cy - y) - (Math.PI /2)));
			markPointY = (float) (cy + outerRadius * Math.sin(Math.atan2(x - cx, cy - y) - (Math.PI /2)));
			
			float degrees = (float) ((float) ((Math.toDegrees(Math.atan2(x - cx, cy - y)) + 360.0)) % 360.0);
			// and to make it count 0-360
			if (degrees < 0) {
				degrees += 2 * Math.PI;
			}

			setAngle(Math.round(degrees));
			invalidate();

		} else {
			IS_PRESSED = false;
			invalidate();
		}

	}

	/**
	 * Gets the adjustment factor.
	 * 
	 * @return the adjustment factor
	 */
	public float getAdjustmentFactor() {
		Log.d(TAG, "inside getAdjustmentFactor");
		return adjustmentFactor;
	}

	/**
	 * Sets the adjustment factor.
	 * 
	 * @param adjustmentFactor
	 *            the new adjustment factor
	 */
	public void setAdjustmentFactor(float adjustmentFactor) {
		Log.d(TAG, "inside setAdjustmentFactor");
		this.adjustmentFactor = adjustmentFactor;
	}
}
