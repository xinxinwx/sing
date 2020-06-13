package com.example.resume.mvp.presenter;

import com.example.resume.mvp.mode.CheckinMode;
import com.example.resume.mvp.view.CheckinView;
import com.example.resume.retrofit.ApiStores;
import com.example.resume.retrofit.RxSchedulers;
import com.lbb.mvplibrary.base.BasePresenter;
import com.lbb.mvplibrary.retrofit.ApiManager;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class CheckinPresenter extends BasePresenter<CheckinView> {

    private final ApiStores apiService;

    public CheckinPresenter(CheckinView view) {
        attachView(view);
        apiService = ApiManager.getInstance().getApiService(ApiStores.class);
    }



    //考勤打卡
    public void singin(String morninglc,String cockdate,String morningtime,String uid){
        Map<String,String> map=new HashMap<>();
        map.put("morninglc",morninglc);
        map.put("cockdate",cockdate);
        map.put("morningtime",morningtime);
        map.put("uid",uid);
        Disposable subscribe1 = apiService.Checkin(map)
                .compose(RxSchedulers.Obs_io_main())
                .subscribe(checkinMode -> mvpView.getSuccess(checkinMode),
                        throwable -> mvpView.getDataFail(throwable.getMessage()));
    }

}
