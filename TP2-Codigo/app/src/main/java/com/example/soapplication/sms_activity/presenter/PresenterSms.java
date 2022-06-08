package com.example.soapplication.sms_activity.presenter;

import com.example.soapplication.sms_activity.ContractSms;
import java.util.Objects;

public class PresenterSms implements ContractSms.Presenter {
    private ContractSms.View mainView;
    private ContractSms.Model model;

    public PresenterSms(ContractSms.View mainView, ContractSms.Model model) {
        this.mainView = mainView;
        this.model = model;
    }

    @Override
    public void onButtonClick2(String num) throws Exception {
        if (mainView != null) {
            mainView.showProgress();
        }
        model.sentSMS(num);
    }

    @Override
    public boolean onButtonClick3(String cod) throws Exception {
        String numRan = model.getNumRan();
        if (Objects.equals(cod,numRan)) {
            return true;
        }
        else
            return false;
    }

    @Override
    public void onDestroy() {
        mainView = null;
    }
}
