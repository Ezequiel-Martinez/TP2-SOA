package com.example.soapplication.sms_activity.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.example.soapplication.R;
import com.example.soapplication.api_unlam_activity.view.ActivityApiUnlam;
import com.example.soapplication.info_activity.view.ActivityInfo;
import com.example.soapplication.sms_activity.ContractSms;
import com.example.soapplication.sms_activity.presenter.PresenterSms;
import com.example.soapplication.sms_activity.model.ModelSms;

public class ActivitySms extends AppCompatActivity implements ContractSms.View {

    ContractSms.Presenter presenter;
    private EditText num, cod;
    private Button SMSEnviar, CodEnviar;
    private ImageButton btn_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        num = findViewById(R.id.editTextTextPersonName2);
        cod = findViewById(R.id.editTextTextPersonName);
        SMSEnviar = findViewById(R.id.button2);
        CodEnviar = findViewById(R.id.button3);
        btn_info = findViewById(R.id.btn_info);

        presenter = new PresenterSms(this, new ModelSms());

        initObjects();

        //Condicion para ver si se tienen permisos para enviar un SMS
        if(ActivityCompat.checkSelfPermission(ActivitySms.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(ActivitySms.this, new String[]{Manifest.permission.SEND_SMS},1);
        }

        SMSEnviar.setOnClickListener((View v)->{
            try {
                presenter.onButtonClick2(num.getText().toString());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        CodEnviar.setOnClickListener((View v)->{
            try {
                if(presenter.onButtonClick3(cod.getText().toString())){
                    Intent i = new Intent(this, ActivityApiUnlam.class);
                    startActivity(i);
                    finish();
                }
                else
                    mensaje();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        btn_info.setOnClickListener(v -> {
            Intent intent = new Intent(this, ActivityInfo.class);
            startActivity(intent);
            finish();
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void showProgress() {
        cod.setVisibility(View.VISIBLE);
        CodEnviar.setVisibility(View.VISIBLE);
    }

    private void initObjects(){
        cod.setVisibility(View.INVISIBLE);
        CodEnviar.setVisibility(View.INVISIBLE);
    }

    private void mensaje(){
        Toast.makeText(this, "Codigo Invalido", Toast.LENGTH_SHORT).show();
    }
}