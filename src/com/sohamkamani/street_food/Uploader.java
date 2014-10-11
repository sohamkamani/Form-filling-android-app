package com.sohamkamani.street_food;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.graphics.Bitmap;
import android.widget.Button;
import android.widget.TextView;

public class Uploader extends Activity {
	InputStream inputStream;
	TextView status;
	Button submitButton;

	public Uploader(TextView status, Button submitButton) {
		this.status = status;
		this.submitButton = submitButton;
	}

	public void upload_image(String image_str, String img_name, final int index) {

		final ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

		nameValuePairs.add(new BasicNameValuePair("image", image_str));
		nameValuePairs.add(new BasicNameValuePair("image_name",
				(img_name + index)));

		try {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					status.setText("uploading image " + (index + 1));
					submitButton.setClickable(false);
				}
			});

			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					"http://sohamkamani.host22.com/upload_photo.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			// final String the_string_response =
			// convertResponseToString(response);
			// status.setText(the_string_response);

		} catch (final Exception e) {
			submitButton.setClickable(true);
			System.out.println("Error in http connection " + e.toString());
		} finally {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					status.setText("photo " + (index + 1) + " done");
					submitButton.setClickable(true);
				}
			});

		}

	}
	
	public void upload_data(String[] data) {

		final ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

		nameValuePairs.add(new BasicNameValuePair("name", data[0]));
		nameValuePairs.add(new BasicNameValuePair("phone", data[1]));
		nameValuePairs.add(new BasicNameValuePair("address", data[2]));
		nameValuePairs.add(new BasicNameValuePair("landmark", data[3]));
		nameValuePairs.add(new BasicNameValuePair("food_type", data[4]));
		nameValuePairs.add(new BasicNameValuePair("cuisine", data[5]));
		nameValuePairs.add(new BasicNameValuePair("cleanliness", data[6]));
		nameValuePairs.add(new BasicNameValuePair("quality", data[7]));
		nameValuePairs.add(new BasicNameValuePair("home_del", data[8]));
		nameValuePairs.add(new BasicNameValuePair("home_del_amt", data[9]));
		nameValuePairs.add(new BasicNameValuePair("t_open", data[10]));
		nameValuePairs.add(new BasicNameValuePair("t_close", data[11]));
		nameValuePairs.add(new BasicNameValuePair("avg_price", data[12]));
		nameValuePairs.add(new BasicNameValuePair("signature_dish", data[13]));
		nameValuePairs.add(new BasicNameValuePair("lat", data[14]));
		nameValuePairs.add(new BasicNameValuePair("lng", data[15]));
		nameValuePairs.add(new BasicNameValuePair("menu", data[16]));
		nameValuePairs.add(new BasicNameValuePair("ingredients", data[17]));

		try {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					status.setText("submitting data");
					submitButton.setClickable(false);
				}
			});

			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					"http://sohamkamani.host22.com/upload_data.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			// final String the_string_response =
			// convertResponseToString(response);
			// status.setText(the_string_response);

		} catch (final Exception e) {
			submitButton.setClickable(true);
			System.out.println("Error in http connection " + e.toString());
		} finally {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					status.setText("data submitted");
					submitButton.setClickable(true);
				}
			});

		}

	}

	public String convertResponseToString(HttpResponse response)
			throws IllegalStateException, IOException {

		String res = "";
		StringBuffer buffer = new StringBuffer();
		inputStream = response.getEntity().getContent();
		final int contentLength = (int) response.getEntity().getContentLength(); // getting
																					// content
																					// length…..

		if (contentLength < 0) {
		} else {
			byte[] data = new byte[512];
			int len = 0;
			try {
				while (-1 != (len = inputStream.read(data))) {
					buffer.append(new String(data, 0, len)); // converting to
																// string and
																// appending to
																// stringbuffer…..
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				inputStream.close(); // closing the stream…..
			} catch (IOException e) {
				e.printStackTrace();
			}
			res = buffer.toString(); // converting stringbuffer to string…..
			// status.setText(res);
			// runOnUiThread(new Runnable() {
			//
			// @Override
			// public void run() {
			// Toast.makeText(ImageUploader.this, "Result : ",
			// Toast.LENGTH_LONG).show();
			// }
			// });
			// System.out.println("Response => " +
			// EntityUtils.toString(response.getEntity()));
		}
		return res;
	}
}