package com.ralph.weixin.web;

import com.ralph.weixin.domain.AccessToken;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Ralph on 2016/7/1.
 */
public interface GetKeyService
{
    @GET("/cgi-bin/token")
    Call<AccessToken> getAccessKey(@Query("grant_type") String clientCredential, @Query("appid") String appId, @Query("secret") String secret);
}
