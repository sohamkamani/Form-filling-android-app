package com.sohamkamani.street_food;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.gson.Gson;

public class MenuEntry extends Activity implements OnClickListener {

	int viewCount;
	EditText fName, fPrice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_entry);
		Button bSubmit = (Button) findViewById(R.id.bMenuSubmit);
		bSubmit.setOnClickListener(this);
		Button bAddMore = (Button) findViewById(R.id.bAddItem);
		bAddMore.setOnClickListener(this);
		viewCount = 6;

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_entry, menu);
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
		case R.id.bMenuSubmit:
			Intent i = new Intent();
			i.putExtra("menuString", getMenuString());
			setResult(RESULT_OK, i);
			finish();
			break;
		case R.id.bAddItem:
			addTextboxes();
			break;
		}
	}

	@SuppressWarnings("deprecation")
	public void addTextboxes() {

		// Find the ScrollView
		LinearLayout mainLl = (LinearLayout) findViewById(R.id.menuEditTextLayout);

		// Create a LinearLayout element
		LinearLayout ll = new LinearLayout(this);

		LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1.0f);

		ll.setOrientation(LinearLayout.HORIZONTAL);
		ll.setGravity(Gravity.CENTER);
		ll.setLayoutParams(param1);

		// Add text
		EditText et1 = new EditText(this);
		et1.setLayoutParams(param2);
		et1.setEms(10);
		et1.setId(viewCount);
		viewCount++;
		ll.addView(et1);

		EditText et2 = new EditText(this);
		et2.setLayoutParams(param2);
		et2.setInputType(InputType.TYPE_CLASS_NUMBER);
		et2.setEms(10);
		et2.setId(viewCount);
		viewCount++;
		ll.addView(et2);

		// Add the LinearLayout element to the ScrollView
		mainLl.addView(ll);

	}

	public String getMenuString() {
		String name;
		int price;
		ArrayList<FoodMenuItem> fmi = new ArrayList<FoodMenuItem>();
		fName = (EditText) findViewById(R.id.name1);
		fPrice = (EditText) findViewById(R.id.price1);
		if (fName.getText().length() > 0 && fPrice.getText().length() > 0) {
			name = fName.getText().toString();
			price = Integer.parseInt(fPrice.getText().toString());
			fmi.add(new FoodMenuItem(name, price));
		}
		fName = (EditText) findViewById(R.id.name2);
		fPrice = (EditText) findViewById(R.id.price2);
		if (fName.getText().length() > 0 && fPrice.getText().length() > 0) {
			name = fName.getText().toString();
			price = Integer.parseInt(fPrice.getText().toString());
			fmi.add(new FoodMenuItem(name, price));
		}
		fName = (EditText) findViewById(R.id.name3);
		fPrice = (EditText) findViewById(R.id.price3);
		if (fName.getText().length() > 0 && fPrice.getText().length() > 0) {
			name = fName.getText().toString();
			price = Integer.parseInt(fPrice.getText().toString());
			fmi.add(new FoodMenuItem(name, price));
		}
		fName = (EditText) findViewById(R.id.name4);
		fPrice = (EditText) findViewById(R.id.price4);
		if (fName.getText().length() > 0 && fPrice.getText().length() > 0) {
			name = fName.getText().toString();
			price = Integer.parseInt(fPrice.getText().toString());
			fmi.add(new FoodMenuItem(name, price));
		}
		fName = (EditText) findViewById(R.id.name5);
		fPrice = (EditText) findViewById(R.id.price5);
		if (fName.getText().length() > 0 && fPrice.getText().length() > 0) {
			name = fName.getText().toString();
			price = Integer.parseInt(fPrice.getText().toString());
			fmi.add(new FoodMenuItem(name, price));
		}
		if (viewCount > 6) {
			int tempCount = 6;
			while (tempCount < viewCount) {
				fName = (EditText) findViewById(tempCount);
				tempCount++;
				fPrice = (EditText) findViewById(tempCount);
				tempCount++;
				if (fName.getText().length() > 0 && fPrice.getText().length() > 0) {
					name = fName.getText().toString();
					price = Integer.parseInt(fPrice.getText().toString());
					fmi.add(new FoodMenuItem(name, price));
				}
			}
		}
		Gson gson = new Gson();
		return gson.toJson(fmi);
	}
}
