package com.zzj.sms.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/12/14.
 */
@Repository
public interface SmsMapper {


    void insertSmsinfo(Map params);

    /**
     * 设置短信不可用
     * @param parmap
     */
    int updatePhoneMessage(Map parmap);

    /**
     * 查询短信信息
     * @param parMap
     * @return
     */
    String getPhoneCode(Map parMap);

}