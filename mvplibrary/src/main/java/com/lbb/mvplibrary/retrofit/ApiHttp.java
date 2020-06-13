package com.lbb.mvplibrary.retrofit;


import com.lbb.mvplibrary.utils.LoggingInterceptor;

import org.apache.http.conn.ssl.SSLSocketFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Jenly <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */

public class ApiHttp {

    /**
     *  默认超时时间 单位/秒
     */
    public static final int DEFAULT_TIME_OUT = 20;

    private int mTimeout = DEFAULT_TIME_OUT;

    private String mBaseUrl;

    private OkHttpClient mOkHttpClient;

    private Retrofit mRetrofit;


    public ApiHttp(String baseUrl){
        this(baseUrl,DEFAULT_TIME_OUT);
    }

    /**
     *
     * @param baseUrl
     * @param timeout  超时时间 单位/秒
     */
    public ApiHttp(String baseUrl, int timeout){
        this.mBaseUrl = baseUrl;
        this.mTimeout = timeout;
    }

    public Retrofit getRetrofit(){
        if(mRetrofit == null){
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(mBaseUrl)
                    .addConverterFactory( GsonConverterFactory.create())
//                    .addConverterFactory( MyConvertFactory.create())
                    .addCallAdapterFactory( RxJava2CallAdapterFactory.create())
                    .client(getOkHttpClient())
                    .build();
        }
        return mRetrofit;
    }

    public OkHttpClient getOkHttpClient(){

        if(mOkHttpClient == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //    LoggingInterceptor loggingInterceptor=new LoggingInterceptor();
            mOkHttpClient = new OkHttpClient.Builder()

                    .connectTimeout(100, TimeUnit.SECONDS)
                    .readTimeout(100, TimeUnit.SECONDS)
                    .writeTimeout(100, TimeUnit.SECONDS)
                    .addInterceptor(loggingInterceptor)
                    .hostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
                    .build();
        }

        return mOkHttpClient;
    }

    public void setOkHttpClient(OkHttpClient okHttpClient) {
        this.mOkHttpClient = okHttpClient;
    }

    public void setRetrofit(Retrofit retrofit) {
        this.mRetrofit = retrofit;
    }
}
