package com.example.soapplication.api_publica_activity.model;

import android.os.AsyncTask;
import android.widget.ImageView;

import com.example.soapplication.api_publica_activity.presenter.PresenterPokemon;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class APIPokemonConnection extends AsyncTask { // TIENE QUE EJECUTAR EN SEGUNDO PLANO
    String apiURL;
    InputStreamReader isr;
    String result = "";
    PresenterPokemon presenter;
    ImageView imageView;

    public APIPokemonConnection(String apiURL, PresenterPokemon presenter, ImageView imageView) {
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

                BufferedReader reader = new BufferedReader(isr);

                for (String line; (line = reader.readLine()) != null;) {
                    result += line;
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
            String peso = jsonObject.getString("weight");
            String altura = jsonObject.getString("height");

            presenter.loadImage(urlArtwork, imageView);
            presenter.setPokemonStrings(nombre, peso, altura);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
