package com.gitee.leica.tools;

import com.gitee.leica.config.LeicaProperties;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wyh
 * @since 2023/8/25
 */
public class PropertiesContext {

    private static LeicaProperties properties;

    @Autowired
    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    public void setUp(LeicaProperties properties) {
        PropertiesContext.properties = properties;
    }

    public static LeicaProperties get() {
        return properties;
    }
}
