package com.skdirect.interfacee;

import android.widget.CheckBox;

import com.skdirect.model.UserLocationModel;

public interface MakeDefaultInterface {
    void defaultOnClick(UserLocationModel userLocationModel, int position);
    void deleteLocationClick(UserLocationModel userLocationModel);
}
