package com.cafebabe.lrucachelib.core;

import android.support.v4.util.LruCache;

public class CafeCache extends LruCache<String, Object> {
	
	private final static int DEFAULT_SIZE = 50;
	
	public CafeCache(){
		super(DEFAULT_SIZE);
	}
	
	public CafeCache(int maxSize) {
		super(maxSize);
	}
	
	public void cache(String key,Object obj){
		put(key, obj);
	}
	
	public Object getValue(String key){
		return super.get(key);
	}

}
