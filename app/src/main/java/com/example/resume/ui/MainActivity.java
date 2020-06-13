package com.example.resume.ui;

import android.Manifest;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.resume.R;
import com.example.resume.fragment.CheckinginFragment;
import com.example.resume.fragment.ListFragment;
import com.example.resume.fragment.MeFragment;
import com.lbb.mvplibrary.base.BaseActivity;
import com.next.easynavigation.view.EasyNavigationBar;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wenming.library.LogReport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivity {

    @BindView(R.id.navigationBar)
    EasyNavigationBar navigationBar;

    private String[] tabText = {"打卡", "考勤", "我的"};
    //未选中icon
    private int[] normalIcon = {R.drawable.checkingin, R.drawable.list, R.drawable.my};

    //选中时icon
    private int[] selectIcon = {R.drawable.select_checkingin, R.drawable.select_list, R.drawable.select_my};

    private List<Fragment> fragments = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        checkPermissionRequestduo(this);


        fragments.add(new CheckinginFragment());
        fragments.add(new ListFragment());
        fragments.add(new MeFragment());


        navigationBar.titleItems(tabText)
                .normalIconItems(normalIcon)
                .selectIconItems(selectIcon)
                .selectTextColor(Color.parseColor("#6577FF"))
                .normalTextColor(Color.parseColor("#8a8a8a"))
                .fragmentList(fragments)
                .fragmentManager(getSupportFragmentManager())
                .canScroll(true)
                .build();
    }

    /**
     * 申请多个权限
     * */
    public void checkPermissionRequestduo(FragmentActivity activity) {
        RxPermissions permissions = new RxPermissions(activity);
        permissions.setLogging(true);
        Disposable subscribe = permissions.request(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA
               ,Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE
                )
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (!aBoolean){
                            checkPermissionRequestduo(MainActivity.this);
                        }else {
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {

    }
}
