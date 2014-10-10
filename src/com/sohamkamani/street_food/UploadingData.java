package com.sohamkamani.street_food;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

public class UploadingData extends AsyncTask<String, Void, String> {

	private TextView statusField;
	private Context context;

	// flag 0 means get and 1 means post.(By default it is get.)
	public UploadingData(Context context, TextView statusField) {
		this.context = context;
		this.statusField = statusField;
	}

	protected void onPreExecute() {

	}

	@Override
	protected String doInBackground(String... arg0) {

		upload(arg0[0],arg0[1]);
		
		return "hooray";

	}

	@Override
	protected void onPostExecute(String result) {
		this.statusField.setText("Login Successful\n"+result);
	}
	
	public void upload(String data1,String data2){
		try{
            String username = data1;
            String password = data2;
            String link="http://sohamkamani.host22.com/create_db.php";
            String data  = URLEncoder.encode("username", "UTF-8") 
            + "=" + URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8") 
            + "=" + URLEncoder.encode(password, "UTF-8");
            URL url = new URL(link);
            //System.out.println("opeing connection...");
            URLConnection conn = url.openConnection(); 
            conn.setDoOutput(true); 
            OutputStreamWriter wr = new OutputStreamWriter
            (conn.getOutputStream()); 
            wr.write( data ); 
            wr.flush(); 
            //System.out.println("Submitted...");
            BufferedReader reader = new BufferedReader
            (new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            // Read Server Response
            while((line = reader.readLine()) != null)
            {
               sb.append(line);
               break;
            }
            //System.out.println(sb.toString());
         }catch(Exception e){
        	 System.out.println("Exception: " + e.getMessage());
         }
	}
	
	
	
}