package com.example.resume.mvp.presenter;

import com.amap.api.maps.MapView;
import com.example.resume.mvp.mode.UpdatapwdMode;
import com.example.resume.mvp.view.UpdatapwdView;
import com.example.resume.retrofit.ApiStores;
import com.example.resume.retrofit.RxSchedulers;
import com.lbb.mvplibrary.base.BasePresenter;
import com.lbb.mvplibrary.retrofit.ApiManager;
import com.lbb.mvplibrary.utils.Validator;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class UpdatapwdPresenter extends BasePresenter<UpdatapwdView> {

    private final ApiStores apiService;

    public UpdatapwdPresenter(UpdatapwdView view) {
        attachView(view);
        apiService = ApiManager.getInstance().getApiService(ApiStores.class);
    }


    public void Updatapwd(String tel,String pwd){
        if (tel.isEmpty()){
            mvpView.toast("请输入手机号");
            return;
        }
        if (!Validator.isMobile(tel)){
            mvpView.toast("请输入正确的手机号");
            return;
        }

        if (pwd.isEmpty()){
            mvpView.toast("请输入密码");
            return;
        }

        mvpView.showloading();
        Map<String,String> map=new HashMap<>();
        map.put("tel",tel);
        map.put("pwd",pwd);
        Disposable subscribe1 = apiService.Updatapwd(map)
                .compose(RxSchedulers.Obs_io_main())
                .subscribe(updatapwdMode -> {
                    mvpView.getDataSuccess(updatapwdMode);
                }, throwable -> {
                    mvpView.getDataFail(throwable.getMessage());
                });
    }

}
