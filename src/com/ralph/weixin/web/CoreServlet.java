package com.ralph.weixin.web;

import com.ralph.weixin.domain.RespType;
import com.ralph.weixin.domain.TextMsg;
import com.ralph.weixin.domain.req.*;
import com.ralph.weixin.util.Constants;
import com.ralph.weixin.util.MessageUtil;
import com.ralph.weixin.util.SignUtil;
import com.ralph.weixin.util.StrUtil;
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
import java.util.List;
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
        if (SignUtil.checkSignature(Constants.TOKEN, signature, timestamp, nonce))
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

        Map<String, Object> reqMap = MessageUtil.parseXml(req, Constants.TOKEN, Constants.APPID, null);
        String fromUserName = (String) reqMap.get("FromUserName");
        String toUserName = (String) reqMap.get("ToUserName");
        String msgType = (String) reqMap.get("MsgType");

        LOG.debug("收到消息,消息类型:{}", msgType);

        String res = " ";
        if (msgType.equals(ReqType.EVENT))
        {
            String eventType = (String) reqMap.get("Event");
            String ticket = (String) reqMap.get("Ticket");
            if (StrUtil.isNotBlank(ticket))
            {
                String eventKey = (String) reqMap.get("EventKey");
                LOG.debug("eventKey:{}", eventKey);
                LOG.debug("ticket:{}", ticket);
            }
            if (eventType.equals(EventType.SUBSCRIBE))
            {

            } else if (eventType.equals(EventType.UNSUBSCRIBE))
            {

            } else if (eventType.equals(EventType.CLICK))
            {
                String eventKey = (String) reqMap.get("EventKey");
                LOG.debug("eventKey:{}", eventKey);
                MenuEvent event = new MenuEvent(eventKey);
            } else if (eventType.equals(EventType.VIEW))
            {
                String eventKey = (String) reqMap.get("EventKey");
                LOG.debug("eventKey:{}", eventKey);
                MenuEvent event = new MenuEvent(eventKey);
            } else if (eventType.equals(EventType.LOCATION))
            {
                double latitude = Double.parseDouble((String) reqMap.get("Latitude"));
                double longitude = Double.parseDouble((String) reqMap.get("Longitude"));
                double precision = Double.parseDouble((String) reqMap.get("Precision"));
                LocationEvent event = new LocationEvent(latitude, longitude,
                        precision);
            } else if (EventType.SCANCODEPUSH.equals(eventType) || EventType.SCANCODEWAITMSG.equals(eventType))
            {
                String eventKey = (String) reqMap.get("EventKey");
                Map<String, Object> scanCodeInfo = (Map<String, Object>) reqMap.get("ScanCodeInfo");
                String scanType = (String) scanCodeInfo.get("ScanType");
                String scanResult = (String) scanCodeInfo.get("ScanResult");
                ScanCodeEvent event = new ScanCodeEvent(eventKey, scanType, scanResult);
            } else if (EventType.PICPHOTOORALBUM.equals(eventType) || EventType.PICSYSPHOTO.equals(eventType) || EventType.PICWEIXIN.equals(eventType))
            {
                String eventKey = (String) reqMap.get("EventKey");
                Map<String, Object> sendPicsInfo = (Map<String, Object>) reqMap.get("SendPicsInfo");
                int count = Integer.parseInt((String) sendPicsInfo.get("Count"));
                List<Map> picList = (List) sendPicsInfo.get("PicList");
                SendPicsInfoEvent event = new SendPicsInfoEvent(eventKey, count, picList);
            } else if (EventType.TEMPLATESENDJOBFINISH.equals(eventType))
            {
                String msgId = (String) reqMap.get("MsgID");
                String status = (String) reqMap.get("Status");
                TemplateMsgEvent event = new TemplateMsgEvent(msgId, status);

            } else if (EventType.MASSSENDJOBFINISH.equals(eventType))
            {
                String msgId = (String) reqMap.get("MsgID");
                String status = (String) reqMap.get("Status");
                Integer TotalCount = Integer.valueOf(String.valueOf(reqMap.get("TotalCount")));
                Integer filterCount = Integer.valueOf(String.valueOf(reqMap.get("FilterCount")));
                Integer sentCount = Integer.valueOf(String.valueOf(reqMap.get("SentCount")));
                Integer errorCount = Integer.valueOf(String.valueOf(reqMap.get("ErrorCount")));
                SendMessageEvent event = new SendMessageEvent(msgId, status, TotalCount, filterCount, sentCount, errorCount);
            }
        } else
        {
            if (msgType.equals(ReqType.TEXT))
            {
                String content = (String) reqMap.get("Content");
                LOG.debug("文本消息内容:{}", content);
                if ("日期".equals(content) || "date".equalsIgnoreCase(content))
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
                } else
                {
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

    private void buildBasicReqMsg(Map<String, Object> reqMap, BaseReqMsg reqMsg)
    {
        addBasicReqParams(reqMap, reqMsg);
        reqMsg.setMsgId((String) reqMap.get("MsgId"));
    }

    private void addBasicReqParams(Map<String, Object> reqMap, BaseReq req)
    {
        req.setMsgType((String) reqMap.get("MsgType"));
        req.setFromUserName((String) reqMap.get("FromUserName"));
        req.setToUserName((String) reqMap.get("ToUserName"));
        req.setCreateTime(Long.parseLong((String) reqMap.get("CreateTime")));
    }
}
