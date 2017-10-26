/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.vnpay.binancechecker;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import vn.vnpay.configs.Config;

/**
 *
 * @author truongnq
 */
public class Main {
    public static void main(String[] args) {
        try {
            Config.initConfig();
            ScheduledExecutorService exeService = Executors.newScheduledThreadPool(1);
            long repeatTime = Config.getConfig().getLong("PERIOD");
            exeService.scheduleAtFixedRate(new Run(), 0, repeatTime, TimeUnit.SECONDS);
        } catch (Exception e) {
            System.err.println(e);
        }
        
    }
}
