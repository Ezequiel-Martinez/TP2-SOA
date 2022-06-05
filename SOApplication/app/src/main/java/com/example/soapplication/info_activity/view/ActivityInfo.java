package com.example.soapplication.info_activity.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.soapplication.R;
import com.example.soapplication.info_activity.ContractInfo;
import com.example.soapplication.info_activity.model.ModelInfo;
import com.example.soapplication.info_activity.presenter.PresenterInfo;
import com.example.soapplication.sms_activity.view.ActivitySms;

public class ActivityInfo extends AppCompatActivity implements ContractInfo.View{

    private TextView tv_info;
    private Button btn_back;
    private ContractInfo.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        btn_back = findViewById(R.id.btn_back);
        tv_info = findViewById(R.id.tv_info);

        presenter = new PresenterInfo(this,new ModelInfo());
        presenter.getSharedPref();

        btn_back.setOnClickListener(v -> {
            Intent i = new Intent(this, ActivitySms.class);
            startActivity(i);
            finish();
        });

    }

    @Override
    public void setInfo(String info) {
        tv_info.setText(info);
    }
}
