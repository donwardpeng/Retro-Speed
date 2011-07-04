package com.speedonspeed;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
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
*Splash class
*
*NOTE: Member variables prefixed by 'digit#' refer to one of the three digits
*used to represent speed or count on the screen. The digits are labelled from left
*to right on the screen, with digit 1 being on the far right and digit 3 being on the far
*left.
*
 */
public class Splash extends Activity implements View.OnClickListener {
	private ViewGroup digit1RelativeView;
	private ImageView digit1TopImageView;
	private ImageView digit1RotatingImageView;
	private ImageView digit1BottomImageView;
	private Integer digit1CurrentValue;
	private Integer digit1NewValue;
	private boolean digit1UpdateInProgress;

	private ViewGroup digit2RelativeView;
	private ImageView digit2TopImageView;
	private ImageView digit2RotatingImageView;
	private ImageView digit2BottomImageView;
	private Integer digit2CurrentValue;
	private Integer digit2NewValue;
	private boolean digit2UpdateInProgress;

	private ViewGroup digit3RelativeView;
	private ImageView digit3TopImageView;
	private ImageView digit3RotatingImageView;
	private ImageView digit3BottomImageView;
	private Integer digit3CurrentValue;
	private Integer digit3NewValue;
	private boolean digit3UpdateInProgress;

	private Integer onClickCount = 000;
	private Integer currentSpeed = 000;
	private Integer colorTint = 0x00000000;
		
	/**
	 *unitsOfSpeed sets the speed units in either "mph" or "kph"
	 */
	private String unitsOfSpeed = "mph";

	/**
	 * flipTime sets the time for each digit to flip
	 */
	private int flipTime = 200;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//load the layout 
		setContentView(R.layout.animations_main_screen);

		//Initialize Digit 1 
		setUpDigit1();
		
		//Initialize Digit 2 
		setUpDigit2();
		
		//Initialize Digit 3 
		setUpDigit3();

		//Use the LocationManager class to obtain GPS locations
		LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		LocationListener mlocListener = new CustomLocationListener();
		mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
				mlocListener);
		mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,
				0, mlocListener);
	}
	
	@Override
	protected void onStart() {

		super.onStart();
//		updateSpeed(0,999);

		
	}



	/**
	 * setupDigit1 is used to initialize Digit 1 to display "0" and setup the 
	 * click listener for the digit
	 * 
	 * @return void
	 */
	private void setUpDigit1() {
		
		//set the current value of digit 1 to zero
		digit1CurrentValue = 0;
		
		//get the Linear Layout View
		digit1RelativeView = (ViewGroup) findViewById(R.id.digit1);

		//Set the Lighting Color Filter
		LightingColorFilter lf = new LightingColorFilter(0xFFFFFFFF,colorTint);		
		
		//get the Top Image View
		digit1TopImageView = (ImageView)findViewById(R.id.digit1Top);
		digit1TopImageView.setOnClickListener(this);	
		digit1TopImageView.setColorFilter(lf);
		
		//get the Rotating Image View
		digit1RotatingImageView = (ImageView)findViewById(R.id.digit1Rotating);
		digit1RotatingImageView.setColorFilter(lf);
		
		//get the Bottom Image View
		digit1BottomImageView=(ImageView)findViewById(R.id.digit1Bottom);
		digit1BottomImageView.setOnClickListener(this);
		digit1BottomImageView.setColorFilter(lf);
		
		// Since we are caching large views, we want to keep their cache
		// between each animation
		digit1RelativeView.setPersistentDrawingCache(ViewGroup.PERSISTENT_ANIMATION_CACHE);
	}

	/**
	 * setupDigit2 is used to initialize Digit 2 to display "0" and setup the 
	 * click listener for the digit
	 * 
	 * @return void
	 */
	private void setUpDigit2() {

		//set the current value of digit 2 to zero
		digit2CurrentValue = 0;

		//get the Linear Layout View
		digit2RelativeView = (ViewGroup) findViewById(R.id.digit2);

		//Set the Lighting Color Filter
		LightingColorFilter lf = new LightingColorFilter(0xFFFFFFFF,colorTint);		
		
		//get the Top Image View
		digit2TopImageView = (ImageView)findViewById(R.id.digit2Top);
		digit2TopImageView.setOnClickListener(this);	
		digit2TopImageView.setColorFilter(lf);
		
		//get the Rotating Image View
		digit2RotatingImageView = (ImageView)findViewById(R.id.digit2Rotating);
		digit2RotatingImageView.setColorFilter(lf);
		
		//get the Bottom Image View
		digit2BottomImageView=(ImageView)findViewById(R.id.digit2Bottom);
		digit2BottomImageView.setOnClickListener(this);
		digit2BottomImageView.setColorFilter(lf);
		
		// Since we are caching large views, we want to keep their cache
		// between each animation
		digit2RelativeView.setPersistentDrawingCache(ViewGroup.PERSISTENT_ANIMATION_CACHE);
	}

	/**
	 * setupDigit3 is used to initialize Digit 3 to display "0" and setup the 
	 * click listener for the digit
	 * 
	 * @return void
	 */
	private void setUpDigit3() {
		
		//set the current value of digit 3 to zero
		digit3CurrentValue = 0;
		
		//get the Linear Layout View
		digit3RelativeView = (ViewGroup) findViewById(R.id.digit3);

		//Set the Lighting Color Filter
		LightingColorFilter lf = new LightingColorFilter(0xFFFFFFFF,colorTint);		
		
		//get the Top Image View
		digit3TopImageView = (ImageView)findViewById(R.id.digit3Top);
		digit3TopImageView.setOnClickListener(this);	
		digit3TopImageView.setColorFilter(lf);
		
		//get the Rotating Image View
		digit3RotatingImageView = (ImageView)findViewById(R.id.digit3Rotating);
		digit3RotatingImageView.setColorFilter(lf);
		
		//get the Bottom Image View
		digit3BottomImageView=(ImageView)findViewById(R.id.digit3Bottom);
		digit3BottomImageView.setOnClickListener(this);
		digit3BottomImageView.setColorFilter(lf);
		
		// Since we are caching large views, we want to keep their cache
		// between each animation
		digit3RelativeView.setPersistentDrawingCache(ViewGroup.PERSISTENT_ANIMATION_CACHE);
	}
	
