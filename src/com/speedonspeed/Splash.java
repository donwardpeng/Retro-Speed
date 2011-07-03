package com.speedonspeed;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
*
*
 */
public class Splash extends Activity implements View.OnClickListener {
	private ViewGroup mLayout1;
	private ImageView mTopImageView;
	private ImageView mRotatingImageView;
	private ImageView mBottomImageView;
	private Integer mCount = 000;
	private String mStringCount = "000";
	static private Integer mCurrentView = 1;
	private String unitsOfSpeed = "mph";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//load the layout 
		setContentView(R.layout.animations_main_screen);

		// Setup all of the text views
		//setUpTextViews();

		//get the Linear Layout View
		mLayout1 = (ViewGroup) findViewById(R.id.layout1);

		//get the Top Image View
		mTopImageView = (ImageView)findViewById(R.id.topDigit);
		mTopImageView.setOnClickListener(this);
		
		//get the Rotating Image View
		mRotatingImageView = (ImageView)findViewById(R.id.rotatingDigit);
		//mRotatingImageView.setOnClickListener(this);
		
		//get the Bottom Image View
		mBottomImageView=(ImageView)findViewById(R.id.bottomDigit);
		mBottomImageView.setOnClickListener(this);
		
		// Since we are caching large views, we want to keep their cache
		// between each animation
		mLayout1.setPersistentDrawingCache(ViewGroup.PERSISTENT_ANIMATION_CACHE);

		/* Use the LocationManager class to obtain GPS locations */
		LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		LocationListener mlocListener = new CustomLocationListener();
		mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
				mlocListener);
		mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,
				0, mlocListener);
	}

	/**
	 * Setup a new 3D rotation on the passed in ImageView.
	 * 
	 * @param position
	 *            the item that was clicked to show a picture, or -1 to show the
	 *            list
	 * @param start
	 *            the start angle at which the rotation must begin
	 * @param end
	 *            the end angle of the rotation
	 *            
	 * @param imageView 
	 * 			   the image to rotate            
	 */
	private void applyRotation(int position, float start, float end,
			ImageView imageView) {
		final float centerX = imageView.getWidth() / 2.0f;
		final float centerY = imageView.getHeight();

		// Create a new 3D rotation with the supplied parameter
		// The animation listener is used to trigger the next animation
		final Rotate3dAnimation rotation = new Rotate3dAnimation(start, end,
				centerX, centerY, -20.0f, true);
		rotation.setDuration(250);
		rotation.setFillAfter(true);
		rotation.setInterpolator(new AccelerateInterpolator());
		rotation.setAnimationListener(new DisplayNextView(position));

		imageView.startAnimation(rotation);
	}

	public void onClick(View v) {
		mRotatingImageView.setImageResource(R.drawable.digit1top);
		mRotatingImageView.setVisibility(View.VISIBLE);	
		applyRotation(2, 0, -90, mRotatingImageView);
		return;
	}

	public void updateSpeed() {
		if (mCurrentView == 1) {
			mCurrentView = 2;
			
			applyRotation(2, 0, 90, mTopImageView);
			return;
		} else {
			mCurrentView = 1;
			applyRotation(1, 0, 90, mBottomImageView);
			return;
		}
	}

	/**
	 * This class listens for the end of the first half of the animation. It
	 * then posts a new action that effectively swaps the views when the
	 * container is rotated 90 degrees and thus invisible.
	 */
	private final class DisplayNextView implements Animation.AnimationListener {
		private final int mPosition;

		private DisplayNextView(int position) {
			mPosition = position;
		}

		public void onAnimationStart(Animation animation) {
		}

		public void onAnimationEnd(Animation animation) {
			mLayout1.post(new SwapViews(mPosition));
		}

		public void onAnimationRepeat(Animation animation) {
		}
	}

	/**
	 * This class is responsible for swapping the views and start the second
	 * half of the animation.
	 */
	private final class SwapViews implements Runnable {
		private final int mPosition;

		public SwapViews(int position) {
			mPosition = position;
		}

		public void run() {
			Rotate3dAnimation rotation;

				float centerX = mRotatingImageView.getWidth() / 2.0f;
				float centerY = mRotatingImageView.getHeight() ;				
								
				mRotatingImageView.setImageResource(R.drawable.digit1bottomflipped);
				
				rotation = new Rotate3dAnimation(-90, -180, centerX, centerY,
						-20.0f, false);
				rotation.setDuration(250);
				rotation.setFillAfter(true);
				rotation.setInterpolator(new DecelerateInterpolator());
				mRotatingImageView.startAnimation(rotation);
//				mRotatingImageView.setVisibility(View.GONE);
		}
	}

	private class CustomLocationListener implements LocationListener {
		@Override
		public void onLocationChanged(Location loc) {
			Integer mphSpeed = 0;
			Integer kphSpeed = 0;
			Integer speedToDisplay = 0;

			loc.getSpeed();
			Float floatSpeed = new Float(loc.getSpeed());
			Integer intMeterPerSecSpeed = floatSpeed.intValue();
			if (unitsOfSpeed.equals("mph")) {
				// m/s to mph is multiply by 1609m/mile and divide by 3600s/h
				mphSpeed = (intMeterPerSecSpeed * 1609) / 3600;
			}

			if (unitsOfSpeed.equals("kph")) {
				// m/s to kph is multiply by 1000m/km and divide by 3600s/h
				kphSpeed = (intMeterPerSecSpeed * 1000) / 3600;
			}

			speedToDisplay = (unitsOfSpeed.equals("mph")) ? mphSpeed : kphSpeed;

			if (Splash.mCurrentView == 2) {
				TextView text1 = (TextView) findViewById(android.R.id.text1);
				Integer text1CurrentSpeed = Integer.parseInt((text1.getText())
						.toString());
				if (!text1CurrentSpeed.equals(speedToDisplay)) {
					text1.setText(String.format("%03d",
							speedToDisplay.intValue()));
					updateSpeed();
				}
			} else {
				TextView text2 = (TextView) findViewById(android.R.id.text2);
				Integer text2CurrentSpeed = Integer.parseInt(text2.getText()
						.toString());
				if (!text2CurrentSpeed.equals(speedToDisplay)) {
					text2.setText(String.format("%03d",
							speedToDisplay.intValue()));
					updateSpeed();
				}
			}
		}

		@Override
		public void onProviderDisabled(String provider) {
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	}
}
