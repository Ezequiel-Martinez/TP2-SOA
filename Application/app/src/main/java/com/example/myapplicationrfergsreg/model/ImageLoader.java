package com.example.myapplicationrfergsreg.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.example.myapplicationrfergsreg.presenter.Presenter;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageLoader extends AsyncTask<Void, Void, Bitmap> {

    private String url;
    private ImageView imageView;
    private Presenter presenter;

    public ImageLoader(String url, ImageView imageView, Presenter presenter) {
        this.url = url;
        this.imageView = imageView;
        this.presenter = presenter;
    }


    @Override
    protected Bitmap doInBackground(Void... voids) {
        try {
            URL urlConnection = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlConnection
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        presenter.setPokemonImage(result);
        presenter.onFinished();
    }

}
