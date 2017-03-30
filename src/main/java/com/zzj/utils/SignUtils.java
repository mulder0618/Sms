package com.zzj.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by mulder on 2017/3/26.
 */
public class SignUtils implements Comparable<SignUtils> {

    private static Logger log = Logger.getLogger(String.valueOf(SignUtils.class));

    public String s;

    public SignUtils(String s) {
        this.s = s;
    }


    /**
     * 返回服务端签名
     * @param paramMap
     * @return
     */
    public static String buildServerSign(Map paramMap, String authcode) throws Exception {
        Iterator<Map.Entry<String, String[]>> entries = paramMap.entrySet().iterator();
        String ss[] = new String[paramMap.size()-1];
        int index = 0;
        while (entries.hasNext()) {
            Map.Entry<String, String[]> entry = entries.next();
            String key = entry.getKey();
            if ("sign".equals(key)){
                continue;
            }
            else{
                ss[index] = key;
                index++;
            }
        }
        SignUtils mySs[]=new SignUtils[ss.length];//创建自定义排序的数组
        for (int i = 0; i < ss.length; i++) {
            mySs[i]=new SignUtils(ss[i]);
        }
        StringBuffer paramBuffer = new StringBuffer();
        Arrays.sort(mySs);//排序
        for (int i = 0; i < mySs.length; i++) {
            String key = mySs[i].s;
            paramBuffer.append(key);
            paramBuffer.append("=");
            try{
                String[] val = (String[]) paramMap.get(key);
                paramBuffer.append(val[0]);
            }
            catch (Exception e){
                paramBuffer.append(paramMap.get(key));
            }
            paramBuffer.append("&");
        }
        String salt = authcode;
        paramBuffer.append(salt);
        log.info("salt: " + salt);
        log.info("接收到的参数: " + paramBuffer.toString());
        String signMd5 = Md5Utils.getMD5(paramBuffer.toString());
        log.info("sha1结果: "+signMd5);
        return signMd5;
    }

    /**
     * 按ascii码排序签名字段
     * @param paramMap
     */
    public static String bulidSign(Map paramMap , String authcode){
        Iterator<Map.Entry<String, String[]>> entries = paramMap.entrySet().iterator();
        String ss[] = new String[paramMap.size()];
        int index = 0;
        while (entries.hasNext()) {
            Map.Entry<String, String[]> entry = entries.next();
            String key = entry.getKey();
            ss[index] = key;
            index++;
        }
        SignUtils mySs[]=new SignUtils[ss.length];//创建自定义排序的数组
        for (int i = 0; i < ss.length; i++) {
            mySs[i]=new SignUtils(ss[i]);
        }
        StringBuffer paramBuffer = new StringBuffer();
        Arrays.sort(mySs);//排序
        for (int i = 0; i < mySs.length; i++) {
            String key = mySs[i].s;
            paramBuffer.append(key);
            paramBuffer.append("=");
            try{
                String[] val = (String[]) paramMap.get(key);
                paramBuffer.append(val[0]);
            }
            catch (Exception e){
                paramBuffer.append(paramMap.get(key));
            }
            paramBuffer.append("&");
        }
        String salt = authcode;
        paramBuffer.append(salt);
        log.info("salt: " + salt);
        log.info("接收到的参数: " + paramBuffer.toString());
        String signMd5 = Md5Utils.getMD5(paramBuffer.toString());
        log.info("sha1结果: "+signMd5);
        return signMd5;
    }


    @Override
    public int compareTo(SignUtils o) {
        if (o == null || o.s == null) return 1;
        if (s.length() > o.s.length()) return 1;
        else if (s.length() < o.s.length()) return -1;
        return s.compareTo(o.s);
    }



    public  static  void main(String[] args){
        //用于生成客户端生成签名
        Map map = new HashMap();
        map.put("currencyCode", "1");
        map.put("orderNO", "201304111690310026");
        map.put("merchantID", "100001");
        map.put("amount","0.01");
        map.put("sign","1");
        System.out.println(SignUtils.bulidSign(map,"zH$1^&lPI&@31tg"));

        //map.put("sign", "3214123125465645654645654");

    }
}
