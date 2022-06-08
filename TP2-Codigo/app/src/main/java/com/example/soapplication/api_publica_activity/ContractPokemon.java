package com.example.soapplication.api_publica_activity;

import android.graphics.Bitmap;

public interface ContractPokemon {
    interface View {
        void showProgress(); // method to display progress bar when next random course details is being fetched
        void hideProgress(); // method to hide progress bar when next random course details is being fetched
        void setPokemon(String nombre, String peso, String altura, Bitmap imagen);
    }

    interface Model {
        interface OnFinishedListener {
            void onFinished(); // function to be called once the Handler of Model class completes its execution
            void onEventSensorChanged(int sensorType);
        }

        void registerSensorListener(OnFinishedListener listener);
        void unregisterSensorListener();
    }

    interface Presenter {
        void registerSensorListener();
        void unregisterSensorListener();
        void onButtonClick() throws Exception; // method to be called when the button is clicked
        void onDestroy(); // method to destroy lifecycle of MainActivity
    }
}
