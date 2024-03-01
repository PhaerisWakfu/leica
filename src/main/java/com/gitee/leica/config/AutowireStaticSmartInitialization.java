package com.gitee.leica.config;

import com.gitee.leica.tools.PropertiesContext;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Configuration;

/**
 * 初始化所有静态操作bean的工具
 *
 * @author wyh
 * @since 2023/3/29
 */
@Configuration
public class AutowireStaticSmartInitialization implements SmartInitializingSingleton {

    private final AutowireCapableBeanFactory beanFactory;

    public AutowireStaticSmartInitialization(AutowireCapableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * 当所有的单例Bean初始化完成后，对static静态成员进行赋值
     */
    @Override
    public void afterSingletonsInstantiated() {
        //为需要bean依赖注入的类添加注入
        //但是并不会注册其本身为bean
        beanFactory.autowireBean(new PropertiesContext());
    }
}