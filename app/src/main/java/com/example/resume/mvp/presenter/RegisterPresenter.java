package com.example.resume.mvp.presenter;

import com.example.resume.mvp.mode.RegisterMode;
import com.example.resume.mvp.view.RegisterView;
import com.example.resume.retrofit.ApiStores;
import com.example.resume.retrofit.RxSchedulers;
import com.lbb.mvplibrary.base.BasePresenter;
import com.lbb.mvplibrary.retrofit.ApiManager;
import com.lbb.mvplibrary.utils.Validator;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class RegisterPresenter extends BasePresenter<RegisterView> {

    private final ApiStores apiService;

    public RegisterPresenter(RegisterView view) {
        attachView(view);
        apiService = ApiManager.getInstance().getApiService(ApiStores.class);
    }


    public void register(String tel,String pwd,String name){
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

        if (name.isEmpty()){
            mvpView.toast("请输入姓名");
            return;
        }
        mvpView.showloading();
        Map<String,String> map=new HashMap<>();
        map.put("tel",tel);
        map.put("pwd",pwd);
        map.put("name",name);
        Disposable subscribe1 = apiService.Register(map)
                .compose(RxSchedulers.Obs_io_main())
                .subscribe(registerMode -> {
                 mvpView.getDataSuccess(registerMode);
                }, throwable -> {
                    mvpView.getDataFail(throwable.getMessage());
                });
    }

}
