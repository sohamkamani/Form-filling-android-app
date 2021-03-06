package com.sohamkamani.street_food;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;

import org.apache.http.entity.mime.MinimalField;

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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.gson.Gson;

public class MainActivity extends Activity implements OnClickListener {

	Button bSubmit, bPhoto, bMenu, bIngr, bLoc;
	Gson gson = new Gson();

	EditText etName, etPhone, etAddr, etLandmark, etOthercui, homeDelMinAmt,
			avgPrice, sigDish;
	public static FoodMenuItem[] foodMenu;
	GPSTracker gps;
	double lat, lng;
	ImageView[] imgViews;
	String mCurrentPhotoPath, imgFilePath,ingrResult,menuResult;
	Uri capturedImageUri;
	int imageCounter = 0;
	NumberPicker clHr, clMin, clampm, opHr, opMin, opampm;
	ToggleButton homeDel;
	TextView homeDelMinAmttxt, tbStatus;
	Bitmap[] foodPhoto = new Bitmap[5];
	String[] imageStrings = new String[5];

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
		tbStatus = (TextView) findViewById(R.id.tbStatus);

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

		homeDelMinAmttxt.setTextColor(Color.GRAY);
		homeDelMinAmt.setEnabled(false);
		
		lat=0;
		lng=0;
		
		ingrResult = "N/a";
		menuResult = "N/a";

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

	private String[] make_data() {
		String[] data = new String[18];
		for(int i = 0 ; i < 18 ; i++){
			data[i] = "N/A";
		}
		EditText et;
		
		//1
		et=(EditText) findViewById(R.id.tbName);
		if(et.getText().length()>0){
			data[0] = et.getText().toString();
		}
		
		//2
		et=(EditText) findViewById(R.id.tbPhone);
		if(et.getText().length()>0){
			data[1] = et.getText().toString();
		}
		
		//3
		et=(EditText) findViewById(R.id.tbAddress);
		if(et.getText().length()>0){
			data[2] = et.getText().toString();
		}
		
		//4
		et=(EditText) findViewById(R.id.tbLandmark);
		if(et.getText().length()>0){
			data[3] = et.getText().toString();
		}
		
		//5
		CheckBox veg,nonveg;
		veg = (CheckBox) findViewById(R.id.checkBoxveg);
		nonveg = (CheckBox) findViewById(R.id.checkBoxnonveg);
		if(veg.isChecked()){
			if(nonveg.isChecked()){
				data[4] = "V & NV";
			}else{
				data[4] = "V";
			}
		}else if(nonveg.isChecked()){
			data[4]="NV";
		}
		
		//6
		RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroupCui);
		int rb_id =rg.getCheckedRadioButtonId();
		RadioButton rb = (RadioButton) findViewById(rb_id);
		if(rb.getText().toString() == "Other:"){
			EditText cui_et = (EditText) findViewById(R.id.tbOther);
			data[5] = cui_et.getText().toString();
		}else{
			data[5] = rb.getText().toString();
		}
		
		//7 and 8
		float rating;
		RatingBar rb_clean = (RatingBar) findViewById(R.id.rCleanliness);
		rating = rb_clean.getRating();
		data[6] = Float.toString(rating);
		RatingBar rb_qual = (RatingBar) findViewById(R.id.rFood);
		rating = rb_qual.getRating();
		data[7] = Float.toString(rating);
		
		//9 and 10
		if(homeDel.isChecked()){
			data[8]="yes";
			data[9] = homeDelMinAmt.getText().toString();
		}else{
			data[8] = "no";
		}
		
		//11 and 12
		data[10] = construct_time(opHr.getValue(), opMin.getValue(), opampm.getValue());
		data[11] = construct_time(clHr.getValue(), clMin.getValue(), clampm.getValue());
		
		//13
		et=(EditText) findViewById(R.id.tbPrice);
		if(et.getText().length()>0){
			data[12] = et.getText().toString();
		}
		
