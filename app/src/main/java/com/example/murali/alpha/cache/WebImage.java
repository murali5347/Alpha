package com.example.murali.alpha.cache;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class WebImage implements SmartImage {
    private static final int CONNECT_TIMEOUT = 5000;
    private static final int READ_TIMEOUT = 10000;

    private static WebImageCache webImageCache;

    private String url;

    public WebImage(String url) {
        this.url = url;
    }

    public Bitmap getBitmap(Context context) {
        // Don't leak context
        if(webImageCache == null) {
            webImageCache = new WebImageCache(context);
        }

        // Try getting bitmap from cache first
        Bitmap bitmap = null;
        if(url != null) {
            bitmap = webImageCache.get(url);
            if(bitmap == null) {
                try {
					bitmap = getBitmapFromUrl(url);
				} catch (OutOfMemoryError e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
                if(bitmap != null){
                    webImageCache.put(url, bitmap);
                }
            }
        }

        return bitmap;
    }

    private Bitmap getBitmapFromUrl(String url) throws IOException {
        Bitmap bitmap = null;

        try {
            URLConnection conn = new URL(url).openConnection();
            conn.setConnectTimeout(CONNECT_TIMEOUT);
            conn.setReadTimeout(READ_TIMEOUT);
            BitmapFactory.Options options = new BitmapFactory.Options();
            
            ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
            int inSample = 1;
            if(memInfo.lowMemory){
            	inSample = 12;
            }
            options.inSampleSize = inSample;
            options.inPurgeable = true;
            Rect rect = new Rect(-1,-1,-1,-1);
            bitmap = BitmapFactory.decodeStream((InputStream) conn.getContent(), rect, options);
        } catch(OutOfMemoryError e) {
        	e.printStackTrace();
          	bitmap = null;
        } catch (Exception e) {
        	e.printStackTrace();
          	bitmap = null;
		}

        return bitmap;
    }

    public static void removeFromCache(String url) {
        if(webImageCache != null) {
            webImageCache.remove(url);
        }
    }
}