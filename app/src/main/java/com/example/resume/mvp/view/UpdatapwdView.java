package com.example.resume.mvp.view;

import com.example.resume.mvp.mode.UpdatapwdMode;

public interface UpdatapwdView {
    void getDataSuccess(UpdatapwdMode updatapwdMode);

    void getDataFail(String msg);

    void toast(String msg);

    void showloading();

}
