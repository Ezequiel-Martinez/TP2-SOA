package com.example.soapplication.sms_activity.model;

import android.telephony.SmsManager;
import android.util.Log;
import com.example.soapplication.sms_activity.ContractSms;
import java.util.Random;

public class ModelSms implements ContractSms.Model {
    private final int NumMin = 1000; //Numero minimo que se puede recibir en el SMS
    private final int NumMax = 9999; //Numero maximo que se puede recibir en el SMS
    String numRan = "0";

    @Override
    public void sentSMS(String num) {
        numRan = getRandomNum();
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(num, null, numRan,null,null);
    }

    private String getRandomNum() {
        Random random = new Random();
        int num = random.nextInt(NumMax - NumMin + 1) + NumMin;
        return String.valueOf(num);
    }

    @Override
    public String getNumRan(){
        return numRan;
    }
}
