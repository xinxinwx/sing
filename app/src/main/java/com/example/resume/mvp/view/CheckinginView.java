package com.example.resume.mvp.view;

import com.example.resume.mvp.mode.SinginMode;
import com.example.resume.mvp.mode.SiteMode;

public interface CheckinginView {

    void getsiteSuccess(SiteMode siteMode);

    void getSuccess(SinginMode singinMode);

    void getDataFail(String msg);
}
