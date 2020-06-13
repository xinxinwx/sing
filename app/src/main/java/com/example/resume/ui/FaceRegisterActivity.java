package com.example.resume.ui;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.example.resume.R;
import com.example.resume.mvp.facemode.Adduser;
import com.example.resume.mvp.mode.AdduserMode;
import com.example.resume.mvp.mode.Detect;
import com.example.resume.mvp.mode.DetectMode;
import com.example.resume.myview.TitleBarView;
import com.example.resume.utils.BitmapToBase64;
import com.example.resume.utils.NetUtil;
import com.lbb.mvplibrary.base.BaseActivity;
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
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

public class FaceRegisterActivity extends BaseActivity implements
        StreamStatusCallback,
        AudioSourceCallback,
        StreamingSessionListener,
        TitleBarView.BtnClickListener {

    CameraPreviewFrameView mCameraPreviewSurfaceView;
    @BindView(R.id.titleview)
    TitleBarView titleview;
    @BindView(R.id.ly)
    LinearLayout ly;
    private MediaStreamingManager mMediaStreamingManager;
    private StreamingProfile mProfile;
    private String TAG = "StreamingByCameraActivity";
    private ImageView iv;
    private BitmapToBase64 bitmapToBase64;
    private StatusLayoutManager statusLayoutManager;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StreamingEnv.init(getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faceregister);
        ButterKnife.bind(this);
        init();

        mMediaStreamingManager.switchCamera(CameraStreamingSetting.CAMERA_FACING_ID.CAMERA_FACING_FRONT);
        checkPermissionRequestduo(this);
        initdata();

        titleview.setMainTitle("人脸注册");
        titleview.setMainTitleColor(Color.WHITE);
        titleview.setTitleBarListener(this);

        statusLayoutManager = setLayout(ly);

    }

    private void initdata() {
        bitmapToBase64 = new BitmapToBase64();
        Disposable subscribe = NetUtil.getmInstance().getnetjson("https://aip.baidubce.com/")
                .GetToken()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getToken -> {
                    Log.d("成功", getToken.getAccess_token());
                    String access_token = getToken.getAccess_token();
                    SPUtils.put(FaceRegisterActivity.this, "access_token", access_token);
                }, throwable ->
                        Log.d("失败", throwable.getMessage()));


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

        statusLayoutManager.showLoadingLayout();
        mMediaStreamingManager.captureFrame(0, 0, new FrameCapturedCallback() {
            private Bitmap bitmap;

            @Override
            public void onFrameCaptured(Bitmap bmp) {
                if (bmp == null) {
                    return;
                }
                bitmap = bmp;
                String base64 = BitmapToBase64.bitmapToBase64(bitmap);
                MMKV mmkv = MMKV.defaultMMKV();
                String name = mmkv.decodeString("name");
                int id = mmkv.decodeInt("id");
                String access_token = (String) SPUtils.get(FaceRegisterActivity.this, "access_token", "");
                Detect detect = new Detect(base64, "BASE64");

                List<Detect> list = new ArrayList<>();
                list.add(detect);
                //人脸检测
                Disposable subscribe1 = NetUtil.getmInstance().getnetjson("https://aip.baidubce.com/")
                        .Detect(access_token, list)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<DetectMode>() {
                            @Override
                            public void accept(DetectMode detectMode) throws Exception {
                                if (detectMode.getError_msg().equals("pic not has face")) {
                                    toastShow("图片没有正面");
                                    statusLayoutManager.showSuccessLayout();
                                } else if (detectMode.getResult().getFace_list().get(0).getFace_probability() == 1) {
                   //                //人脸注册
                Adduser adduser = new Adduser(base64, "BASE64", "xcx_face_1", id+"", name);
                Disposable subscribe = NetUtil.getmInstance().getnetjson("https://aip.baidubce.com/")
                        .Adduser(access_token, adduser)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<AdduserMode>() {
                            @Override
                            public void accept(AdduserMode adduserMode) throws Exception {
                                statusLayoutManager.showSuccessLayout();
                                toastShow("注册成功");
                                finish();
                            }
                        }, throwable -> Log.d("失败", throwable.getMessage()));
                                } else {
                                    toastShow("请从新注册人脸");
                                    statusLayoutManager.showSuccessLayout();
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                statusLayoutManager.showSuccessLayout();
                                Log.d(TAG, "accept: " + throwable.getMessage());
                            }
                        });




//                //人脸识别
//              /*  Faceuser faceuser=new Faceuser(base64,"BASE64","xcx_face_1");
//                String access_token = (String) SPUtils.get(MainActivity.this, "access_token", "");
//                Disposable subscribe = NetUtil.getmInstance().getnetjson("https://aip.baidubce.com/")
//                        .Face(access_token,faceuser)
//                        .subscribeOn(Schedulers.newThread())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Consumer<FaceMode>() {
//                            @Override
//                            public void accept(FaceMode faceMode) throws Exception {
//                               if (faceMode.getError_code()==0){
//                                   String user_info = faceMode.getResult().getFace_list().get(0).getUser_list().get(0).getUser_info();
//                                   Log.d("成功", user_info);
//                                   Toast.makeText(MainActivity.this, user_info, Toast.LENGTH_SHORT).show();
//                               }else {
//                                   Toast.makeText(MainActivity.this, "失败，未在人脸库中找到人脸", Toast.LENGTH_SHORT).show();
//                               }
//
//                            }
//                        }, new Consumer<Throwable>() {
//                            @Override
//                            public void accept(Throwable throwable) throws Exception {
//                                Log.d("失败", throwable.getMessage());
//                            }
//                        });*/
            }
        });
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
                            checkPermissionRequestduo(FaceRegisterActivity.this);
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void leftClick() {
        finish();
    }

    @Override
    public void rightClick() {

    }
}
