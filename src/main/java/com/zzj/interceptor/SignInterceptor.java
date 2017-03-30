package com.zzj.interceptor;

import com.zzj.utils.SignUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by mulder on 2017/2/12.
 */
public class SignInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        String sign = "";
        Map paramMap =  request.getParameterMap();
        if(paramMap.size()==0){
            return false;
        }
        Iterator<Map.Entry<String, String[]>> entries = paramMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, String[]> entry = entries.next();
            String key = entry.getKey();
            if(key.equals("sign")){
                for (String val : entry.getValue() ) {
                    sign = val;
                }
            }
        }
        if("".equals(sign)){
            return false;
        }

        String authcode = "123";
        String serverSign =  SignUtils.buildServerSign(paramMap,authcode);
        if(serverSign.equals(sign)){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }

}
