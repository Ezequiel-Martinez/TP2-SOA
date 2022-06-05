package com.example.soapplication.api_publica_activity.model;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import com.example.soapplication.api_publica_activity.ContractPokemon;

public class SensorReader implements SensorEventListener {

    private Sensor proximity;
    private Sensor accelerometer;
    private final SensorManager sensorManager;
    private ContractPokemon.Model.OnFinishedListener listener;

    public SensorReader(SensorManager sensorManager){
        this.sensorManager = sensorManager;
        this.proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        this.accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }


    public void registerListener(ContractPokemon.Model.OnFinishedListener listener){
        this.listener = listener;
        sensorManager.registerListener(this,proximity,SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,accelerometer,sensorManager.SENSOR_DELAY_NORMAL);
        Log.d("DebugLog_SensorReader","SensorReader registers sensor listener");
    }

    public void unregisterListener(){
        sensorManager.unregisterListener(this);
        Log.d("DebugLog_SensorReader","SensorReader unregisters sensor listener");
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()){
            case Sensor.TYPE_PROXIMITY:
                if (event.values[0] <= 2){ //si estas a 2cm o menos
                    this.listener.onEventSensorChanged(Sensor.TYPE_PROXIMITY);
                    Log.d("DebugLog_SensorReader","Sensor.TYPE_PROXIMITY");
                }
                break;
            case Sensor.TYPE_ACCELEROMETER:
                if(event.values[0] >= 10 || event.values[0] <= -10){
                    Log.d("DebugLog_SensorReader","Sensor.TYPE_ACCELEROMETER");
                    this.listener.onEventSensorChanged(Sensor.TYPE_ACCELEROMETER);
                }
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}
}
