package com.gyh.digou.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**

 * 异步图片加载，加载item的小图片

 * @author Administrator

 *

 */

public class AsyncImageLoader {

 private static final String TAG = "AsyncImageLoader";

 public boolean allow = true;

 private Object lock = new Object();

 private int mStartLoadLimit;

 private int mStopLoadLimit;

 private boolean firstLoad = true;

 //缓存处理类

 ACache fileCache;

 public AsyncImageLoader(Context context){

	 //fileCache = new ACache();

 }

 public void setLoadLimit(int startLoadLimit,int stopLoadLimit){

 if(startLoadLimit > stopLoadLimit){

 return;

 }

 mStartLoadLimit = startLoadLimit;

 mStopLoadLimit = stopLoadLimit;

 synchronized (lock) {

 lock.notifyAll();

 }

 }

 public void lock(){

 allow = false;

 firstLoad = false;

 }

 public void setFirst(){

 firstLoad = true;

 }

 public void unlock(){

 allow = true;

 }

 

 public Drawable loadDrawable(final String imageUrl,final ImageCallback imageCallback){



 final Handler handler=new Handler(){

 @Override

 public void handleMessage(Message msg) {

 imageCallback.imageLoaded((Drawable) msg.obj, imageUrl);

 }

 };

 

 new Thread(){

 public void run() {

 

 Drawable drawable=loadImageFromUrl(imageUrl);

 if(drawable == null){

// handler.sendMessage(handler.obtainMessage(0, R.drawable.downloadfalse));

 return;

 }

 handler.sendMessage(handler.obtainMessage(0,drawable));

 };

 }.start();

 return null;

 }

 

 public Drawable loadDrawable(final int postion, final String imageUrl,final ImageCallback imageCallback){

 final Handler handler=new Handler(){

 @Override

 public void handleMessage(Message msg) {

 imageCallback.imageLoaded((Drawable) msg.obj, imageUrl);

 }

 };

 new Thread(){

 public void run() {

 if(!allow){

 synchronized (lock) {

 try {

 lock.wait();

 } catch (InterruptedException e) {

 e.printStackTrace();

 }

 }

 }

 Drawable drawable= null;

 if(firstLoad || (postion>=mStartLoadLimit && postion<=mStopLoadLimit)){ //第一次加载或

//只有在当前屏的item才会加载

 drawable = loadImageFromUrl(imageUrl);

 }

 if(drawable == null){

 return;

 }

 handler.sendMessage(handler.obtainMessage(0,drawable));

 };

 }.start();

 return null;

 }

 

 

 protected Drawable loadImageFromUrl(String imageurl) {

 if(null == imageurl || imageurl.equals("")){

 return null;

 }

 

 File f =null;

 //从SD卡

 Drawable d = filetoDrable(f);

 if (d != null)

 return d;

 //从网络

  URL m;  

      InputStream in = null; 

 try {

	 	m = null;  

            in = (InputStream) m.getContent();  

            OutputStream os = new FileOutputStream(f);

            CopyStream(in, os);

            os.close();

            return  filetoDrable(f);

            

 } catch (Exception ex){

// ex.printStackTrace();

 }

 return null;

 }

 

 public interface ImageCallback{

 public void imageLoaded(Drawable imageDrawable,String imageUrl);

 }

 

 

 private void CopyStream(InputStream is, OutputStream os) {

 final int buffer_size = 1024;

 try {

 byte[] bytes = new byte[buffer_size];

 for (;;) {

 int count = is.read(bytes, 0, buffer_size);

 if (count == -1)

 break;

 os.write(bytes, 0, count);

 }

 is.close();

 os.close();

 } catch (Exception ex) {

 }

 }

 

 private Drawable filetoDrable(File f)  {

    try {

 Drawable drawable = Drawable.createFromStream(new FileInputStream(f) , "src");

 Log.i("filetoDrable",f.getName());

 return drawable;

 } catch (Exception e) {

 // TODO Auto-generated catch block

// e.printStackTrace();

// Log.i("filetoDrable",e.toString());

 }catch(OutOfMemoryError e1){

// e1.printStackTrace();

 }

   return null;

 }

}
