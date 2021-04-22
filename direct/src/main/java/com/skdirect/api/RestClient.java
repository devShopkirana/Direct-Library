package com.skdirect.api;

import android.app.Activity;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.skdirect.BuildConfig;
import com.skdirect.utils.Aes256;
import com.skdirect.utils.DirectSDK;
import com.skdirect.utils.SharePrefs;
import com.skdirect.utils.Utils;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class RestClient {
    private static Retrofit retrofit = null;
    private static final RestClient ourInstance = new RestClient();
    private static Activity mActivity;
    private Request request;
    public static RestClient getInstance() {
        //mActivity = activity;
        return ourInstance;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private RestClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(2, TimeUnit.MINUTES)
                .connectTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .addInterceptor(chain -> {
                    Response response = null;
                    try {
                        request = chain.request();
                        response = chain.proceed(request);
                        if (response.code() == 401) {
                            DirectSDK.getInstance().token();
                        }
                        if (response.code() == 200) {
                            if (!request.url().toString().contains("/token") && !request.url().toString().contains("/appVersion") && !request.url().toString().contains("/imageupload")) {
                                try {
                                    JSONObject jsonObject = new JSONObject();
                                    jsonObject.put("message", new JSONObject(response.body().string()));
                                    String data = jsonObject.getJSONObject("message").getString("Data");
                                    String destr = Aes256.decrypt(data, new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).format(new Date()) + "1201");
                                    if (BuildConfig.DEBUG) {
                                        printMsg(destr);
                                    }
                                    MediaType contentType = response.body().contentType();
                                    ResponseBody responseBody = ResponseBody.create(contentType, destr);
                                    return response.newBuilder().body(responseBody).build();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return response;

                })
                .addInterceptor(chain -> {
                    request = chain.request().newBuilder()
                            .header("SOURCEKEY", SharePrefs.getInstance(DirectSDK.getInstance().context).getString(SharePrefs.SOURCEKEY))
                            .addHeader("authorization", "Bearer " + Utils.getToken(DirectSDK.getInstance().context))
                            .build();
                    return chain.proceed(request);
                })
                .addInterceptor(interceptor)
                .build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.apiEndpoint)
                    .addConverterFactory(GsonConverterFactory.create(new Gson()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .build();
        }
    }

    public APIServices getService() {
        return retrofit.create(APIServices.class);
    }

    private void printMsg(String msg) {
        int chunkCount = msg.length() / 4050;     // integer division
        for (int i = 0; i <= chunkCount; i++) {
            int max = 4050 * (i + 1);
            if (max >= msg.length()) {
                Log.d(">>>Response::", msg.substring(4050 * i));
                // Log.d("", msg.substring(4050 * i));
            } else {
                Log.d(">>>>Response::", msg.substring(4050 * i, max));
                //  Log.d("", msg.substring(4050 * i, max));
            }
        }
    }
}