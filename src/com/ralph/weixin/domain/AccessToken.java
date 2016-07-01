package com.ralph.weixin.domain;

/**
 * Created by Ralph on 2016/7/1.
 */
public class AccessToken
{

    /**
     * access_token : ACCESS_TOKEN
     * expires_in : 7200
     */

    private String access_token;
    private int expires_in;

    public String getAccess_token()
    {
        return access_token;
    }

    public void setAccess_token(String access_token)
    {
        this.access_token = access_token;
    }

    public int getExpires_in()
    {
        return expires_in;
    }

    public void setExpires_in(int expires_in)
    {
        this.expires_in = expires_in;
    }
}
