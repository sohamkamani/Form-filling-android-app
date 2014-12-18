package com.sohamkamani.form_filling;

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
import android.content.res.Resources.Theme;
import android.graphics.Bitmap;
import android.widget.Button;
import android.widget.TextView;

public class Uploader extends Activity {
	InputStream inputStream;
	TextView status;
	String the_string_response;

	public Uploader(TextView status) {
		this.status = status;
	}

	public void upload_image(String image_str,String dir_name, final int index) {

		final ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

		nameValuePairs.add(new BasicNameValuePair("image", image_str));
		nameValuePairs
				.add(new BasicNameValuePair("image_name", ("img" + index )));
		nameValuePairs.add(new BasicNameValuePair("image_dir", dir_name));

		try {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					status.append("\nuploading image " + (index + 1));
					
				}
			});

			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					"http://hostingwebsite.com/upload_photo.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			the_string_response = convertResponseToString(response);
			// status.setText(the_string_response);

		} catch (final Exception e) {
			System.out.println("Error in http connection " + e.toString());
		} finally {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					status.append("\nimage" + (index+1) + "uploaded");
				}
			});

		}

	}

	public void upload_data(String[] data) {

		final ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

		nameValuePairs.add(new BasicNameValuePair("data1", data[0]));
		nameValuePairs.add(new BasicNameValuePair("data2", data[1]));
		nameValuePairs.add(new BasicNameValuePair("data3", data[2]));
		nameValuePairs.add(new BasicNameValuePair("data4", data[3]));
		nameValuePairs.add(new BasicNameValuePair("data5", data[4]));
		nameValuePairs.add(new BasicNameValuePair("data6", data[5]));
		nameValuePairs.add(new BasicNameValuePair("data7", data[6]));
		nameValuePairs.add(new BasicNameValuePair("data8", data[7]));
		nameValuePairs.add(new BasicNameValuePair("data9", data[8]));
		nameValuePairs.add(new BasicNameValuePair("data10", data[9]));
		nameValuePairs.add(new BasicNameValuePair("data11", data[10]));
		nameValuePairs.add(new BasicNameValuePair("data12", data[11]));
		nameValuePairs.add(new BasicNameValuePair("data13", data[12]));
		nameValuePairs.add(new BasicNameValuePair("data14", data[13]));
		nameValuePairs.add(new BasicNameValuePair("lat", data[14]));
		nameValuePairs.add(new BasicNameValuePair("lng", data[15]));
		nameValuePairs.add(new BasicNameValuePair("data17", data[16]));
		nameValuePairs.add(new BasicNameValuePair("data18", data[17]));

		try {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					status.append("\nsubmitting data");
				}
			});

			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					"http://hostingwebsite.com/create_db.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			the_string_response = convertResponseToString(response);
			// status.setText(the_string_response);

		} catch (final Exception e) {
			System.out.println("Error in http connection " + e.toString());
		} finally {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					status.append("\nData Upload Complete");
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