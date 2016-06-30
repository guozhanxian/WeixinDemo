package com.ralph.weixin.domain;


import com.ralph.weixin.util.MessageBuilder;

/**
 * Created by Ralph on 2016/6/30.
 */
public class VoiceMsg extends BaseMsg
{

    private String mediaId;

    public VoiceMsg(String mediaId)
    {
        this.mediaId = mediaId;
    }

    public String getMediaId()
    {
        return mediaId;
    }

    public void setMediaId(String mediaId)
    {
        this.mediaId = mediaId;
    }

    @Override
    public String toXml()
    {
        MessageBuilder mb = new MessageBuilder(super.toXml());
        mb.addData("MsgType", RespType.VOICE);
        mb.append("<Voice>\n");
        mb.addData("MediaId", mediaId);
        mb.append("</Voice>\n");
        mb.surroundWith("xml");
        return mb.toString();
    }

}
