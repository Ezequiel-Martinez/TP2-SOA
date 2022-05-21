package com.example.myapplicationrfergsreg.model;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.widget.ImageView;

import com.example.myapplicationrfergsreg.presenter.Presenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class APIConnection extends AsyncTask { // TIENE QUE EJECUTAR EN SEGUNDO PLANO
    String apiURL;
    InputStreamReader isr;
    String result = "";
    Presenter presenter;
    ImageView imageView;

    public APIConnection (String apiURL, Presenter presenter, ImageView imageView) {
        this.apiURL = apiURL;
        this.presenter = presenter;
        this.imageView = imageView;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        URL url;
        HttpURLConnection urlConnection = null;

        try {
            url = new URL(apiURL);
            urlConnection = (HttpURLConnection) url.openConnection();
            int responseCode = urlConnection.getResponseCode();

            if(responseCode == HttpURLConnection.HTTP_OK){
                InputStream responseBody = urlConnection.getInputStream();
                isr = new InputStreamReader(responseBody, "UTF-8");

                int data = isr.read();

                while (data != -1) {
                    result += (char) data;
                    data = isr.read();

                }

                return result;
            }
            else {
                throw new Exception("No se pudo establecer la conexión con la API");
            }

        } catch (Exception e) {
            e.getMessage();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {

        super.onPostExecute(o);
        try {
            JSONObject jsonObject = new JSONObject((String) o);

            JSONObject sprites = jsonObject.getJSONObject("sprites");
            JSONObject otherSprites = sprites.getJSONObject("other");
            JSONObject artwork = otherSprites.getJSONObject("official-artwork");
            String urlArtwork = artwork.getString("front_default");
            String nombre = jsonObject.getString("name");

            presenter.loadImage(urlArtwork, imageView);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
