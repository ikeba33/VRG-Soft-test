package com.example.retrofittest1;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Time;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;


public class ImageManager {

    private final static String TAG = "ImageManager";

    /**
     * Private constructor prevents instantiation from other classes
     */
    private ImageManager() {
    }

    public static void fetchImage(final String iUrl, final ImageView iView) {
        if (iUrl == null || iView == null)
            return;

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message message) {
                final Bitmap image = (Bitmap) message.obj;
                iView.setImageBitmap(image);
            }
        };

//        final Thread thread = new Thread() {
//            @Override
//            public void run() {
//                final Bitmap image = downloadImage(iUrl);
//                if (image != null) {
//                    Log.v(TAG, "Got image by URL: " + iUrl);
//                    final Message message = handler.obtainMessage(1, image);
//                    handler.sendMessage(message);
//                }
//            }
//        };
//        iView.setImageResource(R.drawable.ic_launcher_background);
//        thread.setPriority(3);
//        thread.start();
    }

  //  public static Bitmap downloadImage(ImageView iv, String folderToSave) {
//        OutputStream fOut = null;
//
//        try {
//            File file = new File(folderToSave, "alesha" +".jpg"); // +".jpg"); // создать уникальное имя для файла основываясь на дате сохранения
//            fOut = new FileOutputStream(file);
//
//            Bitmap bitmap = (BitmapDrawable) iv.getDrawable().getBitmap();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut); // сохранять картинку в jpeg-формате с 85% сжатия.
//            fOut.flush();
//            fOut.close();
//            MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), file.getName(),  file.getName()); // регистрация в фотоальбоме
//        }
//        catch (Exception e) // здесь необходим блок отслеживания реальных ошибок и исключений, общий Exception приведен в качестве примера
//        {
//            return e.getMessage();
//        }
//        return "";

}
