package com.zzj.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class HttpUtils {
    private static Logger log = Logger.getLogger(HttpUtils.class.toString());
    public final static int connectTimeout = 20000;

    public static ObjectMapper objectMapper = new ObjectMapper();

    public static CloseableHttpClient getHttpClient() {
        return HttpClients.createDefault();
//        return BaseHttpClient.getHttpclient();
    }


    /**
     * https  post请求
     * @param url
     * @param map
     * @return
     */
    public static String doPost(String url, Map map, Object _header) {
        String result = null;
        CloseableHttpResponse response=null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(connectTimeout)
                .setConnectTimeout(connectTimeout)
                .setConnectionRequestTimeout(connectTimeout).build();
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            // 设置Cookie
            if (_header != null) {
                Header[] header = (Header[]) _header;
                for (Header he : header) {
                    if ("Set-Cookie".equals(he.getName())) {
                        httpPost.addHeader(he);
                    }
                }
            }
            //httpPost.addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.118 Safari/537.36");
            //设置参数
            StringBuilder sb = new StringBuilder();
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            for (Iterator it = map.keySet().iterator(); it.hasNext(); ) {
                Object key = it.next();
                list.add(new BasicNameValuePair(String.valueOf(key), String.valueOf(map.get(key))));
                sb.append(String.valueOf(key)+"="+String.valueOf(map.get(key)));
                sb.append("*********");
            }
            if (list.size() > 0) {
                UrlEncodedFormEntity entity = null;
                entity = new UrlEncodedFormEntity(list, "UTF-8");
                httpPost.setEntity(entity);
            }
            //设置重试次数为3次
            int i = 0;
            while (true){
                try{
                    response = httpClient.execute(httpPost);
                }catch(Exception e){
                    if(i>3){
                        break;
                    }
                    i++;
                }
                if (response!= null ) {
                    break;
                }
            }
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(response!=null){
                    response.close();
                }
                if(httpClient!=null){
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    /**
     * http  get请求
     *
     * @param url
     * @param headp
     * @return
     */
    public static   String doGet(String url, String params,Object headp) {
        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response=null;
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(connectTimeout)
                .setConnectTimeout(connectTimeout)
                .setConnectionRequestTimeout(connectTimeout).build();
        try {
            HttpGet httpget = new HttpGet(url+"?"+params);
            httpget.setConfig(requestConfig);
            // 设置Cookie
            if (headp != null) {
                Header[] header11 = (Header[]) headp;
                for (Header he : header11) {
                    if ("Set-Cookie".equals(he.getName())) {
                        httpget.addHeader(he);
                    }
                }
            }
            log.info("调用外部接口地址+参数："+url);
            response = getHttpClient().execute(httpget);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(response!=null){
                    response.close();
                }
                if(httpClient!=null){
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 获取ip地址
     * @param request
     * @return
     */
    public static String getIp(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }



}
