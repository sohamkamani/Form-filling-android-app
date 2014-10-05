package com.sohamkamani.street_food;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.R.color;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends Activity implements OnClickListener {

	Button bSubmit, bPhoto, bMenu, bIngr, bLoc;
	EditText etName;
	public static FoodMenuItem[] foodMenu;
	GPSTracker gps;
	double lat, lng;
	ImageView[] imgViews;
	String mCurrentPhotoPath;
	Uri capturedImageUri;
	int imageCounter = 0;
	NumberPicker clHr, clMin, clampm, opHr, opMin, opampm;
	ToggleButton homeDel;
	TextView homeDelMinAmttxt;
	EditText homeDelMinAmt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		bSubmit = (Button) findViewById(R.id.bSubmit);
		bPhoto = (Button) findViewById(R.id.bPic);
		bMenu = (Button) findViewById(R.id.bMenu);
		bIngr = (Button) findViewById(R.id.bIngredients);
		bLoc = (Button) findViewById(R.id.bLocation);
		homeDel = (ToggleButton) findViewById(R.id.homeDelivery);
		homeDelMinAmt = (EditText) findViewById(R.id.minDelAmt);
		homeDelMinAmttxt = (TextView) findViewById(R.id.minDelAmttxt);

		clHr = (NumberPicker) findViewById(R.id.timeCloseHr);
		clMin = (NumberPicker) findViewById(R.id.timeCloseMin);
		clampm = (NumberPicker) findViewById(R.id.timeCloseampm);
		clMin.setMinValue(0);
		clMin.setMaxValue(1);
		clMin.setDisplayedValues(new String[] { "00", "30" });
		clampm.setMinValue(0);
		clampm.setMaxValue(1);
		clampm.setDisplayedValues(new String[] { "am", "pm" });
		clHr.setMinValue(1);
		clHr.setMaxValue(12);
		opHr = (NumberPicker) findViewById(R.id.timeOpenHr);
		opMin = (NumberPicker) findViewById(R.id.timeOpenMin);
		opampm = (NumberPicker) findViewById(R.id.timeOpenampm);
		opMin.setMinValue(0);
		opMin.setMaxValue(1);
		opMin.setDisplayedValues(new String[] { "00", "30" });
		opampm.setMinValue(0);
		opampm.setMaxValue(1);
		opampm.setDisplayedValues(new String[] { "am", "pm" });
		opHr.setMinValue(1);
		opHr.setMaxValue(12);

		imgViews = new ImageView[5];
		imgViews[0] = (ImageView) findViewById(R.id.img1);
		imgViews[1] = (ImageView) findViewById(R.id.img2);
		imgViews[2] = (ImageView) findViewById(R.id.img3);
		imgViews[3] = (ImageView) findViewById(R.id.img4);
		imgViews[4] = (ImageView) findViewById(R.id.img5);

		etName = (EditText) findViewById(R.id.tbName);
		bSubmit.setOnClickListener(this);
		bPhoto.setOnClickListener(this);
		bMenu.setOnClickListener(this);
		bIngr.setOnClickListener(this);
		bLoc.setOnClickListener(this);
		homeDel.setOnClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bSubmit:

			break;
		case R.id.bMenu:
			Intent intentMenu = new Intent(this, MenuEntry.class);
			startActivityForResult(intentMenu, 0);
			break;

		case R.id.bPic:
			dispatchTakePictureIntent();
			break;

		case R.id.bIngredients:
			Intent intentIngr = new Intent(this, IngredientEntry.class);
			startActivityForResult(intentIngr, 1);
			break;

		case R.id.bLocation:
			getLocation();
			gps = new GPSTracker(MainActivity.this);
			break;
			
		case R.id.homeDelivery:
			if(homeDel.isChecked()){
				homeDelMinAmttxt.setTextColor(Color.GREEN);
			}
			break;

		}
	}

	public void getLocation() {
		gps = new GPSTracker(MainActivity.this);
		if (gps.canGetLocation()) {

			lat = gps.getLatitude();
			lng = gps.getLongitude();

			// \n is for new line
			Toast.makeText(getApplicationContext(),
					"Your Location is - \nLat: " + lat + "\nLong: " + lng,
					Toast.LENGTH_LONG).show();
		} else {
			// can't get location
			// GPS or Network is not enabled
			// Ask user to enable GPS/network in settings
			gps.showSettingsAlert();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				Bundle fMenu = data.getExtras();
				String[] fm = fMenu.getStringArray("foodmenu");
				// etName.setText(fm[0]);

			}
			if (resultCode == RESULT_CANCELED) {
				// Write your code if there's no result
			}
		}
		if (requestCode == 2 && resultCode == RESULT_OK) {
			// what to do with image
			try {
				Bitmap bitmap = MediaStore.Images.Media.getBitmap(
						getApplicationContext().getContentResolver(),
						capturedImageUri);
				bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, true);
				imgViews[imageCounter % 5].setImageBitmap(bitmap);
				imageCounter++;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// EVERYTHING PHOTO RELATED DOWN HERE

	/*
	 * private void dispatchTakePictureIntent() { Intent takePicture = new
	 * Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	 * startActivityForResult(takePicture, 2); }
	 */

	private File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());
		String imageFileName = "JPEG_" + timeStamp + "_";
		File storageDir = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		File image = File.createTempFile(imageFileName, /* prefix */
				".jpg", /* suffix */
				storageDir /* directory */
		);

		// Save a file: path for use with ACTION_VIEW intents
		mCurrentPhotoPath = "file:" + image.getAbsolutePath();
		return image;
	}

	private void dispatchTakePictureIntent() {
		Calendar cal = Calendar.getInstance();
		File file = new File(Environment.getExternalStorageDirectory(),
				(cal.getTimeInMillis() + ".jpg"));
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			file.delete();
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		capturedImageUri = Uri.fromFile(file);
		Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		i.putExtra(MediaStore.EXTRA_OUTPUT, capturedImageUri);
		startActivityForResult(i, 2);

	}

}
