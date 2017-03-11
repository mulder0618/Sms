package com.zzj.sms.service;
import com.zzj.sms.mapper.SmsMapper;
import com.zzj.utils.HttpUtils;
import com.zzj.utils.Md5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by mulder on 17/2/3.
 */
@Service
public class SmsService {

    @Autowired
    private SmsMapper smsMapper;

    @Value("${smsadress}")
    String smsadress;

    @Value("${smsuserid}")
    String smsuserid;

    @Value("${smspassword}")
    String smspassword;

    @Value("${smsSign}")
    String smsSign;

    /**
     * 插入短信
     * @param userId
     * @param verificationKey
     * @param mobile
     * @param status
     * @param createDate
     * @param content
     */
    public void setSmsinfo(String userId,
                           String verificationKey,
                           String mobile,
                           String status,
                           Date createDate,
                           String content,
                           String sendResult){
        Map smsinfo = new HashMap();
        smsinfo.put("userId",userId);
        smsinfo.put("verificationKey",verificationKey);
        smsinfo.put("mobile",mobile);
        smsinfo.put("status",status);
        smsinfo.put("createDate",createDate);
        smsinfo.put("content",content);
        smsinfo.put("sendResult",sendResult);
        smsMapper.insertSmsinfo(smsinfo);
    }


    /**
     * 调用短信接口
     * @param mobile
     * @param smscontent
     * @return
     * @throws UnsupportedEncodingException
     */
    public String sendSms(String mobile,String smscontent) throws UnsupportedEncodingException {
        Map smsParam = new HashMap();
        smsParam.put("userid",smsuserid);
        smsParam.put("pwd", Md5Utils.getMD5(smspassword).toUpperCase());
        smsParam.put("mobile",mobile);
        smsParam.put("msgfmt","utf8");
        String content = "【"+smsSign+"】"+smscontent;
        byte[] base64Content = content.getBytes("UTF-8");
        smsParam.put("content", Base64Utils.encodeToString(base64Content));
        smsParam.put("encodeType","base64");
        String sendresult = HttpUtils.doPost(smsadress,smsParam,null);
        return sendresult;
    }

    /**
     * 生成定长的随机字符数字
     * @param length
     * @return
     */
    public String getSmsCodeWithChar(int length){
        StringBuilder sb=new StringBuilder();
        Random rand=new Random();//随机用以下三个随机生成器
        Random randdata=new Random();
        int data=0;
        for(int i=0;i<length;i++)
        {
            int index=rand.nextInt(3);
            //目的是随机选择生成数字，大小写字母
            switch(index)
            {
                case 0:
                    data=randdata.nextInt(10);//仅仅会生成0~9
                    sb.append(data);
                    break;
                case 1:
                    data=randdata.nextInt(26)+65;//保证只会产生65~90之间的整数
                    sb.append((char)data);
                    break;
                case 2:
                    data=randdata.nextInt(26)+97;//保证只会产生97~122之间的整数
                    sb.append((char)data);
                    break;
            }
        }
        String result=sb.toString();
        return result;
    }


    public String getSmsCode(int length) {
        StringBuilder sb=new StringBuilder();
        Random rand=new Random();
        for(int i=0;i<length;i++)
        {
            sb.append(rand.nextInt(10));
        }
        String data=sb.toString();
        return data;
    }

    /*public static void main(String[] args){
        new SmsService().getSmsCode(6);
    }*/

}
