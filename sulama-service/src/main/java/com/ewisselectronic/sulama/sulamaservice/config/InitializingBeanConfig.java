package com.ewisselectronic.sulama.sulamaservice.config;

import com.ewisselectronic.sulama.sulamacore.logging.Log;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class InitializingBeanConfig implements InitializingBean {

    @Override
    public void afterPropertiesSet(){
        System.out.println(Log.ANSI_GREEN +
                new Timestamp(System.currentTimeMillis()) + " " +
                "[ARPANET Solab Service] " +
                "Application started successfully!" +
                Log.ANSI_RESET);
    }

}
