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
import org.apache.commons.configuration.XMLConfiguration;
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
    
    private static PropertiesConfiguration notifyConfig;

    public static String prefix;
    
    

    
    @Override
    public void run() {
        initConfig();
        ClientManager.getInstance().start();
        session = ClientManager.getInstance().getQrSession();
        TopupWalletClientManager.getInstance().start();
        toupWalletSession = TopupWalletClientManager.getInstance().getQrSession();
        
    }

    public static void destroy() {
        try {
            ThreadPoolManager.getInstance().shutdown();
            logger.info("Clear Thread success");
            ClientManager.getInstance().stop();
            logger.info("Clear Netty success");
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
            String databaseConfigPath = "config/DatabaseConfig.properties";
            databaseConfig = new PropertiesConfiguration(databaseConfigPath);
            databaseConfig.setReloadingStrategy(new FileChangedReloadingStrategy());
            
            String procedureConfigPath = "config/procedure.properties";
            procedureConfig = new PropertiesConfiguration(procedureConfigPath);
            procedureConfig.setReloadingStrategy(new FileChangedReloadingStrategy());
            
            String notifyConfigPath = "config/NotifyConfig.properties";
            notifyConfig = new PropertiesConfiguration(notifyConfigPath);
            notifyConfig.setReloadingStrategy(new FileChangedReloadingStrategy());
            
            String dataConfigPath = "config/DataConfig.properties";
            dataConfig = new PropertiesConfiguration(dataConfigPath);
            dataConfig.setReloadingStrategy(new FileChangedReloadingStrategy());
            
            String partnerConfigPath = "config/PartnerConfig.xml";
            partnerConfig = new XMLConfiguration(partnerConfigPath);
            FileChangedReloadingStrategy partnerConfigStrategy = new FileChangedReloadingStrategy();
            partnerConfigStrategy.setConfiguration(partnerConfig);
            partnerConfigStrategy.init();
            partnerConfig.setReloadingStrategy(new FileChangedReloadingStrategy());
            
            String rescodeMapConfigPath = "config/RescodeMap.xml";
            rescodeMap = new XMLConfiguration(rescodeMapConfigPath);
            FileChangedReloadingStrategy rescodeConfigStrategy = new FileChangedReloadingStrategy();
            rescodeConfigStrategy.setConfiguration(rescodeMap);
            rescodeConfigStrategy.init();
            rescodeMap.setReloadingStrategy(new FileChangedReloadingStrategy());
            
            tracePath = "config/Trace.txt";
            
            iso8585ConfigPath = "config/iso87ascii.xml";
            
            String nettyConfigPath = "config/NettyConfig.properties";
            nettyConfig = new PropertiesConfiguration(nettyConfigPath);
            nettyConfig.setReloadingStrategy(new FileChangedReloadingStrategy());
            
            privateRsaKey = "config/private.rsa";
            
            publicRsaKey = "config/public.rsa";
              
            String rescodeConfigPath = "config/Rescode.properties";
            rescodeConfig = new PropertiesConfiguration(rescodeConfigPath);
            rescodeConfig.setReloadingStrategy(new FileChangedReloadingStrategy());

            String comeMethodConfigPath = "config/ComeMethod.xml";
            comeMethod = new XMLConfiguration(comeMethodConfigPath);
            FileChangedReloadingStrategy comeMethodConfigStrategy = new FileChangedReloadingStrategy();
            comeMethodConfigStrategy.setConfiguration(comeMethod);
            comeMethodConfigStrategy.init();
            comeMethod.setReloadingStrategy(new FileChangedReloadingStrategy());
        } catch (Exception e) {
            logger.error("Load file config failed ex: {}.", e);
        }
    }

    /**
     *
     * @return
     */
    public static PropertiesConfiguration getDatabaseConfig() {
        return databaseConfig;
    }

    /**
     *
     * @return
     */
    public static PropertiesConfiguration getApiConfig() {
        return apiConfig;
    }

    /**
     *
     * @return
     */
    public static PropertiesConfiguration getProcedureConfig() {
        return procedureConfig;
    }
    
    public static XMLConfiguration getPartnerConfig() {
        return partnerConfig;
    }
    
    /**
     *
     * @return
     */
    public static PropertiesConfiguration getNotifyConfig() {
        return notifyConfig;
    }
    
    /**
     *
     * @return
     */
    public static PropertiesConfiguration getDataConfig() {
        return dataConfig;
    }
    
    /**
     *
     * @return
     */
    public static PropertiesConfiguration getNettyConfig() {
        return nettyConfig;
    }
    
    /**
     *
     * @return
     */
    public static PropertiesConfiguration getRescodeConfig() {
        return rescodeConfig;
    }
    /**
     *
     * @return
     */
    public static XMLConfiguration comeMethod() {
        return comeMethod;
    }
    /**
     *
     * @return
     */
    public static XMLConfiguration getRescodeMap() {
        return rescodeMap;
    }

    /**
     *
     * @return
     */
    public static AirSession getAirSession() {
        return session;
    }
    /**
     *
     * @return
     */
    public static TopupWalletSession getToupWalletSession() {
        return toupWalletSession;
    }


    public static String getPath() {
        return prefix;
    }
    
    public static String tracePath(){
        return tracePath;
    }
    
    public static String getIsoConfig(){
        return iso8585ConfigPath;
    }
    
    public static String getPrivateRsaKey() {
        return privateRsaKey;
    }

    public static String getPublicRsaKey() {
        return publicRsaKey;
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
