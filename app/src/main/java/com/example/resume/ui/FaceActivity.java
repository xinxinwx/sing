package com.example.resume.ui;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.example.resume.R;
import com.example.resume.mvp.facemode.Faceuser;
import com.example.resume.mvp.mode.CheckinMode;
import com.example.resume.mvp.mode.GetToken;
import com.example.resume.mvp.presenter.CheckinPresenter;
import com.example.resume.mvp.view.CheckinView;
import com.example.resume.myview.TitleBarView;
import com.example.resume.utils.BitmapToBase64;
import com.example.resume.utils.NetUtil;
import com.lbb.mvplibrary.base.BaseActivity;
import com.lbb.mvplibrary.base.MvpActivity;
import com.lbb.mvplibrary.utils.SPUtils;
import com.qiniu.pili.droid.streaming.AVCodecType;
import com.qiniu.pili.droid.streaming.AudioSourceCallback;
import com.qiniu.pili.droid.streaming.CameraStreamingSetting;
import com.qiniu.pili.droid.streaming.FrameCapturedCallback;
import com.qiniu.pili.droid.streaming.MediaStreamingManager;
import com.qiniu.pili.droid.streaming.StreamStatusCallback;
import com.qiniu.pili.droid.streaming.StreamingEnv;
import com.qiniu.pili.droid.streaming.StreamingProfile;
import com.qiniu.pili.droid.streaming.StreamingSessionListener;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.mmkv.MMKV;

