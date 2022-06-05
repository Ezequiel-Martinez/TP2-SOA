package com.example.soapplication.api_publica_activity.model;

import android.hardware.SensorManager;

import com.example.soapplication.api_publica_activity.ContractPokemon;
import com.example.soapplication.api_unlam_activity.model.ApiUnlam;

public class ModelPokemon implements ContractPokemon.Model {

    private SensorReader sensorReader;

    public ModelPokemon(SensorManager sensorManager){
        sensorReader = new SensorReader(sensorManager);
    }

    @Override
    public void getNextCourse(OnFinishedListener onFinishedListener) {}

    @Override
    public void registerSensorListener(OnFinishedListener listener) {
        sensorReader.registerListener(listener);
    }

    @Override
    public void unregisterSensorListener() {
        sensorReader.unregisterListener();
    }
}
