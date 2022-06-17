package com.example.soapplication.api_publica_activity.view;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import static android.view.View.GONE;
import com.example.soapplication.R;
import com.example.soapplication.api_publica_activity.ContractPokemon;
import com.example.soapplication.api_publica_activity.presenter.PresenterPokemon;

public class ActivityPokemon extends AppCompatActivity implements ContractPokemon.View {
    private TextView nombrePokemon;
    private TextView descripcionPokemon;
    private ProgressBar progressBar;
    ContractPokemon.Presenter presenter;
    private ImageView imagenPokemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombrePokemon = findViewById(R.id.PokemonName);
        Button button = findViewById(R.id.button);
        progressBar = findViewById(R.id.progressBar);
        imagenPokemon = findViewById(R.id.PokemonImage);
        descripcionPokemon = findViewById(R.id.PokemonDescription);

        presenter = new PresenterPokemon(this, imagenPokemon,(SensorManager) getSystemService(SENSOR_SERVICE));

        // operations to be performed when user clicks the button
        button.setOnClickListener(v -> {
            try {
                presenter.onButtonClick();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.registerSensorListener();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.unregisterSensorListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unregisterSensorListener();
        presenter.onDestroy();
    }

    @Override
    // method to display the Course Detail TextView
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        nombrePokemon.setVisibility(View.INVISIBLE);
        descripcionPokemon.setVisibility(View.INVISIBLE);
        imagenPokemon.setVisibility(View.INVISIBLE);
    }

    @Override
    // method to hide the Course Detail TextView
    public void hideProgress() {
        progressBar.setVisibility(GONE);
        nombrePokemon.setVisibility(View.VISIBLE);
        descripcionPokemon.setVisibility(View.VISIBLE);
        imagenPokemon.setVisibility(View.VISIBLE);
    }

    @SuppressLint("SetTextI18n")
    @Override
    // method to set random string in the Course Detail TextView
    public void setPokemon(String nombre, String peso, String altura, Bitmap imagen) {
        nombrePokemon.setText(nombre);
        descripcionPokemon.setText( "Peso: " + peso + "\n" + "Altura: " + altura + "\n");
        imagenPokemon.setImageBitmap(imagen);
    }
}
