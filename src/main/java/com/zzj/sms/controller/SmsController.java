package com.zzj.sms.controller;

import com.zzj.sms.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mulder on 2016/10/27.
 */
@RestController
public class SmsController {

    @Autowired
    SmsService smsService;

    @RequestMapping("/sendSms")
    public Object sendSms(
            @RequestParam(value = "mobile", required = true) String mobile,
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "sign", required = true) String sign,
            HttpServletRequest request
    ) throws Exception {
        Map result = new HashMap();
        //  生成随机数字
        String smsCode = smsService.getSmsCode(6);
        //  发送短信
        String smsContent = "您的验证码是:"+smsCode;
        String sendResult = smsService.sendSms(mobile,smsContent);
        if(sendResult!=null){
            smsService.setSmsinfo(userId,smsCode,mobile,"1",new Date(),smsCode,sendResult);
            result.put("result","success");
            result.put("msg","短信发送成功");
        }
        else{
            result.put("result","error");
            result.put("msg","短信发送失败");
        }
        result.put("result","success");
        result.put("msg",smsContent);
        return result;
    }



}
