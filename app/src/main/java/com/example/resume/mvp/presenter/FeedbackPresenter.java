package com.example.resume.mvp.presenter;

import com.example.resume.mvp.mode.FeedbackMode;
import com.example.resume.mvp.mode.UpdatapwdMode;
import com.example.resume.mvp.view.FeedbackView;
import com.example.resume.retrofit.ApiStores;
import com.example.resume.retrofit.RxSchedulers;
import com.lbb.mvplibrary.base.BasePresenter;
import com.lbb.mvplibrary.retrofit.ApiManager;
import com.lbb.mvplibrary.utils.Validator;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class FeedbackPresenter extends BasePresenter<FeedbackView> {

    private final ApiStores apiService;

    public FeedbackPresenter(FeedbackView view) {
        attachView(view);
        apiService = ApiManager.getInstance().getApiService(ApiStores.class);
    }


    public void Feedback(String uid,String content){

        Map<String,String> map=new HashMap<>();
        map.put("uid",uid);
        map.put("content",content);
        Disposable subscribe1 = apiService.Opinion(map)
                .compose(RxSchedulers.Obs_io_main())
                .subscribe(feedbackMode -> mvpView.getDataSuccess(feedbackMode),
                        throwable -> mvpView.getDataFail(throwable.getMessage()));
    }

}
