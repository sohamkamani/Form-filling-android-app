package com.sohamkamani.street_food;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener {

	Button bSubmit, bPhoto, bMenu, bIngr;
	EditText etName ;
	public static FoodMenuItem[] foodMenu ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		bSubmit = (Button) findViewById(R.id.bSubmit);
		bPhoto = (Button) findViewById(R.id.bPic);
		bMenu = (Button) findViewById(R.id.bMenu);
		bIngr = (Button) findViewById(R.id.bIngredients);
		etName = (EditText) findViewById(R.id.tbName);
		bSubmit.setOnClickListener(this);
		bPhoto.setOnClickListener(this);
		bMenu.setOnClickListener(this);
		bIngr.setOnClickListener(this);
		
		
		etName.setText("yoyo");
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
			Intent intentMenu = new Intent(this,MenuEntry.class);
			startActivityForResult(intentMenu, 0);
			break;
		
		case R.id.bPic:
			EditText t1 = (EditText) findViewById(R.id.tbName);
			t1.setText(foodMenu[0].name);
			break;
			
		case R.id.bIngredients:
			Intent intentIngr = new Intent(this,IngredientEntry.class);
			startActivityForResult(intentIngr, 1);
			break;
			

		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 0) {
	        if(resultCode == RESULT_OK){
	            Bundle fMenu = data.getExtras();
	            String[] fm = fMenu.getStringArray("foodmenu");
	            //etName.setText(fm[0]);
	            
	        }
	        if (resultCode == RESULT_CANCELED) {
	            //Write your code if there's no result
	        }
	    }
	}
}