import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class FaceActivity extends MvpActivity<CheckinPresenter> implements
        StreamStatusCallback,
        AudioSourceCallback,
        StreamingSessionListener,
        CheckinView {

    CameraPreviewFrameView mCameraPreviewSurfaceView;
    @BindView(R.id.titleview)
    TitleBarView titleview;
    @BindView(R.id.hint)
    TextView hint;
    private MediaStreamingManager mMediaStreamingManager;
    private StreamingProfile mProfile;
    private String TAG = "StreamingByCameraActivity";
    private int mCurrentCamFacingIndex = 1;
    private TranslateAnimation animation;
    private View line;
    private BitmapToBase64 bitmapToBase64;
    private double latitude;
    private double longitude;
    private String address;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StreamingEnv.init(getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face);
        ButterKnife.bind(this);
        init();
        line = findViewById(R.id.line);

        mMediaStreamingManager.switchCamera(CameraStreamingSetting.CAMERA_FACING_ID.CAMERA_FACING_FRONT);
        checkPermissionRequestduo(this);
        initAnimation();
        initdata();

        titleview.setMainTitle("考勤签到");
        titleview.setMainTitleColor(Color.WHITE);

        Intent intent = getIntent();
        latitude = intent.getDoubleExtra("latitude", 0);
        longitude = intent.getDoubleExtra("longitude", 0);
        address = intent.getStringExtra("address");
        Log.d("哈哈", "onCreate: "+address);
        discern();
    }

    @Override
    protected CheckinPresenter createPresenter() {
        return new CheckinPresenter(this);
    }

    @Override
    protected void initretry() {

    }

    private void initdata() {
        bitmapToBase64 = new BitmapToBase64();
        Disposable subscribe = NetUtil.getmInstance().getnetjson("https://aip.baidubce.com/")
                .GetToken()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GetToken>() {
                    @Override
                    public void accept(GetToken getToken) throws Exception {
                        Log.d("成功", getToken.getAccess_token());
                        String access_token = getToken.getAccess_token();
                        SPUtils.put(FaceActivity.this, "access_token", access_token);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("失败", throwable.getMessage());
                    }
                });


    }

    private void initAnimation() {
        animation = new TranslateAnimation(0, 0, 0, 680);
        animation.setDuration(3000);//设置动画持续时间
        animation.setRepeatCount(10000);//设置重复次数
        animation.setRepeatMode(Animation.REVERSE);//设置反方向执行
        line.setAnimation(animation);
        animation.startNow();
    }

    private void init() {
        //get form you server
        String publishURLFromServer = "rtmpp://xxxx/xx/x";
        mCameraPreviewSurfaceView = findViewById(R.id.cameraPreview_surfaceView);


        try {
            //encoding setting
            mProfile = new StreamingProfile();
            mProfile.setVideoQuality(StreamingProfile.VIDEO_QUALITY_HIGH1)
                    .setAudioQuality(StreamingProfile.AUDIO_QUALITY_MEDIUM2)
                    .setEncodingSizeLevel(StreamingProfile.VIDEO_ENCODING_HEIGHT_480)
                    .setEncoderRCMode(StreamingProfile.EncoderRCModes.QUALITY_PRIORITY)
                    .setPublishUrl(publishURLFromServer);
            //preview setting
            CameraStreamingSetting cameraSetting = new CameraStreamingSetting();
            cameraSetting.setCameraId(Camera.CameraInfo.CAMERA_FACING_BACK)
                    .setContinuousFocusModeEnabled(true)
                    .setCameraPrvSizeLevel(CameraStreamingSetting.PREVIEW_SIZE_LEVEL.MEDIUM)
                    .setCameraPrvSizeRatio(CameraStreamingSetting.PREVIEW_SIZE_RATIO.RATIO_4_3);
            //streaming engine init and setListener
            mMediaStreamingManager = new MediaStreamingManager(this, mCameraPreviewSurfaceView, AVCodecType.SW_VIDEO_WITH_SW_AUDIO_CODEC);  // soft codec
            mMediaStreamingManager.prepare(cameraSetting, mProfile);
//            mMediaStreamingManager.setStreamingStateListener(this);
            mMediaStreamingManager.setStreamingSessionListener(this);
            mMediaStreamingManager.setStreamStatusCallback(this);
            mMediaStreamingManager.setAudioSourceCallback(this);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        mMediaStreamingManager.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // You must invoke pause here.
        mMediaStreamingManager.pause();
        animation.cancel();
    }

    @Override
    public void notifyStreamStatusChanged(StreamingProfile.StreamStatus status) {
        Log.e(TAG, "StreamStatus = " + status);
    }

    @Override
    public void onAudioSourceAvailable(ByteBuffer srcBuffer, int size, long tsInNanoTime, boolean isEof) {
    }

    @Override
    public boolean onRecordAudioFailedHandled(int code) {
        Log.i(TAG, "onRecordAudioFailedHandled");
        return false;
    }

    @Override
    public boolean onRestartStreamingHandled(int code) {
        Log.i(TAG, "onRestartStreamingHandled");
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (mMediaStreamingManager != null) {
                    mMediaStreamingManager.startStreaming();
                }
            }
        }).start();
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public Camera.Size onPreviewSizeSelected(List<Camera.Size> list) {
        return null;
    }

    @Override
    public int onPreviewFpsSelected(List<int[]> list) {
        return -1;
    }

    public void register(View view) {


    }

    private void discern() {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hint.setText("正在识别");
                Date date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String format = dateFormat.format(date);
                Log.d("点击了", latitude + "----" + longitude + "----" + address + "---" + format);

                mMediaStreamingManager.captureFrame(0, 0, new FrameCapturedCallback() {
                    private Bitmap bitmap;

                    @Override
                    public void onFrameCaptured(Bitmap bmp) {
                        if (bmp == null) {
                            return;
                        }
                        bitmap = bmp;
                        String base64 = BitmapToBase64.bitmapToBase64(bitmap);
                        //人脸识别
                        Faceuser faceuser = new Faceuser(base64, "BASE64", "xcx_face_1");
                        String access_token = (String) SPUtils.get(FaceActivity.this, "access_token", "");
                        Disposable subscribe = NetUtil.getmInstance().getnetjson("https://aip.baidubce.com/")
                                .Face(access_token, faceuser)
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(faceMode -> {
                                    Log.d(TAG, "onFrameCaptured: " + faceMode);
                                    if (faceMode.getError_code() == 0) {
                                        String user_info = faceMode.getResult().getFace_list().get(0).getUser_list().get(0).getUser_info();
                                        Toast.makeText(FaceActivity.this, user_info, Toast.LENGTH_SHORT).show();
                                        Date d=new Date();
                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                        String dateNowStr = sdf.format(d);

                                        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                        String dateNowStr1 = sdf1.format(d);

                                         MMKV kv = MMKV.defaultMMKV();
                                         int id = kv.decodeInt("id");
                                         mvpPresenter.singin(address,dateNowStr,dateNowStr1,id+"");
                                    } else if (faceMode.getError_msg().equals("match user is not found")) {
                                        Toast.makeText(FaceActivity.this, "失败，未在人脸库中找到人脸", Toast.LENGTH_SHORT).show();
                                         finish();
                                    } else {
                                        hint.setText("识别失败,请对准人脸");
                                        discern();
                                    }
                                }, throwable -> Log.d("失败", throwable.getMessage()));
                    }
                });
            }
        }, 3000);//3秒后执行Runnable中的run方法


    }


    /**
     * 申请多个权限
     */
    public void checkPermissionRequestduo(FragmentActivity activity) {
        RxPermissions permissions = new RxPermissions(activity);
        permissions.setLogging(true);
        Disposable subscribe = permissions.request(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (!aBoolean) {
                            checkPermissionRequestduo(FaceActivity.this);
                        } else {
                            initAnimation();
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void getSuccess(CheckinMode checkinMode) {
         if (checkinMode.getCode().equals("200")){
             toastShow(checkinMode.getMessage());
             finish();
         }else {
             discern();
         }
    }

    @Override
    public void getDataFail(String msg) {

    }
}
