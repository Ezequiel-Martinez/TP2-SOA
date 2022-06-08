package com.example.soapplication.api_unlam_activity.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.example.soapplication.api_unlam_activity.model.ModelApiUnlam;
import com.example.soapplication.api_unlam_activity.ContractApiUnlam;
import com.example.soapplication.api_unlam_activity.view.ActivityApiUnlam;
import java.util.HashMap;

public class PresenterApiUnlam implements ContractApiUnlam.Presenter, ContractApiUnlam.Model.OnEventListener {

    private ContractApiUnlam.View view;
    private ContractApiUnlam.Model model;

    public PresenterApiUnlam(ContractApiUnlam.View view, ContractApiUnlam.Model model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void logEvent(ContractApiUnlam.EventType type, String description) {

        switch (type){
            case TYPE_USER_LOGIN:
                model.logEvent(this,"login",description);
                break;

            case TYPE_USER_REGISTER:
                model.logEvent(this,"register",description);
                break;

            case TYPE_SENSOR:
                model.logEvent(this,"sensor",description);
                break;

            case TYPE_INTENT:
                model.logEvent(this,"intent",description);
                break;

            case TYPE_BROADCAST:
                model.logEvent(this,"broadcast",description);
                break;

            case TYPE_BACKGROUND_EXECUTION:
                model.logEvent(this,"background_execution",description);
                break;

            default:
                break;
        }

    }

    @Override
    public void updateSharedPref(String key, String value) {
        model.updateSharedPref(((ActivityApiUnlam)view).getSharedPreferences(ModelApiUnlam.SHARED_PREF,Context.MODE_PRIVATE),key,value);
    }

    @Override
    public void processLogin(HashMap<String,String> values) {
        if(connectionAvailable()){
            model.login(this,values);
        }
    }

    @Override
    public void processRegister(HashMap<String,String> values) {
        if(connectionAvailable()){
            model.register(this,values);
        }
    }

    @Override
    public void onEventLog(boolean result, String token) {
    }

    @Override
    public void onEventLogin(boolean result, String token) {
        if(result){
            model.setToken(token);
            logEvent(ContractApiUnlam.EventType.TYPE_USER_LOGIN,"User login");
            view.makeIntent();
            return;
        }
        view.makeToast("Login error");
    }

    @Override
    public void onEventRegister(boolean result, String token) {
        if(result){
            model.setToken(token);
            logEvent(ContractApiUnlam.EventType.TYPE_USER_REGISTER,"User register");
            view.makeIntent();
            return;
        }
        view.makeToast("Register error");
    }

    private boolean connectionAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) ((ActivityApiUnlam)view).getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo == null){
            view.makeToast("No connection");
            return false;
        }
        return networkInfo.isConnected();
    }

}
