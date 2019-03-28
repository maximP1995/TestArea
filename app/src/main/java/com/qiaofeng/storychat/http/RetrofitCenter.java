package com.qiaofeng.storychat.http;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by zhengmj on 18-12-19.
 */

public class RetrofitCenter {
    public static final String GET_SYS_TIME = "currentTimeMillis";
    public static final String TEST_POST = "api/person";

    private final String DEFAULT_ADDRESS = "https://chanchifeng.com/qiaofeng/";
    private Retrofit retrofit;
    private IHttpFunc iHttpAddress;
    public RetrofitCenter(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new PublicParameterInterceptor());
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("url_info", "url_info=" + message);
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(httpLoggingInterceptor);//这里是日志监听
        builder.connectTimeout(5, TimeUnit.SECONDS);
        builder.readTimeout(5, TimeUnit.SECONDS);
        builder.writeTimeout(5, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);//错误重连
        retrofit = new Retrofit.Builder()
                .client(builder.build())
//                .client(new OkHttpClient())
                .baseUrl(DEFAULT_ADDRESS)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        iHttpAddress = retrofit.create(IHttpFunc.class);
    }
    public IHttpFunc doHttpLink(){
        return iHttpAddress;
    }
}
