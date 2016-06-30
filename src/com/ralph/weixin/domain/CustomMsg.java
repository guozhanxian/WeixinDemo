package com.ralph.weixin.domain;

import com.ralph.weixin.util.MessageBuilder;

/**
 * Created by Ralph on 2016/6/30.
 */
public class CustomMsg extends BaseMsg
{

    private String kfAccount;

    public CustomMsg(String kfAccount)
    {
        this.kfAccount = kfAccount;
    }

    public String getKfAccount()
    {
        return kfAccount;
    }

    public void setKfAccount(String kfAccount)
    {
        this.kfAccount = kfAccount;
    }

    @Override
    public String toXml()
    {
        MessageBuilder mb = new MessageBuilder(super.toXml());
        mb.addData("MsgType", RespType.KF);
        mb.append("<TransInfo>\n");
        mb.addData("KfAccount", kfAccount);
        mb.append("</TransInfo>\n");
        mb.surroundWith("xml");
        return mb.toString();
    }

}
