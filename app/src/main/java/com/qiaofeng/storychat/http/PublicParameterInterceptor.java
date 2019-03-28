package com.qiaofeng.storychat.http;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by zhengmj on 18-12-20.
 */

public class PublicParameterInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String method = request.method();
        if ("POST".equals(method)){
            String oldUrl = request.url().toString();
            Log.d("200","oldUrl == "+oldUrl);
            if (request.body() instanceof  FormBody){
                FormBody oldBody = (FormBody) request.body();
                Gson gson = new Gson();
                SortedMap<String, String> map = new TreeMap<>();
                for (int i = 0; i <oldBody.size();i++){
                    map.put(oldBody.name(i),oldBody.value(i));
                }
                String json = gson.toJson(map);
                RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"),json);
                Log.d("200","json  == "+json);
                request = new Request.Builder()
                        .url(oldUrl)
                        .post(requestBody)
                        .build();
//                FormBody.Builder builder = new FormBody.Builder();
//                for (int i = 0;i<oldBody.size();i++){
//                    String name = oldBody.name(i);
//                    String value = oldBody.value(i);
//                    Log.d("200","name: "+name+" value: "+value);
//                    builder.addEncoded(name,value);
//                }
//                request = new Request.Builder().url(oldUrl).post(oldBody).build();
//                //下面用来打印
//                StringBuilder sb = new StringBuilder();
//                sb.append(request.url()).append("?");
//                for (int i = 0, m = oldBody.size(); i < m; i++) {
//                    sb.append(oldBody.encodedName(i));
//                    sb.append("=");
//                    sb.append(oldBody.encodedValue(i));
//                    if (i < m-1) sb.append("&");
//                }
//                Log.d("200", "post url==" + sb.toString());
            }else if (request.body() instanceof RequestBody){
                Log.d("200","RequestBody");
                RequestBody requestBody = request.body();

            }
        }else if ("GET".equals(method)){
           String oldUrl = request.url().toString();

           String newUrl = oldUrl+"&source=android";
           request = new Request.Builder().url(newUrl).build();

        }
        return chain.proceed(request);
    }
}
