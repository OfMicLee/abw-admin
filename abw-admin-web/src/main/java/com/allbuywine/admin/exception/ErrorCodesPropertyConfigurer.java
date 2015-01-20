package com.allbuywine.admin.exception;

import com.allbuywine.admin.bean.exception.ErrorHandler;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.Properties;


public class ErrorCodesPropertyConfigurer extends PropertyPlaceholderConfigurer
{
    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props){
            
        super.processProperties(beanFactory, props);

        ErrorHandler.loadErrorCodes(props);
    }
}
