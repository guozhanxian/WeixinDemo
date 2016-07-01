package com.ralph.weixin.web;

import com.ralph.weixin.domain.Material;
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
public class GetMaterial extends HttpServlet
{
    private static final Logger LOG = LoggerFactory.getLogger(GetMaterial.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setCharacterEncoding("utf-8");
        String materialType = request.getParameter("materialType");

        String res = "";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.weixin.qq.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetMaterialService gm = retrofit.create(GetMaterialService.class);
        Call<Material> call = gm.getAccessKey(Constants.ACCESS_TOKEN.getAccess_token(),materialType,"0","20");
        try{
            Response<Material> resp = call.execute();
            Material t = resp.body();

            if(t!=null)
            {
                LOG.debug(t.getTotal_count()+"<<<<"+t.getItem_count());
            }else{
                LOG.debug("没有查询到结果！");
            }
            request.setAttribute("res",t);
            request.getRequestDispatcher("viewMaterial.jsp").forward(request,response);
        }catch (Exception e){
            LOG.error("获取AccessToken失败！",e);
            res = "获取AccessToken失败！";
            request.setAttribute("msg",res);
            request.getRequestDispatcher("msg.jsp").forward(request,response);
        }
    }

}
