package com.ewisselectronic.sulama.sulamaservice.config;

import com.ewisselectronic.sulama.sulamacore.logging.Log;
import com.ewisselectronic.sulama.sulamacore.service.SystemSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import static com.ewisselectronic.sulama.sulamaservice.utils.ServiceUtils.updateGlobalData;

@Component
public class GlobalDataLoader implements ApplicationRunner {

    @Autowired
    private SystemSettingsService systemSettingsService;

    @Value("${project.appTag}")
    private String appTag;

    @Value("${project.mainPath}}")
    private String mainPath;

    public static boolean initialize = false;

    @Override
    public void run(ApplicationArguments args) {
        try {
            do {
                updateGlobalData(systemSettingsService);
                Thread.sleep(10 * 1000);
            } while (initialize == false);
        } catch (Exception e) {
            Log.error("Sulama service global data loader exception. Message: %s", e.getMessage());
            System.exit(-1);
        }
    }

}
