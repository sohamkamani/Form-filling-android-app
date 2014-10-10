package com.sohamkamani.street_food;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.util.Base64;

public class ImgGiver {

	public String ImgtoString(Bitmap bm) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); // bm is the bitmap
															// object
		byte[] b = baos.toByteArray();

		String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

		return encodedImage;
	}
}
