package com.lbb.mvplibrary.base;

import android.os.Bundle;
import android.view.View;


import com.lbb.mvplibrary.R;

import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;


/**
 * Created by WuXiaolong on 2016/3/30.
 * github:https://github.com/WuXiaolong/
 * 微信公众号：吴小龙同学
 * 个人博客：http://wuxiaolong.me/
 */
public abstract class MvpActivity<P extends BasePresenter> extends BaseActivity {
    protected P mvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mvpPresenter = createPresenter();
        super.onCreate(savedInstanceState);
        initretry();
    }

    protected StatusLayoutManager setLayout(View view){
        StatusLayoutManager statusLayoutManager = new StatusLayoutManager.Builder(view)
                .setDefaultLoadingText("拼命加载中...")
                .setEmptyLayout(R.layout.layout_emptyorder)
                .setLoadingLayout(R.layout.layout_progressshow)
                .setErrorLayout(R.layout.activity_error)
                .setErrorClickViewID(R.id.tv_base_again).
                        setOnStatusChildClickListener(new OnStatusChildClickListener() {
                            @Override
                            public void onEmptyChildClick(View view) {
                            }

                            @Override
                            public void onErrorChildClick(View view) {
                                initretry();
                            }

                            @Override
                            public void onCustomerChildClick(View view) {

                            }
                        }).build();
        return statusLayoutManager;

    }





    protected abstract P createPresenter();

    protected abstract void initretry();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
        }
    }

    public void showLoading() {
        //showProgressDialog();
    }

    public void hideLoading() {
       // dismissProgressDialog();
    }
}
