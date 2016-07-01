package com.ralph.weixin.web;

import com.ralph.weixin.domain.AccessToken;
import com.ralph.weixin.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Ralph on 2016/7/1.
 */
public class UpdateKey extends HttpServlet
{
    private static final Logger LOG = LoggerFactory.getLogger(UpdateKey.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setCharacterEncoding("utf-8");
        String res = "";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.weixin.qq.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetKeyService gs = retrofit.create(GetKeyService.class);
        Call<AccessToken> call = gs.getAccessKey(Constants.GRANT_TYPE, Constants.APPID,Constants.APPSECRET);
        try{
            Response<AccessToken> resp = call.execute();
            AccessToken t = resp.body();
            Constants.ACCESS_TOKEN = t;
            res = Constants.ACCESS_TOKEN.getAccess_token();
        }catch (Exception e){
            LOG.error("获取AccessToken失败！",e);
            res = "获取AccessToken失败！";
        }

        request.setAttribute("msg",res);
        request.getRequestDispatcher("msg.jsp").forward(request,response);
    }
}
