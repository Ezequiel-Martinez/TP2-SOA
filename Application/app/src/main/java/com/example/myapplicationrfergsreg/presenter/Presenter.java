package com.example.myapplicationrfergsreg.presenter;

import android.media.Image;
import android.util.JsonReader;
import android.widget.ImageView;

import com.example.myapplicationrfergsreg.Contract;
import com.example.myapplicationrfergsreg.R;
import com.example.myapplicationrfergsreg.model.APIConnection;
import com.example.myapplicationrfergsreg.model.ImageLoader;

public class Presenter implements Contract.Presenter, Contract.Model.OnFinishedListener {

    private Contract.View mainView;
    private Contract.Model model;
    private APIConnection apiConnection;
    private ImageView vistaPokemon;
    JsonReader jsonReader;

    public Presenter(Contract.View mainView, Contract.Model model, ImageView imageView) {
        this.mainView = mainView;
        this.model = model;
        this.vistaPokemon = imageView;
        apiConnection = new APIConnection("https://pokeapi.co/api/v2/pokemon/ditto", this, vistaPokemon);
    }

    @Override
    public void onButtonClick() throws Exception {
        if (mainView != null) {
            mainView.showProgress();
        }
        model.getNextCourse(this);
        apiConnection.execute();

    }

    @Override
    public void onDestroy() {
        mainView = null;
    }

    @Override
    // method to return the string which will be displayed in the Course Detail TextView
    public void onFinished(String string) {
        if (mainView != null) {
            mainView.setString(string);
            mainView.hideProgress();
        }
    }

    public void loadImage(String url, ImageView imageView) {
        new ImageLoader(url, vistaPokemon).execute();
    }
}
