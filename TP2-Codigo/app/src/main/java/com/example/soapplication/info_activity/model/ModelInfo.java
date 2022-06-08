package com.example.soapplication.info_activity.model;

import android.content.SharedPreferences;
import com.example.soapplication.info_activity.ContractInfo;
import java.util.Map;

public class ModelInfo implements ContractInfo.Model {
    @Override
    public void getSharedPref(OnEventListener listener, SharedPreferences sharedPreferences) {
        Map<String,String> m;

        m = (Map<String, String>) sharedPreferences.getAll();

        String data = new String();

        data += "Usuario - Ultimo acceso\n";

        for(Map.Entry<String,String> entry : m.entrySet()){
            data += entry.getValue() + " - " + entry.getKey() + "\n";
        }

        listener.displaySharedPref(data);
    }
}
