package com.example.soapplication.api_unlam_activity;

import android.content.SharedPreferences;
import java.util.HashMap;

public interface ContractApiUnlam {

    enum EventType{ TYPE_USER_LOGIN, TYPE_USER_REGISTER, TYPE_SENSOR, TYPE_BACKGROUND_EXECUTION, TYPE_BROADCAST, TYPE_INTENT };

    interface Model{
        interface OnEventListener{
            void onEventLogin(boolean result, String token);
            void onEventRegister(boolean result, String token);
        }
        void setToken(String token);
        void login(OnEventListener listener, HashMap<String,String> values);
        void register(OnEventListener listener, HashMap<String,String> values);
        void logEvent(OnEventListener listener, String type, String description);
        void updateSharedPref(SharedPreferences sharedPreferences, String key, String value);
    }

    interface View{
        void makeIntent();
        void makeToast(String text);
    }

    interface Presenter{
        void processLogin(HashMap<String,String> values);
        void processRegister(HashMap<String,String> values);
        void logEvent(EventType type, String description);
        void updateSharedPref(String key, String value);
    }

}
