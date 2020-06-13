package com.example.resume.mvp.view;

import com.example.resume.mvp.mode.FeedbackMode;
import com.example.resume.mvp.mode.LoginMode;

public interface FeedbackView {
    void getDataSuccess(FeedbackMode feedbackMode);

    void getDataFail(String msg);
}
