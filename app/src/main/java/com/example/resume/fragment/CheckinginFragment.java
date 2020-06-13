package com.example.resume.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.resume.R;
import com.example.resume.mvp.mode.SinginMode;
import com.example.resume.mvp.mode.SiteMode;
import com.example.resume.mvp.presenter.CheckinginPresenter;
import com.example.resume.mvp.view.CheckinginView;
import com.example.resume.myview.TitleBarView;
import com.example.resume.ui.FaceActivity;
import com.lbb.mvplibrary.base.MvpFragment;
import com.tencent.mmkv.MMKV;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CheckinginFragment extends MvpFragment<CheckinginPresenter> implements LocationSource,
        AMapLocationListener, View.OnClickListener,
        CheckinginView {

    @BindView(R.id.morning_tv)
    TextView morningTv;
    @BindView(R.id.night_tv)
    TextView nightTv;
    @BindView(R.id.map)
    MapView map;
    @BindView(R.id.tbv_titlebar)
    TitleBarView tbvTitlebar;
    @BindView(R.id.singin)
    LinearLayout singin;
    @BindView(R.id.face)
    ImageView face;
    @BindView(R.id.errsing)
    LinearLayout errsing;
    @BindView(R.id.refresh)
    TextView refresh;
    @BindView(R.id.refresh1)
    TextView refresh1;
    @BindView(R.id.location_tv)
    TextView locationTv;
    @BindView(R.id.location1_tv)
    TextView location1Tv;
    private Unbinder unbinder;

    private AMap aMap;
    private MapView mapView;
    // 处理定位更新
    private OnLocationChangedListener mListener;
    // 定位
    private AMapLocationClient mlocationClient;

    private AMapLocationClientOption mLocationOption;
    private Activity mActivity;

    private Double latitude;
    private Double longitude;
    private String address;
    private MyLocationStyle myLocationStyle;
    private double latitude1;
    private double longitude1;
    private int id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity = getActivity();
        View inflate = View.inflate(mActivity, R.layout.fragment_checkingin, null);
        unbinder = ButterKnife.bind(this, inflate);
        map.onCreate(savedInstanceState);// 此方法必须重写
        initdata();
        Glide.with(getActivity()).load(R.drawable.face).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(face);
        return inflate;
    }


    private void initdata() {
        tbvTitlebar.hideleft();
        tbvTitlebar.setMainTitle("打卡");
        tbvTitlebar.setMainTitleColor(Color.WHITE);

        singin.setOnClickListener(this);
        refresh.setOnClickListener(this);
        refresh1.setOnClickListener(this);

    }


    /**
     * 初始化
     */
    private void init() {

        if (aMap == null) {
            aMap = map.getMap();
        }
        setUpMap();
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        aMap.setLocationSource(this);// 设置定位监听
        // 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
        //初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
        myLocationStyle.interval(60000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.moveCamera(CameraUpdateFactory.zoomTo(15));

    }


    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        super.onResume();
        init();
        map.onResume();


        MMKV kv = MMKV.defaultMMKV();
        id = kv.decodeInt("id");

        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateNowStr = sdf.format(d);
        mvpPresenter.singin(id+"",dateNowStr);

        mvpPresenter.getSite(id + "");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (mvpPresenter != null) {
                MMKV kv = MMKV.defaultMMKV();
                id = kv.decodeInt("id");
                mvpPresenter.getSite(id + "");
            }

        }
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
        map.onPause();
        deactivate();
    }


    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        map.onSaveInstanceState(outState);
    }

    @Override
    protected CheckinginPresenter createPresenter() {
        return new CheckinginPresenter(this);
    }

    @Override
    protected void initretry() {

    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        map.onDestroy();
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {

                longitude = amapLocation.getLongitude();
                latitude = amapLocation.getLatitude();
                address = amapLocation.getAddress();

                MMKV mmkv = MMKV.defaultMMKV();
                double longitude1 = mmkv.decodeDouble("longitude");
                double latitude1 = mmkv.decodeDouble("latitude");
                LatLng latLng1 = new LatLng(latitude, longitude);
                LatLng latLng2 = new LatLng(latitude1, longitude1);
                Log.d("哈哈", "onLocationChanged: " + address);
                float distance = AMapUtils.calculateLineDistance(latLng1, latLng2);
                float qianmifload = (float) distance / 1000;
                if (qianmifload <= 1) {
                    singin.setVisibility(View.VISIBLE);
                    errsing.setVisibility(View.GONE);
                } else {
                    errsing.setVisibility(View.VISIBLE);
                    singin.setVisibility(View.GONE);
                }

                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr", errText);

            }
        }
    }

    public  float round(float v, int scale)
    {
        if (scale < 0)
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        BigDecimal bgNum1 = new BigDecimal(Float.toString(v));
        BigDecimal bgNum2 = new BigDecimal("1");
        return bgNum1.divide(bgNum2, scale, BigDecimal.ROUND_HALF_UP).floatValue();
        // return b.setScale(scale, BigDecimal.ROUND_HALF_UP).floatValue();
    }


    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(mActivity);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.singin:
                Bundle bundle = new Bundle();
                bundle.putDouble("latitude", latitude);
                bundle.putDouble("longitude", longitude);
                bundle.putString("address", address);
                startActivity(FaceActivity.class, bundle);
                break;
            case R.id.refresh:
                if (mvpPresenter != null) {
                    MMKV kv = MMKV.defaultMMKV();
                    id = kv.decodeInt("id");
                    mvpPresenter.getSite(id + "");
                }

                break;
            case R.id.refresh1:
                if (mvpPresenter != null) {
                    MMKV kv = MMKV.defaultMMKV();
                    id = kv.decodeInt("id");
                    mvpPresenter.getSite(id + "");
                }
                break;
            default:
                break;
        }
    }


    @Override
    public void getsiteSuccess(SiteMode siteMode) {
        double latitude = siteMode.getData().getLatitude();
        double longitude = siteMode.getData().getLongitude();
        MMKV kv = MMKV.defaultMMKV();
        kv.encode("latitude", latitude);
        kv.encode("longitude", longitude);
    }

    @Override
    public void getSuccess(SinginMode singinMode) {
        if (singinMode.getCode().equals("200")) {
            String morningtime = singinMode.getData().getMorningtime();
            String night = singinMode.getData().getNight();
            String morninglc = singinMode.getData().getMorninglc();
            String nightlc = singinMode.getData().getNightlc();
            if (morningtime.isEmpty()){
                morningTv.setText("早上打卡时间  "+"未打卡");
            }else {
                morningTv.setText("早上打卡时间  "+morningtime);
            }
            if (night.isEmpty()){
                nightTv.setText("晚上打卡时间  "+"未打卡");
            }else {
                nightTv.setText("晚上打卡时间  "+night);
            }
            locationTv.setText(morninglc);
            location1Tv.setText(nightlc);

        }
    }

    @Override
    public void getDataFail(String msg) {

    }


}
