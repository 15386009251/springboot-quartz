package com.quartz.demo.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.ResourceEntityResolver;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 用于持有spring的applicationContext,一个系统只能有一个ApplicationContextHolder <br />
 * 
 * <pre>
 * 在java代码中则可以如此使用:
 * BlogDao dao = (BlogDao)SpringContextHolder.getBean("blogDao");
 * </pre>
 * @author
 * Created by LIU on 2018/10/9 15:33
 */
@Component
public class SpringContextHolder implements ApplicationContextAware, DisposableBean {

    /**
     * @Fields LOGGER : 日志操作
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SpringContextHolder.class);

    /**
     * @Fields applicationContext : Spring上下文
     */
    private static ApplicationContext applicationContext;

    /**
     * 实现ApplicationContextAware接口, 注入Context到静态变量中.
     * @param context spring上下文
     */
    @Override
    public void setApplicationContext(final ApplicationContext context) {
        LOGGER.debug("Injecting 'ApplicationContext' to SpringContextHolder:" + applicationContext);
        if (applicationContext != null) {
            throw new IllegalStateException("ApplicationContextHolder already holded 'applicationContext'.");
        }
        applicationContext = context;
    }

    /**
     * 取得存储在静态变量中的ApplicationContext.
     * @return {@link ApplicationContext}
     */
    public static ApplicationContext getApplicationContext() {
        if (applicationContext == null) {
            throw new IllegalStateException("'applicationContext' property is null, ApplicationContextHolder not yet init.");
        }
        return applicationContext;
    }

    /**
     * @Title: getBeanDefinitionNames
     * @Description: 获取所有定义的bean的名称
     * @return bean的名称数组
     */
    public static String[] getBeanDefinitionNames() {
        return getApplicationContext().getBeanDefinitionNames();
    }

    /**
     * @Title: getBean
     * @Description: 根据bean名称从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     * @param beanName bean名称
     * @param <T> bean的类型
     * @return bean实例
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(final String beanName) {
        return (T) getApplicationContext().getBean(beanName);
    }

    /**
     * @Title: getBean
     * @Description: 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     * @param clazz bean类型
     * @param <T> bean的类型
     * @return bean实例
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBeanByType(final Class<? extends T> clazz) {
        try {
            final String[] beanNames = getApplicationContext().getBeanNamesForType(clazz);
            if (beanNames != null && beanNames.length == 1) {
                return (T) getApplicationContext().getBean(beanNames[0]);
            }
            if (beanNames == null || beanNames.length == 0) {
                throw new IllegalStateException("未找到指定类型的Bean定义.");
            }
            throw new IllegalStateException("找到多个同类型的Bean定义.");
        } catch (final Throwable th) {
            LOGGER.error("根据类型在Spring上下文查找对象出错:" + clazz, th);
            throw new IllegalStateException("根据类型在Spring上下文查找对象出错:" + clazz);
        }
    }

    /**
     * @Title: getBeanListByType
     * @Description: 取指定类型的Bean列表集合,如果不存则抛出异常IllegalStateException.
     * @param type
     * @return 指定类型的bean列表
     */
    @SuppressWarnings("unchecked")
    public static <E> List<E> getBeanListByType(final Class<? extends E> type) {
        try {
            final String[] beanNames = getApplicationContext().getBeanNamesForType(type);
            if (beanNames == null || beanNames.length == 0) {
                throw new IllegalStateException("找不到该类型的Bean定义.");
            }
            final List<E> list = new ArrayList<E>(beanNames.length);
            for (final String beanName : beanNames) {
                list.add((E) getBean(beanName));
            }
            return list;

        } catch (final Throwable th) {
            LOGGER.error("根据类型在Spring上下文查找对象出错:" + type, th);
            throw new IllegalStateException("根据类型在Spring上下文查找对象出错:" + type);
        }
    }

    /**
     * @Title: getBeanNamesForType
     * @Description: 根据bean的类型，获取所有该类型的bean名称
     * @param clazz bean的类型
     * @return bean的名称数组
     */
    public static String[] getBeanNamesForType(final Class<?> clazz) {
        final String[] beanNames = getApplicationContext().getBeanNamesForType(clazz);
        Arrays.sort(beanNames);
        return beanNames;
    }

    /**
     * 从指定的配置文件中加载Bean到ApplicationContext
     * @param configLocationString 指定配置文件路径
     */
    public static void loadBean(final String configLocationString) {
        final ConfigurableApplicationContext ctx = (ConfigurableApplicationContext) getApplicationContext();
        final XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader((BeanDefinitionRegistry) ctx.getBeanFactory());
        beanDefinitionReader.setResourceLoader(getApplicationContext());
        beanDefinitionReader.setEntityResolver(new ResourceEntityResolver(getApplicationContext()));
        try {
            final String[] configLocations = new String[] { configLocationString };
            for (final String configLocation : configLocations) {
                beanDefinitionReader.loadBeanDefinitions(getApplicationContext().getResources(configLocation));
            }
        } catch (final BeansException e) {
            throw GloubFunc.getException(e);
        } catch (final IOException e) {
            throw GloubFunc.getException(e);
        }
    }

    /**
     * 清除SpringContextHolder中的ApplicationContext为Null.
     */
    public static void cleanApplicationContext() {
        LOGGER.debug("Cleaning 'ApplicationContext' in SpringContextHolder:" + applicationContext);
        applicationContext = null;
    }

    /**
     * 实现DisposableBean接口, 在Context关闭时清理静态变量.
     */
    @Override
    public void destroy() {
        cleanApplicationContext();
    }
}