		//14
		et=(EditText) findViewById(R.id.tbDish);
		if(et.getText().length()>0){
			data[13] = et.getText().toString();
		}
		
		//15 and 16
		data[14] = Double.toString(lat);
		data[15] = Double.toString(lng);
		
		//17 and 18
		data[16] = menuResult;
		data[17] = ingrResult;
		
		return data;
	}
	
	private String construct_time(int hr, int min, int ampm){
		String time = Integer.toString(hr) + ":";
		if(min==0){
			time+="00";
		}else{
			time+="30";
		}
		if(ampm==0)
			time+=" am";
		else
			time+=" pm";
		return time;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bSubmit:
//			tbStatus.setText("Submitting...");
//			// new UploadingData(this,tbStatus).execute("AAnd","roid");
//			final Uploader uploader = new Uploader(tbStatus, bSubmit);
//			// for (int i = 0; (i < 5 && foodPhoto[i] != null); i++) {
//			// imgup.upload_image(foodPhoto[i], etName.getText().toString(), i);
//			// }
//			Thread t = new Thread(new Runnable() {
//
//				@Override
//				public void run() {
//					// TODO Auto-generated method stub
//					for (int i = 0; (i < 5 && imageStrings[i] != null); i++) {
//						uploader.upload_image(imageStrings[i], etName.getText()
//								.toString(), i);
//					}
//					String[] data = make_data();
//					uploader.upload_data(data);
//				}
//			});
//			t.start();
			String[] total_data = make_data();
			String tot_data=" ";
			for(int i=0 ; i<18; i++){
				tot_data = tot_data + total_data[i] + " # ";
			}
			tbStatus.setText(tot_data);

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
			if (homeDel.isChecked()) {
				homeDelMinAmttxt.setTextColor(Color.BLACK);
				homeDelMinAmt.setEnabled(true);
			} else {
				homeDelMinAmttxt.setTextColor(Color.GRAY);
				homeDelMinAmt.setEnabled(false);
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
		if (requestCode == 0 && resultCode == RESULT_OK) {

			menuResult = data.getStringExtra("menuString");

		}
		
		if (requestCode == 1 && resultCode == RESULT_OK) {

			ingrResult = data.getStringExtra("ingredients");

		}

		if (requestCode == 2 && resultCode == RESULT_OK) {
			// what to do with image
			try {
				Bitmap bitmap = MediaStore.Images.Media.getBitmap(
						getApplicationContext().getContentResolver(),
						capturedImageUri);
				if (bitmap.getWidth() < bitmap.getHeight()) {
					bitmap = Bitmap.createScaledBitmap(bitmap, 720, 1280, true);
				} else {
					bitmap = Bitmap.createScaledBitmap(bitmap, 1280, 720, true);
				}

				// foodPhoto[imageCounter % 5] = bitmap;
				imageStrings[imageCounter % 5] = imageToString(bitmap);
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

	// private File createImageFile() throws IOException {
	// // Create an image file name
	// String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
	// .format(new Date());
	// String imageFileName = "JPEG_" + timeStamp + "_";
	// File storageDir = Environment
	// .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
	// File image = File.createTempFile(imageFileName, /* prefix */
	// ".jpg", /* suffix */
	// storageDir /* directory */
	// );
	//
	// // Save a file: path for use with ACTION_VIEW intents
	// mCurrentPhotoPath = "file:" + image.getAbsolutePath();
	// return image;
	// }

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
		// imgFilePath = file.getAbsolutePath();
		Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		i.putExtra(MediaStore.EXTRA_OUTPUT, capturedImageUri);
		startActivityForResult(i, 2);

	}

	public String imageToString(Bitmap bitmap) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream); // compress to
																	// format //
																	// you want.
		byte[] byte_arr = stream.toByteArray();
		// String image_str = Base64custom.encodeBytes(byte_arr);
		String image_str = Base64custom.encodeBytes(byte_arr);

		return image_str;
	}

}
