package com.lbb.mvplibrary.base;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.lbb.mvplibrary.R;
import com.lbb.mvplibrary.app.AppManager;
import com.lbb.mvplibrary.swipebacklayout.app.SwipeBackActivity;
import com.lbb.mvplibrary.utils.ActivityAnimUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;
import retrofit2.Call;

/**
 * Created by WuXiaolong on 2015/9/23.
 * github:https://github.com/WuXiaolong/
 * 微信公众号：吴小龙同学
 * 个人博客：http://wuxiaolong.me/
 */
public abstract class BaseActivity extends SwipeBackActivity implements View.OnClickListener {
    public Activity mActivity;
    private CompositeDisposable mCompositeDisposable;
    private List<Call> calls;
    private long exitTime = 0;

    /**
     * 存储全局的activity
     */
    private static List<Activity> mActivities = new LinkedList<Activity>();

    /**
     * 当前前台的activity
     */
    private static Activity mForegroundActivity;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        mActivity = this;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppManager.getInstance().addActivity(this);
        AppManager.getActivityStackInfos();


        //设置全屏
		  transparencyBar(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//android6.0以后可以对状态栏文字颜色和图标进行修改
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        isNetworkConnected(this);
//       requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉标题
        getSupportActionBar().hide();
        mActivity = this;
        synchronized (mActivities) {
            mActivities.add(this);
        }



        //因为不是所有的系统都可以设置颜色的，在4.4以下就不可以。。有的说4.1，所以在设置的时候要检查一下系统版本是否是4.1以上
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(getResources().getColor(R.color.title_color));
//        }

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

                            }

                            @Override
                            public void onCustomerChildClick(View view) {

                            }
                        }).build();
        return statusLayoutManager;

    }




    public static void transparencyBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = activity.getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }






    public void startActivity(Class<?> clazz) {
        Intent intent = new Intent(this,clazz);
        startActivity(intent);
        slideRightIn();
    }



    protected void startActivity(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        slideRightIn();

    }


    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        mActivity = this;
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        mActivity = this;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        slideLeftOut();
    }

    /**
     * 获取前台activity
     */
    public Activity getForegroundActivity() {
        return mForegroundActivity;
    }

    /**
     * 返回上页
     */
/*    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            Activity foregroundActivity = getForegroundActivity();
            if (foregroundActivity != null) {
                if ("MainActivity".equals(foregroundActivity.getClass().getSimpleName())) {
                    exit();
                } else {
                    AppManager.getInstance().killActivity(this);
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }*/


    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            NotificationManager systemService =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            systemService.cancelAll();
            AppManager.getInstance().killAllActivity();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    @Override
    protected void onDestroy() {
        callCancel();
        onUnsubscribe();
        super.onDestroy();
    }


    /**
     * 判断是否有网络连接
     */
    public boolean isNetworkConnected(Context context) {
        ConnectivityManager mConnectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {   //判断网络连接是否打开
            return mNetworkInfo.isConnected();
        } else {
            Toast.makeText(context, "请检查您的网络连接", Toast.LENGTH_SHORT).show();
        }
        return false;
    }




    public void addCalls(Call call) {
        if (calls == null) {
            calls = new ArrayList<>();
        }
        calls.add(call);
    }

    private void callCancel() {
        if (calls != null && calls.size() > 0) {
            for (Call call : calls) {
                if (!call.isCanceled())
                    call.cancel();
            }
            calls.clear();
        }
    }


    public <T> void addSubscription(Observable<T> observable, DisposableObserver<T> observer) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(observer);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void addSubscription(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    public void onUnsubscribe() {
        //取消注册，以避免内存泄露
        if (mCompositeDisposable != null)
            mCompositeDisposable.dispose();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                super.onBackPressed();//返回
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void toastShow(int resId) {
        Toast.makeText(mActivity, resId, Toast.LENGTH_SHORT).show();
    }

    public void toastShow(String resId) {
        Toast.makeText(mActivity, resId, Toast.LENGTH_SHORT).show();
    }


    /**
     * 进入
     */
    protected void slideRightIn() {
        ActivityAnimUtils.to(mActivity);
    }


    /**
     * 右边划出
     */
    protected void slideLeftOut() {
        ActivityAnimUtils.out(mActivity);
    }

}
