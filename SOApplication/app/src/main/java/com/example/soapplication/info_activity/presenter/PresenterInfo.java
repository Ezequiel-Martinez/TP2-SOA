package com.example.soapplication.info_activity.presenter;

import android.content.Context;
import com.example.soapplication.api_unlam_activity.model.ModelApiUnlam;
import com.example.soapplication.info_activity.ContractInfo;

public class PresenterInfo implements ContractInfo.Presenter, ContractInfo.Model.OnEventListener {

    private ContractInfo.View view;
    private ContractInfo.Model model;

    public PresenterInfo(ContractInfo.View view, ContractInfo.Model model){
        this.view = view;
        this.model = model;
    }

    @Override
    public void getSharedPref() {
        model.getSharedPref(this,((Context)view).getSharedPreferences(ModelApiUnlam.SHARED_PREF,Context.MODE_PRIVATE));
    }

    @Override
    public void displaySharedPref(String info) {
        view.setInfo(info);
    }
}
