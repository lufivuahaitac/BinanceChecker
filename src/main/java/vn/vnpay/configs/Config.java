/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.vnpay.configs;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

/**
 *
 * @author YTB
 */
public class Config implements Runnable{

    private static Logger logger;
    private static final long serialVersionUID = 1L;
    private static String log4j2Path;
    
    private static PropertiesConfiguration config;

    public static String prefix;
    
    

    
    @Override
    public void run() {
        initConfig();
        
    }

    public static void destroy() {
        try {
            logger.info("Clean up all success");
        } catch (Exception ex) {
            logger.error("Shutdown Simdanang Service have ex: {}.", ex);
        }
    }
    /**
     * initConfig
     */
    public static void initConfig() {
        prefix = "/";
        log4j2Path = "config/log4j2.xml";

        try {
            logger();
            String configPath = "config/Config.properties";
            config = new PropertiesConfiguration(configPath);
            config.setReloadingStrategy(new FileChangedReloadingStrategy());
            
        } catch (Exception e) {
            logger.error("Load file config failed ex: {}.", e);
        }
    }

    /*
     *
     * @return
     */
    public static PropertiesConfiguration getConfig() {
        return config;
    }
    

    /**
     *
     * @throws IOException
     */
    private static void logger() throws IOException {
        String configuration = log4j2Path;
        URI source = new File(configuration).toURI();
        Configurator.initialize("contextLog4J", null, source);
        logger = LogManager.getLogger(Config.class);
    }

    
}
