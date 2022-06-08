package com.example.soapplication.info_activity;

import android.content.SharedPreferences;

public interface ContractInfo {

    interface Model{
        interface OnEventListener{
            void displaySharedPref(String info);
        }
        void getSharedPref(OnEventListener listener, SharedPreferences sharedPreferences);
    }

    interface View{
        void setInfo(String info);
    }

    interface Presenter{
        void getSharedPref();
    }
}
