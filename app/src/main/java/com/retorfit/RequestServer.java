package com.retorfit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by lady_zhou on 2017/3/29.
 */

public interface RequestServer {

        @GET("book/search")
        Call<Address> getString(@Query("q") String name,
                                @Query("tag") String tag, @Query("start") int start,
                                @Query("count") int count);
    }

