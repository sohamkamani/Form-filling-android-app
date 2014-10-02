package com.sohamkamani.street_food;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	Button bSubmit, bPhoto, bMenu, bIngr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		bSubmit = (Button) findViewById(R.id.bSubmit);
		bPhoto = (Button) findViewById(R.id.bPic);
		bMenu = (Button) findViewById(R.id.bMenu);
		bIngr = (Button) findViewById(R.id.bIngredients);
		bSubmit.setOnClickListener(this);
		bPhoto.setOnClickListener(this);
		bMenu.setOnClickListener(this);
		bIngr.setOnClickListener(this);
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
			

		}
	}
}
