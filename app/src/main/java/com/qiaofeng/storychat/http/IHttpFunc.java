package com.qiaofeng.storychat.http;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by zhengmj on 18-12-19.
 */

public interface IHttpFunc {
    @GET(RetrofitCenter.GET_SYS_TIME)
    Call<ResponseBody> getSystemTime();

    @POST(RetrofitCenter.TEST_POST)
    @FormUrlEncoded
    Call<ResponseBody> getPerson(@Field("userName")String userName,@Field("note")String note);
}
