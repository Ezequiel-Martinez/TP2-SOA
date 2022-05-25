package com.example.myapplicationrfergsreg;

import android.graphics.Bitmap;

import java.net.ProtocolException;

public interface Contract {
    interface View {
        void showProgress(); // method to display progress bar when next random course details is being fetched
        void hideProgress(); // method to hide progress bar when next random course details is being fetched
        void setPokemon(String nombre, String peso, String altura, Bitmap imagen);
    }

    interface Model {
        interface OnFinishedListener {
            void onFinished(); // function to be called once the Handler of Model class completes its execution
        }

        void getNextCourse(Contract.Model.OnFinishedListener onFinishedListener);
    }

    interface Presenter {
        void onButtonClick() throws Exception; // method to be called when the button is clicked
        void onDestroy(); // method to destroy lifecycle of MainActivity
    }
}
