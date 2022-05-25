package com.example.myapplicationrfergsreg.presenter;

import android.graphics.Bitmap;
import android.util.JsonReader;
import android.widget.ImageView;

import com.example.myapplicationrfergsreg.Contract;
import com.example.myapplicationrfergsreg.model.APIPokemonConnection;
import com.example.myapplicationrfergsreg.model.ImageLoader;

public class Presenter implements Contract.Presenter, Contract.Model.OnFinishedListener {

    private Contract.View mainView;
    private APIPokemonConnection apiConnection;
    private ImageView vistaPokemon;
    private Bitmap imagenPokemon;
    private String nombrePokemon;
    private String pesoPokemon;
    private String alturaPokemon;
    private int counter = 1;

    public Presenter(Contract.View mainView, ImageView imageView) {
        this.mainView = mainView;
        this.vistaPokemon = imageView;
    }

    @Override
    public void onButtonClick() throws Exception {
        if (mainView != null) {
            mainView.showProgress();
        }
        //model.getNextCourse(this);
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
