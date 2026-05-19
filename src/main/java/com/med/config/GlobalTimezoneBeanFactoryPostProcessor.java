package com.med.config;


/*
  @author   george
  @project   elqueuebeta
  @class  GlobalTimezoneBeanFactoryPostProcessor
  @version  1.0.0 
  @since 13.05.26 - 14.27
*/

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.TimeZone;

@Component
public class GlobalTimezoneBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+2"));
    }
}
