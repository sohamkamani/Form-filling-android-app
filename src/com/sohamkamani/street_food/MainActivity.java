package com.sohamkamani.street_food;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	Button bSubmit, bPhoto, bMenu, bIngr, bLoc;
	EditText etName;
	public static FoodMenuItem[] foodMenu;
	GPSTracker gps;
	double lat,lng;
	ImageView imgV1;
	String mCurrentPhotoPath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		bSubmit = (Button) findViewById(R.id.bSubmit);
		bPhoto = (Button) findViewById(R.id.bPic);
		bMenu = (Button) findViewById(R.id.bMenu);
		bIngr = (Button) findViewById(R.id.bIngredients);
		bLoc = (Button) findViewById(R.id.bLocation);
		imgV1 = (ImageView) findViewById(R.id.imageView1);

		etName = (EditText) findViewById(R.id.tbName);
		bSubmit.setOnClickListener(this);
		bPhoto.setOnClickListener(this);
		bMenu.setOnClickListener(this);
		bIngr.setOnClickListener(this);
		bLoc.setOnClickListener(this);
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

		}
	}
	
	public void getLocation(){
			gps = new GPSTracker(MainActivity.this);
			if(gps.canGetLocation()){
	        	
	        	lat = gps.getLatitude();
	        	lng = gps.getLongitude();
	        	
	        	// \n is for new line
	        	Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + lat + "\nLong: " + lng, Toast.LENGTH_LONG).show();	
	        }else{
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
	        Bundle extras = data.getExtras();
	        Bitmap imageBitmap = (Bitmap) extras.get("data");
	        imgV1.setImageBitmap(imageBitmap);
	    }
	}
	
	
	private void dispatchTakePictureIntent() {
	    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
	        startActivityForResult(takePictureIntent, 2);
	    }
	}
	
	private File createImageFile() throws IOException {
	    // Create an image file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    String imageFileName = "JPEG_" + timeStamp + "_";
	    File storageDir = Environment.getExternalStoragePublicDirectory(
	            Environment.DIRECTORY_PICTURES);
	    File image = File.createTempFile(
	        imageFileName,  /* prefix */
	        ".jpg",         /* suffix */
	        storageDir      /* directory */
	    );

	    // Save a file: path for use with ACTION_VIEW intents
	    mCurrentPhotoPath = "file:" + image.getAbsolutePath();
	    return image;
	}
}
