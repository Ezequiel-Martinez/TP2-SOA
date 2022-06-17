package com.example.soapplication.api_publica_activity.model;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Vibrator;
import com.example.soapplication.api_publica_activity.ContractPokemon;

public class ModelPokemon implements ContractPokemon.Model {

    private SensorReader sensorReader;
    private MediaPlayer mediaPlayer;

    public ModelPokemon(ContractPokemon.View view, SensorManager sensorManager){
        sensorReader = new SensorReader(sensorManager);
        mediaPlayer = MediaPlayer.create((Context) view,com.example.soapplication.R.raw.alert_sound);
    }

    @Override
    public void registerSensorListener(OnFinishedListener listener) {
        sensorReader.registerListener(listener);
    }

    @Override
    public void unregisterSensorListener() {
        sensorReader.unregisterListener();
    }

    @Override
    public void processSensor(ContractPokemon.View view, int sensorType) {
        if(sensorType == Sensor.TYPE_PROXIMITY){
            mediaPlayer.start();
        }
        if(sensorType == Sensor.TYPE_GYROSCOPE){
            ((Vibrator)((Context)view).getSystemService(Context.VIBRATOR_SERVICE)).vibrate(500);
        }
    }

}
