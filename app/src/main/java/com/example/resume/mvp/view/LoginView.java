package com.example.resume.mvp.view;

import com.example.resume.mvp.mode.LoginMode;

public interface LoginView {
    void getDataSuccess(LoginMode loginMode);

    void getDataFail(String msg);

    void toast(String msg);

    void showloading();
}
