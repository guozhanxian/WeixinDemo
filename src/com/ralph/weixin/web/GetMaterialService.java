package com.ralph.weixin.web;

import com.ralph.weixin.domain.AccessToken;
import com.ralph.weixin.domain.Material;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Ralph on 2016/7/1.
 */
public interface GetMaterialService
{
    @FormUrlEncoded
    @POST("/cgi-bin/material/batchget_material")
    Call<Material> getAccessKey(@Query("access_token") String accessToken, @Field("type") String type, @Field("offset") String offset, @Field("count") String count);
}
