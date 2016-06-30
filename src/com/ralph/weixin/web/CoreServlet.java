package com.ralph.weixin.web;

import com.ralph.weixin.domain.RespType;
import com.ralph.weixin.domain.TextMsg;
import com.ralph.weixin.domain.req.BaseReq;
import com.ralph.weixin.domain.req.BaseReqMsg;
import com.ralph.weixin.domain.req.ReqType;
import com.ralph.weixin.domain.req.TextReqMsg;
import com.ralph.weixin.util.MessageUtil;
import com.ralph.weixin.util.SignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by Ralph on 2016/6/29.
 */
public class CoreServlet extends HttpServlet
{
    private static final Logger LOG = LoggerFactory.getLogger(CoreServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");
        PrintWriter out = response.getWriter();
        // 通过检验 signature 对请求进行校验，若校验成功则原样返回 echostr，表示接入成功，否则接入失败
        if (SignUtil.checkSignature("guozhanxian", signature, timestamp, nonce))
        {
            out.print(echostr);
        }
        out.flush();
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");

        Map<String, Object> reqMap = MessageUtil.parseXml(req, "guozhanxian", "wxf679d1ffc5ff36ff", null);
        String fromUserName = (String) reqMap.get("FromUserName");
        String toUserName = (String) reqMap.get("ToUserName");
        String msgType = (String) reqMap.get("MsgType");

        LOG.debug("收到消息,消息类型:{}", msgType);

        String res = " ";
        if (msgType.equals(ReqType.EVENT))
        {

        } else
        {
            if (msgType.equals(ReqType.TEXT))
            {
                String content = (String) reqMap.get("Content");
                LOG.debug("文本消息内容:{}", content);
                if("日期".equals(content) || "date".equalsIgnoreCase(content))
                {
                    TextMsg msg = new TextMsg();
                    Date d = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    msg.setContent(sdf.format(d));
                    msg.setToUserName(fromUserName);
                    msg.setFromUserName(toUserName);
                    msg.setCreateTime(new Date().getTime());
                    msg.setMsgType(RespType.TEXT);
                    res = msg.toXml();
                }else{
                    TextMsg msg = new TextMsg();
                    Date d = new Date();
                    msg.setContent("我是蝈蝈，软件行业的颜值担当！我会的软件开发技术可多了，你想学习啥可以问我哦！");
                    msg.setToUserName(fromUserName);
                    msg.setFromUserName(toUserName);
                    msg.setCreateTime(new Date().getTime());
                    msg.setMsgType(RespType.TEXT);
                    res = msg.toXml();
                }
            }
        }

        PrintWriter out = resp.getWriter();
        out.print(res);
        out.flush();
        out.close();
    }

    private void buildBasicReqMsg(Map<String, Object> reqMap, BaseReqMsg reqMsg) {
        addBasicReqParams(reqMap, reqMsg);
        reqMsg.setMsgId((String) reqMap.get("MsgId"));
    }

    private void addBasicReqParams(Map<String, Object> reqMap, BaseReq req) {
        req.setMsgType((String) reqMap.get("MsgType"));
        req.setFromUserName((String) reqMap.get("FromUserName"));
        req.setToUserName((String) reqMap.get("ToUserName"));
        req.setCreateTime(Long.parseLong((String) reqMap.get("CreateTime")));
    }
}
