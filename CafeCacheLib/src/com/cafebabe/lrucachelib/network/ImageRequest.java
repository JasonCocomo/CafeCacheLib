package com.cafebabe.lrucachelib.core;

import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageRequest extends NetWorkHander {

	@Override
	protected void onNetworkResonse(InputStream is) {
		try {
			Bitmap bitmap = BitmapFactory.decodeStream(is);
			/**
			 * 创建一个固定宽高的bitmap对象
			 */
			if(bitmap != null){
				bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);
				requestCallback.onSucessed(bitmap);
			}else{
				requestCallback.onFailed();
			}
		} catch (Exception e) {
			requestCallback.onFailed();
			e.printStackTrace();
		}
	}

}
