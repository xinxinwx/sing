package com.example.resume.mvp.presenter;

import com.example.resume.mvp.view.LoginView;
import com.example.resume.retrofit.ApiStores;
import com.example.resume.retrofit.RxSchedulers;
import com.lbb.mvplibrary.base.BasePresenter;
import com.lbb.mvplibrary.retrofit.ApiManager;
import com.lbb.mvplibrary.utils.Validator;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;

public class LoginPresenter extends BasePresenter<LoginView> {

    private final ApiStores apiService;

    public LoginPresenter(LoginView view) {
        attachView(view);
        apiService = ApiManager.getInstance().getApiService(ApiStores.class);
    }


    public void login(String tel,String pwd){
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
        Disposable subscribe1 = apiService.Login(map)
                .compose(RxSchedulers.Obs_io_main())
                .subscribe(mgMode -> {
                            mvpView.getDataSuccess(mgMode);
                        },
                        throwable -> {
                            mvpView.getDataFail(throwable.getMessage());
                        });
    }

}
