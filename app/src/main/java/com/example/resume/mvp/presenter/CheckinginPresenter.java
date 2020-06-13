package com.example.resume.mvp.presenter;

import com.example.resume.mvp.mode.SinginMode;
import com.example.resume.mvp.view.CheckinginView;
import com.example.resume.retrofit.ApiStores;
import com.example.resume.retrofit.RxSchedulers;
import com.lbb.mvplibrary.base.BasePresenter;
import com.lbb.mvplibrary.retrofit.ApiManager;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class CheckinginPresenter extends BasePresenter<CheckinginView> {

    private final ApiStores apiService;

    public CheckinginPresenter(CheckinginView view) {
        attachView(view);
        apiService = ApiManager.getInstance().getApiService(ApiStores.class);
    }


    //获取位置
    public void getSite(String uid){
        Map<String,String> map=new HashMap<>();
        map.put("id",uid);
        Disposable subscribe1 = apiService.Site(map)
                .compose(RxSchedulers.Obs_io_main())
                .subscribe(siteMode -> mvpView.getsiteSuccess(siteMode),
                        throwable -> mvpView.getDataFail(throwable.getMessage()));
    }

    //获取当天考勤信息
    public void singin(String uid,String cockdate){
        Map<String,String> map=new HashMap<>();
        map.put("uid",uid);
        map.put("cockdate",cockdate);
        Disposable subscribe1 = apiService.Singin(map)
                .compose(RxSchedulers.Obs_io_main())
                .subscribe(singinMode ->
                                mvpView.getSuccess(singinMode),
                        throwable ->
                                mvpView.getDataFail(throwable.getMessage()));
    }

}
