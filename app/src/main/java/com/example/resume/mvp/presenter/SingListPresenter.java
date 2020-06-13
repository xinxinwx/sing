package com.example.resume.mvp.presenter;

import com.example.resume.mvp.mode.SingListMode;
import com.example.resume.mvp.view.SingListView;
import com.example.resume.retrofit.ApiStores;
import com.example.resume.retrofit.RxSchedulers;
import com.lbb.mvplibrary.base.BasePresenter;
import com.lbb.mvplibrary.retrofit.ApiManager;
import com.lbb.mvplibrary.utils.Validator;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class SingListPresenter extends BasePresenter<SingListView> {

    private final ApiStores apiService;

    public SingListPresenter(SingListView view) {
        attachView(view);
        apiService = ApiManager.getInstance().getApiService(ApiStores.class);
    }


    public void SingList(String uid,String cockdate,String page){

        Map<String,String> map=new HashMap<>();
        map.put("uid",uid);
        map.put("cockdate",cockdate);
        map.put("page",page);
        Disposable subscribe1 = apiService.Checkinlist(map)
                .compose(RxSchedulers.Obs_io_main())
                .subscribe(singListMode -> mvpView.getDataSuccess(singListMode),
                        throwable -> mvpView.getDataFail(throwable.getMessage()));
    }

}
