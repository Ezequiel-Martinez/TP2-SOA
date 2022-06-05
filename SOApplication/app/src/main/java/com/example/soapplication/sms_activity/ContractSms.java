package com.example.soapplication.sms_activity;

public interface ContractSms {
    interface View {
        void showProgress(); // method to display progress bar when next random course details is being fetched
    }

    interface Model {
        void sentSMS(String s1);
        String getNumRan();
    }

    interface Presenter {
        void onButtonClick2(String s2) throws Exception;
        boolean onButtonClick3(String s3) throws Exception;
        void onDestroy(); // method to destroy lifecycle of MainActivity
    }

}
