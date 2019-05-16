package com.retorfit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


/**
 * 学习网址  https://blog.csdn.net/carson_ho/article/details/73732076
 */
public class MainActivity extends AppCompatActivity {
    private Button mBtGet, mBtPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtGet = (Button) findViewById(R.id.button);
        mBtPost = (Button) findViewById(R.id.button2);

        mBtGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Inint();
            }
        });

        mBtPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Inint2();
            }
        });
    }

    private void Inint2() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://fanyi.youdao.com/")
                //增加返回值为String的支持
                //设置数据解析器
                .addConverterFactory(ScalarsConverterFactory.create())
                //增加返回值为Gson的支持(以实体类返回)
                .addConverterFactory(GsonConverterFactory.create())
                //增加返回值为Oservable<T>的支持，支持RXJava
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        // 创建 网络请求接口 的实例
        RequestServer reqest = retrofit.create(RequestServer.class);//这里采用的是Java的动态代理模式
        //对 发送请求 进行封装
        Call<Translation> call = reqest.getCall("I love you");
        //采用异步请求
        call.enqueue(new Callback<Translation>() {
            @Override
            public void onResponse(Call<Translation> call, Response<Translation> response) {
//                处理返回的数据结果
                Log.e("===", "return:" + response.body().getTranslateResult().get(0).get(0).getTgt());
            }

            @Override
            public void onFailure(Call<Translation> call, Throwable t) {
                Log.e("===", "失败");
                System.out.println(t.getMessage());
            }
        });
    }

    //http://gc.ditu.aliyun.com/geocoding?a=苏州市
    private void Inint() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.douban.com/v2/")
                //增加返回值为String的支持
                .addConverterFactory(ScalarsConverterFactory.create())
                //增加返回值为Gson的支持(以实体类返回)
                .addConverterFactory(GsonConverterFactory.create())
                //增加返回值为Oservable<T>的支持
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        // 创建 网络请求接口 的实例
        RequestServer reqest = retrofit.create(RequestServer.class);//这里采用的是Java的动态代理模式
        //对 发送请求 进行封装
        Call<Address> call = reqest.getString("小王子", "", 0, 3);
        //采用异步请求
        call.enqueue(new Callback<Address>() {
            @Override
            public void onResponse(Call<Address> call, Response<Address> response) {
//                处理返回的数据结果
                Log.e("===", "return:" + response.body().toString() + "成功");
            }

            @Override
            public void onFailure(Call<Address> call, Throwable t) {
                Log.e("===", "失败");
            }
        });

    }

}
