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
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.TextView;
import android.widget.Toast;

public class ImageUploader extends Activity {
	InputStream inputStream;
	TextView status;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.image_uploading);

		status = (TextView) findViewById(R.id.imgStat);

		Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.land);
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream); // compress to
																	// which
																	// format
																	// you want.
		byte[] byte_arr = stream.toByteArray();
		// String image_str = Base64custom.encodeBytes(byte_arr);
		String image_str = Base64custom.encodeBytes(byte_arr);
		final ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

		nameValuePairs.add(new BasicNameValuePair("image", image_str));

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					status.setText("uploading");
					HttpClient httpclient = new DefaultHttpClient();
					HttpPost httppost = new HttpPost(
							"http://sohamkamani.host22.com/upload_photo.php");
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					HttpResponse response = httpclient.execute(httppost);
					final String the_string_response = convertResponseToString(response);
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							Toast.makeText(ImageUploader.this,
									"Response " + the_string_response,
									Toast.LENGTH_LONG).show();
						}
					});

				} catch (final Exception e) {
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							Toast.makeText(ImageUploader.this,
									"ERROR " + e.getMessage(),
									Toast.LENGTH_LONG).show();
						}
					});
					System.out.println("Error in http connection "
							+ e.toString());
				}
			}
		});
		t.start();

	}

	public String convertResponseToString(HttpResponse response)
			throws IllegalStateException, IOException {

		String res = "";
		StringBuffer buffer = new StringBuffer();
		inputStream = response.getEntity().getContent();
		final int contentLength = (int) response.getEntity().getContentLength(); // getting
																					// content
																					// length…..
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(ImageUploader.this,
						"contentLength : " + contentLength, Toast.LENGTH_LONG)
						.show();
			}
		});

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

			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					Toast.makeText(ImageUploader.this, "Result : ",
							Toast.LENGTH_LONG).show();
				}
			});
			// System.out.println("Response => " +
			// EntityUtils.toString(response.getEntity()));
		}
		return res;
	}
}