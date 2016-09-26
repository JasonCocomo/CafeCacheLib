package com.cafebabe.lrucachelib.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public abstract class NetWorkHander  {
	
	public interface NetworkRequestCallback{
		public void onSucessed(Object bitmap);
		public void onNetworking();
		public void onFailed();
	}
	
	public class NetWorkPolicy{
		public String requestMethod = "POST";
		public int connectTimeOut = 3000;
		public boolean useCaches = true;
	}
	
	private static final String TAG = "NetWorkHander";
	protected NetworkRequestCallback requestCallback ;
	public void doRequest(String path,Map<String,Object> params,NetWorkPolicy policy,NetworkRequestCallback requestCallback) {
		HttpURLConnection conn;
		URL url = null;
		InputStream is = null;
		this.requestCallback = requestCallback;
		try {
			url = new URL(path);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setRequestMethod(policy.requestMethod);
			conn.setConnectTimeout(policy.connectTimeOut);
			if(params!=null){
				StringBuilder sb = new StringBuilder();
				for(String key :params.keySet()){
					sb.append(key).append("=").append(params.get(key)).append("&");
				}
				if(sb.length()>0){
					sb.subSequence(0, sb.length()-1);
				}
				conn.getOutputStream().write(sb.toString().getBytes());
			}
			conn.connect();
			if(conn.getResponseCode() == 200){
				onNetworkResonse(conn.getInputStream());
			}else{
				if(requestCallback !=null){
					requestCallback.onFailed();
				}
			}
		} catch (OutOfMemoryError e) {
			if(requestCallback !=null){
				requestCallback.onFailed();
			}
			Log.i(TAG, e.toString());
		} catch (Exception e) {
			if(requestCallback !=null){
				requestCallback.onFailed();
			}
			Log.i(TAG, e.toString());
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	protected abstract void onNetworkResonse(InputStream is) ;

}
