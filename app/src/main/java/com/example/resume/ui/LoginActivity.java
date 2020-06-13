package com.example.resume.ui;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.resume.R;
import com.example.resume.mvp.mode.LoginMode;
import com.example.resume.mvp.presenter.LoginPresenter;
import com.example.resume.mvp.view.LoginView;
import com.lbb.mvplibrary.base.MvpActivity;
import com.lbb.mvplibrary.utils.LinLog;
import com.tencent.mmkv.MMKV;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

public class LoginActivity extends MvpActivity<LoginPresenter> implements LoginView {

    @BindView(R.id.et_login_phone)
    EditText etLoginPhone;
    @BindView(R.id.tv_login_type)
    TextView tvLoginType;
    @BindView(R.id.et_login_change)
    EditText etLoginChange;
    @BindView(R.id.tv_explain)
    LinearLayout tvExplain;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.updatapwd)
    TextView updatapwd;
    @BindView(R.id.register)
    TextView register;
    @BindView(R.id.ly)
    LinearLayout ly;
    private StatusLayoutManager statusLayoutManager;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        btLogin.setOnClickListener(this);
        register.setOnClickListener(this);
        updatapwd.setOnClickListener(this);

        statusLayoutManager = setLayout(ly);
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected void initretry() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //登录
            case R.id.bt_login:

                String tel = etLoginPhone.getText().toString().trim();
                String pwd = etLoginChange.getText().toString().trim();
                mvpPresenter.login(tel, pwd);
                break;
            //修改密码
            case R.id.updatapwd:
                startActivity(UpdatapwdActivity.class);
                break;
            //注册
            case R.id.register:
                startActivity(RegisterActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    public void getDataSuccess(LoginMode loginMode) {
        statusLayoutManager.showSuccessLayout();
        String code = loginMode.getCode();
        if (code.equals("200")) {
            MMKV kv = MMKV.defaultMMKV();
            kv.encode("id",loginMode.getData().getId());
            kv.encode("latitude",loginMode.getData().getLatitude());
            kv.encode("longitude",loginMode.getData().getLongitude());
            kv.encode("name",loginMode.getData().getName());
            startActivity(MainActivity.class);
        } else {
            toastShow(loginMode.getMessage());
        }
    }

    @Override
    public void getDataFail(String msg) {
        statusLayoutManager.showSuccessLayout();
        LinLog.loge(msg);
    }

    @Override
    public void toast(String msg) {
        toastShow(msg);
    }

    @Override
    public void showloading() {
        statusLayoutManager.showLoadingLayout();
    }
}
