package com.ronsolomon.solom.movieproject_v2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;



public class Task_downloadImage extends AsyncTask <String, Integer, Bitmap> {

    private Activity mActivity;
    private ProgressDialog mDialog;

     Task_downloadImage(Activity activity) {
        mActivity = activity;
        mDialog = new ProgressDialog(mActivity);
    }


    @Override
    protected Bitmap doInBackground(String... urls) {
        Log.d("doInBackground", "Start Downloading Image. PLEASE WAIT!");
        Bitmap image = downloadImage(urls[0]);

        return image;
    }

    @Override
    protected void onPreExecute() {

        mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mDialog.setCancelable(true);
        mDialog.setMessage("Loading...");
        mDialog.setProgress(0);

    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        mDialog.show();
        mDialog.setProgress(progress[0]);
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        if(result != null){
            ImageView imageView = (ImageView)mActivity.findViewById(R.id.imageView1);
            imageView.setImageBitmap(result);

        }
        mDialog.dismiss();
    }

    private Bitmap downloadImage (String urlString){
        URL url;

        try {

            url=new URL(urlString);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            InputStream instr = httpConn.getInputStream();
            int fileLength = httpConn.getContentLength();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int nRead, totalBytesRead = 0;
            byte [] data = new byte[2048];
            mDialog.setMax(fileLength);

            while ((nRead = instr.read(data, 0, data.length)) != -1){
                buffer.write(data, 0 , nRead);
                totalBytesRead += nRead;
                publishProgress(new Integer[]{totalBytesRead});

            }

            buffer.flush();
            byte[] image = buffer.toByteArray();
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            return bitmap;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
