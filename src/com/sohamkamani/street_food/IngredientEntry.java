package com.sohamkamani.street_food;

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

public class IngredientEntry extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ingredient_entry);
		Button bSubmit = (Button) findViewById(R.id.bMenuSubmit);
		bSubmit.setOnClickListener(this);
		Button bAddMore = (Button) findViewById(R.id.bAddItem);
		bAddMore.setOnClickListener(this);
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
			String[] fm = new String[2];
			fm[0] = "tre,6";
			fm[1] = "rer,67";
			Intent i = new Intent();
			Bundle menuResult = new Bundle();
			menuResult.putStringArray("foodmenu", fm);
			i.putExtras(menuResult);
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
		ll.addView(et1);

		EditText et2 = new EditText(this);
		et2.setLayoutParams(param2);
		et2.setInputType(InputType.TYPE_CLASS_NUMBER);
		et2.setEms(10);
		ll.addView(et2);

		// Add the LinearLayout element to the ScrollView
		mainLl.addView(ll);

	}
}
