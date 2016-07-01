package com.ralph.weixin.domain;

import java.util.List;

/**
 * Created by Ralph on 2016/7/1.
 */
public class Material
{

    /**
     * total_count : TOTAL_COUNT
     * item_count : ITEM_COUNT
     * item : [{"media_id":"MEDIA_ID","name":"NAME","update_time":"UPDATE_TIME","url":"URL"}]
     */

    private String total_count;
    private String item_count;
    /**
     * media_id : MEDIA_ID
     * name : NAME
     * update_time : UPDATE_TIME
     * url : URL
     */

    private List<ItemBean> item;

    public String getTotal_count()
    {
        return total_count;
    }

    public void setTotal_count(String total_count)
    {
        this.total_count = total_count;
    }

    public String getItem_count()
    {
        return item_count;
    }

    public void setItem_count(String item_count)
    {
        this.item_count = item_count;
    }

    public List<ItemBean> getItem()
    {
        return item;
    }

    public void setItem(List<ItemBean> item)
    {
        this.item = item;
    }

    public static class ItemBean
    {
        private String media_id;
        private String name;
        private String update_time;
        private String url;

        public String getMedia_id()
        {
            return media_id;
        }

        public void setMedia_id(String media_id)
        {
            this.media_id = media_id;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public String getUpdate_time()
        {
            return update_time;
        }

        public void setUpdate_time(String update_time)
        {
            this.update_time = update_time;
        }

        public String getUrl()
        {
            return url;
        }

        public void setUrl(String url)
        {
            this.url = url;
        }
    }

    private String errcode;
    private String errmsg;

    public String getErrcode()
    {
        return errcode;
    }

    public void setErrcode(String errcode)
    {
        this.errcode = errcode;
    }

    public String getErrmsg()
    {
        return errmsg;
    }

    public void setErrmsg(String errmsg)
    {
        this.errmsg = errmsg;
    }
}
