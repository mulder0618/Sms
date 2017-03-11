package com.zzj.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 获取MD5签名
 * Created by admin on 2016/12/16.
 */
public class Md5Utils {

    /**
     * MD5签名
     */
    public static synchronized String getMD5(String s) {
        String result = "";

        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(s.getBytes());
            result = toHexString(md5.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return result;
    }
    /**
     * 转换为16进制
     */
    public static String toHexString(byte[] b) {
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < b.length; ++i) {
            sb.append("0123456789abcdef".charAt(b[i] >>> 4 & 15));
            sb.append("0123456789abcdef".charAt(b[i] & 15));
        }
        return sb.toString();
    }


    public static byte[] md5Encode(String inStr) throws Exception {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            //return "";
        }

        byte[] byteArray = inStr.getBytes("GBK");
        byte[] md5Bytes = md5.digest(byteArray);
        /*StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }*/
        //System.out.print("md5hex:"+hexValue.toString());
        return md5Bytes;
    }

}
