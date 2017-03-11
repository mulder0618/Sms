package com.zzj.config;

/**
 * Created by mulder on 2016/11/3.
 */
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;


@Configuration
@MapperScan("com.zzj.*.mapper")
@AutoConfigureAfter(MyBatisConfig.class)
public class MyBatisMapperScannerConfig {
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("com.zzj.*.mapper");
        return mapperScannerConfigurer;
    }
}