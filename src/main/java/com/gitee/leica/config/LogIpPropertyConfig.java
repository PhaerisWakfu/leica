package com.gitee.leica.config;

import ch.qos.logback.core.PropertyDefinerBase;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author wyh
 * @since 2024/1/19
 */
@Slf4j
public class LogIpPropertyConfig extends PropertyDefinerBase {

    private static String ip;

    static {
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.error("获取日志IP地址异常", e);
            ip = null;
        }
    }

    @Override
    public String getPropertyValue() {
        return ip;
    }
}