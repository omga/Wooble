package com.woobledev.wooble;

import com.sinch.android.rtc.messaging.Message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 19.06.15.
 */
public class SinchMessage implements Message {

    private List<String> recipientIds;
    private String textBody;
    private HashMap<String,String> headers;

    public SinchMessage(String recepientId, String text) {
        this();
        recipientIds.add(recepientId);
        textBody = text;
        headers.put("time","sasi");
    }


    public SinchMessage() {
        this.recipientIds = new ArrayList();
        this.headers = new HashMap();
    }
    @Override
    public String getMessageId() {
        return null;
    }

    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }

    @Override
    public String getTextBody() {
        return textBody;
    }

    @Override
    public List<String> getRecipientIds() {
        return recipientIds;
    }

    @Override
    public String getSenderId() {
        return null;
    }

    @Override
    public Date getTimestamp() {
        return null;
    }
}
