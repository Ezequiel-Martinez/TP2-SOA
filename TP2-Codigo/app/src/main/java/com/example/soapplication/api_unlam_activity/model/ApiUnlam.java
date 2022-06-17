package com.example.soapplication.api_unlam_activity.model;

import android.os.AsyncTask;
import android.os.Build;
import androidx.annotation.RequiresApi;
import com.example.soapplication.api_unlam_activity.ContractApiUnlam;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class ApiUnlam extends AsyncTask{

    private String current_dir;
    private ContractApiUnlam.Model.OnEventListener listener;
    static final String url = "http://so-unlam.net.ar/api/api/"; //directorio global ( dependiendo de la accion a ejecutar hay que acceder al sub-directorio especifico )
    static final String dir_event = "event";
    static final String dir_login = "login";
    static final String dir_register = "register";
    static final String environment = "PROD";
    static final String header_key = "Content-Type";
    static final String header_value = "application/json";
    static final String event_header_key = "Authorization";
    static final String event_header_value = "Bearer "; //a esto hay que concatenarle el token del usuario

    @Override
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected Object doInBackground(Object[] objects) {
        URL url;
        int connection_response_code;
        String connection_response_body = null;
        HttpURLConnection connection = null;
        DataOutputStream dataOutputStream;
        HashMap<String,Object> data = (HashMap<String,Object>)(objects[0]);

        try {
            this.current_dir = (String) data.get("request_url");
            this.listener = (ContractApiUnlam.Model.OnEventListener) data.get("listener");
            url = new URL((String) data.get("request_url"));
            connection = (HttpURLConnection)url.openConnection();
            connection.setDoOutput(true); //para incluir el body del request ( json )
            connection.setConnectTimeout(3000); //millis
            connection.setRequestProperty(this.header_key,this.header_value);

            if((Boolean) data.get("event")){
                connection.setRequestProperty(this.event_header_key,this.event_header_value + ModelApiUnlam.user_token);
            }

            connection.setRequestMethod("POST");
            dataOutputStream = new DataOutputStream(connection.getOutputStream());
            dataOutputStream.write(data.get("request_body").toString().getBytes(StandardCharsets.UTF_8));
            dataOutputStream.flush();
            connection.connect();
            connection_response_code = connection.getResponseCode(); //codigo de respuesta del server

            if(connection_response_code == HttpURLConnection.HTTP_OK || connection_response_code == HttpURLConnection.HTTP_CREATED) {
                String line;
                InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream()); //para leer el response del server
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                connection_response_body = "";
                while ((line = bufferedReader.readLine()) != null) {
                    connection_response_body += line;
                }
            }
            dataOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(connection != null){
                connection.disconnect();
            }
        }
        return connection_response_body;
    }

    @Override
    protected void onPostExecute(Object server_response) {
        super.onPostExecute(server_response);

        try {
            if(((String)server_response) != null){

                JSONObject jsonObject = new JSONObject((String) server_response);

                if(jsonObject.getBoolean("success")){
                    if(this.current_dir.contains(dir_login)){
                        this.listener.onEventLogin(true,jsonObject.getString("token"));
                        return;
                    }
                    if(this.current_dir.contains(dir_register)){
                        this.listener.onEventRegister(true,jsonObject.getString("token"));
                        return;
                    }
                }
                else{
                    if(this.current_dir.contains(dir_login)){
                        this.listener.onEventLogin(false,null);
                        return;
                    }
                    if(this.current_dir.contains(dir_register)){
                        this.listener.onEventRegister(false,null);
                        return;
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
