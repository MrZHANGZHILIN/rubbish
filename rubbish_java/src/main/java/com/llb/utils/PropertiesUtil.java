package com.llb.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * 读取配置文件工具类
 * @Author llb
 * Date on 2020/4/17
 */
public class PropertiesUtil {
    private static Properties props;

    static{
        try {
            if(props == null) {
                Resource resource = new ClassPathResource("/application.properties");
                props = PropertiesLoaderUtils.loadProperties(resource);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取属性
     * @param key
     * @return
     */
    public static String getProperty(String key) {
        return props.getProperty(key);
    }

    /**
     * 获取properyies属性
     * @return
     */
    public static Properties getProperties(){
        return props;
    }
}
