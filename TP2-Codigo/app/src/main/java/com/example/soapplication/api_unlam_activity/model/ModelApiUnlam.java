package com.example.soapplication.api_unlam_activity.model;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import com.example.soapplication.api_unlam_activity.ContractApiUnlam;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;

public class ModelApiUnlam implements ContractApiUnlam.Model {

    static String user_token = null;
    public static final String SHARED_PREF = "soapplication_shared_pref";

    @Override
    public void setToken(String token) {
        user_token = token;
    }

    @Override
    public void login(OnEventListener listener, HashMap<String, String> values) {
        try {
            if(!validateLoginValues(values)){
                listener.onEventLogin(false,"");
                return;
            }
            JSONObject body = new JSONObject();
            HashMap<String,Object> data = new HashMap<>();

            body.put("email",values.get("email"));
            body.put("password",values.get("password"));

            data.put("listener",listener);
            data.put("request_body",body);
            data.put("request_url", ApiUnlam.url+ ApiUnlam.dir_login);
            data.put("event",false);

            new ApiUnlam().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void register(OnEventListener listener, HashMap<String, String> values) {
        try {
            if(!validateRegisterValues(values)){
                listener.onEventRegister(false,"");
                return;
            }
            JSONObject body = new JSONObject();
            HashMap<String,Object> data = new HashMap<>();

            body.put("env", ApiUnlam.environment);
            body.put("name",values.get("name"));
            body.put("lastname",values.get("last_name"));
            body.put("dni",Integer.valueOf(values.get("dni")));
            body.put("email",values.get("email"));
            body.put("password",values.get("password"));
            body.put("commission",Integer.valueOf(values.get("commission")));
            body.put("group",Integer.valueOf(values.get("group")));

            data.put("listener",listener);
            data.put("request_body",body);
            data.put("request_url", ApiUnlam.url+ ApiUnlam.dir_register);
            data.put("event",false);

            new ApiUnlam().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void logEvent(OnEventListener listener, String type, String description) {
        try {
            if(user_token == null){
                listener.onEventLog(false,null);
                return;
            }

            JSONObject body = new JSONObject();
            HashMap<String,Object> data = new HashMap<>();

            body.put("env", ApiUnlam.environment);
            body.put("type_events",type);
            body.put("description",description);

            data.put("listener",listener);
            data.put("request_body",body);
            data.put("request_url", ApiUnlam.url+ ApiUnlam.dir_event);
            data.put("event",true);

            new ApiUnlam().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateSharedPref(SharedPreferences sharedPreferences, String key, String value) {
        sharedPreferences.edit().putString(key,value).apply();
    }

    private boolean validateLoginValues(HashMap<String, String> values) {
        if(values.get("email") == null || values.get("email").isEmpty()){
            return false;
        }
        if(values.get("password") == null || values.get("password").isEmpty() || values.get("password").length() < 8){
            return false;
        }
        return true;
    }

    private boolean validateRegisterValues(HashMap<String, String> values) {
        if(values.get("name") == null || values.get("last_name") == null || values.get("dni") == null ||
                values.get("email") == null || values.get("password") == null || values.get("commission") == null || values.get("group") == null){
            return false;
        }
        if(values.get("name").isEmpty() || values.get("last_name").isEmpty() || values.get("dni").isEmpty() ||
                values.get("email").isEmpty() || values.get("password").isEmpty() || values.get("commission").isEmpty() || values.get("group").isEmpty()){
            return false;
        }
        if(values.get("password").length() < 8 || !values.get("commission").equals("3900")){
            return false;
        }
        return true;
    }

}
