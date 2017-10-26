/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.vnpay.binancechecker;

import vn.vnpay.configs.Config;
import vn.vnpay.utils.Utils;

/**
 *
 * @author lufiv
 */
public class Run implements Runnable{

    @Override
    public void run() {
        Utils.getInstance().getPrice(Config.getConfig().getString("SYMBOL"));
    }

    
}