/**
 * onClick(View v) - Click handler for all three digits. It calls updateDigits
 * passing in the old and new count values to be displayed by the digits
 * 
 * @return void
 */
	public void onClick(View v) {
		updateDigits(onClickCount, ++onClickCount);
	}

	/**
	 * updateSpeed() - Called to update the speed displayed by the digits.
	 * 
	 * @param oldSpeed - old speed value
	 * 
	 * @param newSpeed - new speed value
	 * 
	 * @return void
	 */
	public void updateSpeed(Integer oldSpeed, Integer newSpeed) {
		updateDigits(oldSpeed, newSpeed);
	}
	
/**
 * updateDigits() -  Will redraw the three digits with the proper value.
 *   
 * @param oldValue - the initial value of the digits
 * 
 * @param newValue - the ending value of the digits
 * 
 * @return void
 */	
private void updateDigits(Integer oldValue, Integer newValue) {
	
	
	boolean digit1UpdateRequired = false;
	boolean digit2UpdateRequired = false;
	boolean digit3UpdateRequired = false;

	//parse out the digits in the currentClickCount
	String currentClickCount = String.format("%03d", oldValue.intValue());
	digit1CurrentValue = (currentClickCount.length() == 3) ? Integer.parseInt(currentClickCount.substring(2,3)): 0;
	digit2CurrentValue = (currentClickCount.length() >= 2) ? Integer.parseInt(currentClickCount.substring(1,2)): 0;
	digit3CurrentValue = (currentClickCount.length() >= 1) ? Integer.parseInt(currentClickCount.substring(0,1)): 0;
	
	//parse out the digits in the newClickCount
	String newClickCount = String.format("%03d", newValue.intValue());
	digit1NewValue = (newClickCount.length() == 3) ? Integer.parseInt(newClickCount.substring(2,3)): 0;
	digit2NewValue = (newClickCount.length() >= 2) ? Integer.parseInt(newClickCount.substring(1,2)): 0;
	digit3NewValue = (newClickCount.length() >= 1) ? Integer.parseInt(newClickCount.substring(0,1)): 0;
			
	//determine which digits require updates
	digit1UpdateRequired = (digit1CurrentValue != digit1NewValue) ? true: false;
	digit2UpdateRequired = (digit2CurrentValue != digit2NewValue) ? true: false;
	digit3UpdateRequired = (digit3CurrentValue != digit3NewValue) ? true: false;

	//update digit 1 if required
	if(digit1UpdateRequired && !digit1UpdateInProgress){
	String rotatingDigit = "com.speedonspeed:drawable/digit" + digit1CurrentValue + "top";
	int rotatingDigitResource = this.getResources().getIdentifier(rotatingDigit, null, null);			
	digit1RotatingImageView.setImageResource(rotatingDigitResource);
	digit1RotatingImageView.setVisibility(View.VISIBLE);	
	String topDigit = "com.speedonspeed:drawable/digit" + digit1NewValue + "top";
	int topDigitResource = this.getResources().getIdentifier(topDigit, null, null);
	digit1TopImageView.setImageResource(topDigitResource);
	digit1UpdateInProgress = true;
	applyRotation(0, 0, -90, digit1RotatingImageView);
	}
	
	//update digit 2 if required
	if(digit2UpdateRequired && !digit2UpdateInProgress){
	String rotatingDigit = "com.speedonspeed:drawable/digit" + digit2CurrentValue + "top";
	int rotatingDigitResource = this.getResources().getIdentifier(rotatingDigit, null, null);			
	digit2RotatingImageView.setImageResource(rotatingDigitResource);
	digit2RotatingImageView.setVisibility(View.VISIBLE);	
	String topDigit = "com.speedonspeed:drawable/digit" + digit2NewValue + "top";
	int topDigitResource = this.getResources().getIdentifier(topDigit, null, null);
	digit2TopImageView.setImageResource(topDigitResource);
	digit2UpdateInProgress = true;
	applyRotation(0, 0, -90, digit2RotatingImageView);
	}
	
	//update digit 3 if required
	if(digit3UpdateRequired && !digit3UpdateInProgress){
	String rotatingDigit = "com.speedonspeed:drawable/digit" + digit3CurrentValue + "top";
	int rotatingDigitResource = this.getResources().getIdentifier(rotatingDigit, null, null);			
	digit3RotatingImageView.setImageResource(rotatingDigitResource);
	digit3RotatingImageView.setVisibility(View.VISIBLE);	
	String topDigit = "com.speedonspeed:drawable/digit" + digit3NewValue + "top";
	int topDigitResource = this.getResources().getIdentifier(topDigit, null, null);
	digit3TopImageView.setImageResource(topDigitResource);
	digit3UpdateInProgress = true;
	applyRotation(0, 0, -90, digit3RotatingImageView);
	}
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
	private void applyRotation(int position, float start, float end, ImageView imageView) {
		final float centerX = imageView.getWidth() / 2.0f;
		final float centerY = imageView.getHeight();
		// Create a new 3D rotation with the supplied parameter
		// The animation listener is used to trigger the next animation
		final Rotate3dAnimation rotation = new Rotate3dAnimation(start, end, centerX, centerY, -20.0f, true);
		rotation.setDuration(flipTime);
		rotation.setFillAfter(true);
		rotation.setInterpolator(new AccelerateInterpolator());
		//Setup the Animation Lifecycle listener to handle the second half of each digits 
		//flip sequence
		rotation.setAnimationListener(new DisplayNextView());
		imageView.startAnimation(rotation);
	}

	/**
	 * The DisplayNextView class is the animation lifecycle listener registered
	 * with each digits animation sequence. At the end of each digits flip 
	 * sequence it will post a message and initialize SwapViews which 
	 * will finish the animation sequence.
	 */
	private final class DisplayNextView implements Animation.AnimationListener {
		public void onAnimationStart(Animation animation) {
		}

		public void onAnimationEnd(Animation animation) {
			digit1RelativeView.post(new SwapViews());
		}
		
		public void onAnimationRepeat(Animation animation) {
		}
	}

	/**
	 * The SwapViews class is responsible for completing the animation
	 * of each digit. It takes care of the 'second' half of the animation 
	 * sequence for each digit.
	 */
	private final class SwapViews implements Runnable {
		
		public SwapViews() {
		}
		
		public void run() {
			Rotate3dAnimation rotation;	
			if(digit1UpdateInProgress){
				String rotatingDigit = "com.speedonspeed:drawable/digit" + digit1NewValue + "bottomflipped";
				int rotatingDigitResource = getResources().getIdentifier(rotatingDigit, null, null);			
				digit1RotatingImageView.setImageResource(rotatingDigitResource);
				digit1UpdateInProgress = false;
				float centerX = digit1RotatingImageView.getWidth() / 2.0f;
				float centerY = digit1RotatingImageView.getHeight() ;				
				rotation = new Rotate3dAnimation(-90, -180, centerX, centerY,-20.0f, false);
				rotation.setDuration(flipTime);
				rotation.setFillAfter(true);
				rotation.setInterpolator(new DecelerateInterpolator());
				digit1RotatingImageView.startAnimation(rotation);
				}
			
			if(digit2UpdateInProgress){
				String rotatingDigit = "com.speedonspeed:drawable/digit" + digit2NewValue + "bottomflipped";
				int rotatingDigitResource = getResources().getIdentifier(rotatingDigit, null, null);			
				digit2RotatingImageView.setImageResource(rotatingDigitResource);
				digit2UpdateInProgress = false;
				float centerX = digit2RotatingImageView.getWidth() / 2.0f;
				float centerY = digit2RotatingImageView.getHeight() ;				
				rotation = new Rotate3dAnimation(-90, -180, centerX, centerY,-20.0f, false);
				rotation.setDuration(flipTime);
				rotation.setFillAfter(true);
				rotation.setInterpolator(new DecelerateInterpolator());
				digit2RotatingImageView.startAnimation(rotation);
				}
			
			if(digit3UpdateInProgress){
				String rotatingDigit = "com.speedonspeed:drawable/digit" + digit3NewValue + "bottomflipped";
				int rotatingDigitResource = getResources().getIdentifier(rotatingDigit, null, null);			
				digit3RotatingImageView.setImageResource(rotatingDigitResource);
				digit3UpdateInProgress = false;
				float centerX = digit3RotatingImageView.getWidth() / 2.0f;
				float centerY = digit3RotatingImageView.getHeight() ;				
				rotation = new Rotate3dAnimation(-90, -180, centerX, centerY,-20.0f, false);
				rotation.setDuration(flipTime);
				rotation.setFillAfter(true);
				rotation.setInterpolator(new DecelerateInterpolator());
				digit3RotatingImageView.startAnimation(rotation);
				}
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

			if (currentSpeed != speedToDisplay)
			{	
				Integer tempSpeed = currentSpeed;
				currentSpeed = speedToDisplay;
				updateSpeed(tempSpeed, speedToDisplay);
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
