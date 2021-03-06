package com.example.soapplication.api_publica_activity.presenter;

import android.graphics.Bitmap;
import android.hardware.SensorManager;
import android.widget.ImageView;
import com.example.soapplication.api_publica_activity.model.APIPokemonConnection;
import com.example.soapplication.api_publica_activity.ContractPokemon;
import com.example.soapplication.api_publica_activity.model.ImageLoader;
import com.example.soapplication.api_publica_activity.model.ModelPokemon;

public class PresenterPokemon implements ContractPokemon.Presenter, ContractPokemon.Model.OnFinishedListener {

    private ContractPokemon.Model model;
    private ContractPokemon.View mainView;
    private APIPokemonConnection apiConnection;
    private ImageView vistaPokemon;
    private Bitmap imagenPokemon;
    private String nombrePokemon;
    private String pesoPokemon;
    private String alturaPokemon;
    private int counter = 1;

    public PresenterPokemon(ContractPokemon.View mainView, ImageView imageView, SensorManager sensorManager) {
        this.model = new ModelPokemon(mainView,sensorManager);
        this.mainView = mainView;
        this.vistaPokemon = imageView;
    }

    @Override
    public void registerSensorListener() {
        model.registerSensorListener(this);
    }

    @Override
    public void unregisterSensorListener() {
        model.unregisterSensorListener();
    }

    @Override
    public void onButtonClick() throws Exception {
        if (mainView != null) {
            mainView.showProgress();
        }

        String apiURL = "https://pokeapi.co/api/v2/pokemon/" + counter;
        apiConnection = new APIPokemonConnection(apiURL, this, vistaPokemon);
        apiConnection.execute();
        counter++;
    }

    @Override
    public void onDestroy() {
        mainView = null;
    }
    // method to return the string which will be displayed in the Course Detail TextView
    public void onFinished() {
        if (mainView != null) {
            mainView.setPokemon(nombrePokemon, pesoPokemon, alturaPokemon, imagenPokemon);
            mainView.hideProgress();
        }
    }

    @Override
    public void onEventSensorChanged(int sensorType) {
        model.processSensor(mainView,sensorType);
    }

    public void loadImage(String url, ImageView imageView) {
        new ImageLoader(url, vistaPokemon, this).execute();
    }

    public void setPokemonImage(Bitmap image) {
        imagenPokemon = image;
    }

    public void setPokemonStrings(String nombre, String peso, String altura) {
        nombrePokemon = nombre;
        pesoPokemon = peso;
        alturaPokemon = altura;
    }
}
