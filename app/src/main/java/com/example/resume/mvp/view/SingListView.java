package com.example.resume.mvp.view;

import com.example.resume.mvp.mode.SingListMode;

public interface SingListView {
    void getDataSuccess(SingListMode singListMode);

    void getDataFail(String msg);
}
