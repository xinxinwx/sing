package com.example.resume.mvp.view;

import com.example.resume.mvp.mode.CheckinMode;
import com.example.resume.mvp.mode.SinginMode;
import com.example.resume.mvp.mode.SiteMode;

public interface CheckinView {


    void getSuccess(CheckinMode checkinMode);

    void getDataFail(String msg);
}
