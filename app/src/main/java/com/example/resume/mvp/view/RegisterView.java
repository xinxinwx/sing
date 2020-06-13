package com.example.resume.mvp.view;

import com.example.resume.mvp.mode.RegisterMode;

public interface RegisterView {
    void getDataSuccess(RegisterMode registerMode);

    void getDataFail(String msg);

    void toast(String msg);

    void showloading();

}
