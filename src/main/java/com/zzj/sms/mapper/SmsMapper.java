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

}