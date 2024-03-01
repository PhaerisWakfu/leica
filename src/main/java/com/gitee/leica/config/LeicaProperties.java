package com.gitee.leica.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

/**
 * @author wyh
 * @since 2024/1/19
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "leica")
@SuppressWarnings("SpellCheckingInspection")
public class LeicaProperties {

    @NestedConfigurationProperty
    public WebDriver webDriver = new WebDriver();

    @Data
    public static class WebDriver {

        /**
         * web driver 路径
         */
        private String driverPath = "D:\\soft\\chrome driver\\chromedriver.exe";

        /**
         * 浏览器二进制文件路径
         */
        private String binaryPath;
    }
}
