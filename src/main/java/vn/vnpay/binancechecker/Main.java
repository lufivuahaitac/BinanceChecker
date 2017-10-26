/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.vnpay.binancechecker;

import vn.vnpay.utils.SequenceNumber;
import vn.vnpay.utils.Utils;

/**
 *
 * @author truongnq
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        while(true){
            Utils.getInstance().getPrice("LINKETH");
            Thread.sleep(2000);
                //System.out.println(SequenceNumber.getInstance().next());
        }
    }
}
