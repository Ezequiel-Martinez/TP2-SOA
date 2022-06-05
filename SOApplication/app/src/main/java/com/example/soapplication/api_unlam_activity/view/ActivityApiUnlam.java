package com.example.soapplication.api_unlam_activity.view;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.soapplication.R;
import com.example.soapplication.api_publica_activity.view.ActivityPokemon;
import com.example.soapplication.api_unlam_activity.presenter.PresenterApiUnlam;
import com.example.soapplication.api_unlam_activity.model.ModelApiUnlam;
import com.example.soapplication.api_unlam_activity.ContractApiUnlam;
import java.util.Calendar;
import java.util.HashMap;

public class ActivityApiUnlam extends AppCompatActivity implements ContractApiUnlam.View{

    private TextView et_name;
    private TextView et_last_name;
    private TextView et_dni;
    private TextView et_email;
    private TextView et_password;
    private TextView et_commission;
    private TextView et_group;
    private Button btn_login;
    private Button btn_register;
    private Button btn_accept;
    private ContractApiUnlam.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_unlam);

        et_name = findViewById(R.id.et_name);
        et_last_name = findViewById(R.id.et_last_name);
        et_dni = findViewById(R.id.et_dni);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        et_commission = findViewById(R.id.et_commission);
        et_group = findViewById(R.id.et_group);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);
        btn_accept = findViewById(R.id.btn_accept);
        presenter = new PresenterApiUnlam(this,new ModelApiUnlam());

        setupButtonListener();
        updateToLoginLayout(); //por defecto que arranque en el layout de login
    }

    @Override
    public void makeIntent() {

        //key : value ( como el usuario se puede logear varias veces, entonces uso la fecha-hh-mm-ss como clave )
        presenter.updateSharedPref(Calendar.getInstance().getTime().toString(),et_name.getText().toString() + " " + et_last_name.getText().toString());

        Log.d("DebugLog_MainActivity","Making intent");
        //TODO: Aca hay que hacer un intent para abrir la activity que se encarga de manejar todo lo relacionado con la api-rest publica, y el log del intent

        presenter.logEvent(ContractApiUnlam.EventType.TYPE_INTENT,"Intent ( ActivityApiUnlam -> ActivityPokemon )");

        Intent intent = new Intent(this, ActivityPokemon.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void makeToast(String text) {
        Log.d("DebugLog_MainActivity","Making toast ( text: " + text + " )");
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    private void updateToLoginLayout() {
        et_name.setEnabled(false);
        et_last_name.setEnabled(false);
        et_dni.setEnabled(false);
        et_commission.setEnabled(false);
        et_group.setEnabled(false);
        btn_login.setEnabled(false);
        btn_register.setEnabled(true);
    }

    private void updateToRegisterLayout() {
        et_name.setEnabled(true);
        et_last_name.setEnabled(true);
        et_dni.setEnabled(true);
        et_commission.setEnabled(true);
        et_group.setEnabled(true);
        btn_login.setEnabled(true);
        btn_register.setEnabled(false);
    }

    private HashMap<String,String> getFieldValues(){
        HashMap<String,String> values = new HashMap<>();
        /*
        values.put("name",et_name.getText().toString());
        values.put("last_name",et_last_name.getText().toString());
        values.put("dni",et_dni.getText().toString());
        values.put("email",et_email.getText().toString());
        values.put("password",et_password.getText().toString());
        values.put("commission",et_commission.getText().toString());
        values.put("group",et_group.getText().toString());
         */
        //TODO: Eliminar, es simplemente para hacer pruebas
        values.put("name","pepa");
        values.put("last_name","pig");
        values.put("dni","12345678");
        values.put("email","abc@hotmail.com");
        values.put("password","1234567890");
        values.put("commission","3900");
        values.put("group","6");
        return values;
    }

    private void setupButtonListener(){

        btn_login.setOnClickListener(v -> {
            updateToLoginLayout();
        });

        btn_register.setOnClickListener(v -> {
            updateToRegisterLayout();
        });

        btn_accept.setOnClickListener(v -> {

            HashMap<String,String> values = getFieldValues();

            //Si el boton de login esta habilitado, entonces significa que estoy registrando un usuario
            //Si el boton de register esta habilitado, entonces significa que estoy haciendo el login de un usuario
            if(btn_login.isEnabled()){
                /*
                    Datos necesarios para el registro de un usuario
                    {
                        "name": string,
                        "lastname": string,
                        "dni": int,
                        "email": string,
                        "password": string,
                        "commission": int,
                        "group": int
                    }
                */
                presenter.processRegister(values);
            }
            else{
                /*
                    Datos necesarios para el login de un usuario
                    {
                        "email": string,
                        "password": string,
                    }
                */
                presenter.processLogin(values);
            }
        });

    }
}