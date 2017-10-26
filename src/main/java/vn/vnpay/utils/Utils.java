/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.vnpay.utils;

import vn.vnpay.beans.Bean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import vn.vnpay.caches.PriceCache;

/**
 *
 * @author truongnq
 */
public class Utils {

    private final SystemTray tray;
    private final Image image;
    private final TrayIcon trayIcon;

    private final OkHttpClient client;
    private final Gson gson;

    private final int stepCheck;
    private final double stepPrice;
    private final double startPrice;

    
    private static final class SingletonHolder {

        private static final Utils INSTANCE = new Utils();
    }

    public static Utils getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private Utils() {
        startPrice =  0.00081000;
        stepPrice = 0.00002;
        stepCheck = 5;
        tray = SystemTray.getSystemTray();
        image = Toolkit.getDefaultToolkit().createImage("icon.png");
        trayIcon = new TrayIcon(image, "");
        //Let the system resizes the image if needed
        trayIcon.setImageAutoSize(true);
        //Set tooltip text for the tray icon
        trayIcon.setToolTip("Ko vui rồi");
        client = new OkHttpClient();
        gson = new Gson();
    }

    public void displayTray(String title, String message) {

        try {
            tray.add(trayIcon);
            trayIcon.displayMessage(title, message, TrayIcon.MessageType.INFO);
            tray.remove(trayIcon);
        } catch (AWTException ex) {
            System.err.println(ex);
        }

    }
    
    public void getPrice(String symbol){
        try {
            Request request = new Request.Builder()
                    .url("https://www.binance.com/api/v1/ticker/allPrices")
                    .build();
            
            Response response = client.newCall(request).execute();
            String body = response.body().string();

            List<Bean> list = gson.fromJson(body, new TypeToken<ArrayList<Bean>>(){}.getType());   
            
            for(Bean b:list){
                if(symbol.equals(b.getSymbol())){
                    b.setTimestamp();
                    int current = SequenceNumber.getInstance().next();
                    PriceCache.getInstance().set(current, b);
                    System.out.printf("Hien tai: %.9f\n", b.getPrice());
                    checkPrice(b.getPrice(), startPrice);
                    Bean pre = PriceCache.getInstance().get(getPre(current, stepCheck));
                    if(null!=pre){
                        checkPrice(b.getPrice(), pre.getPrice());
                    }
                    
                }
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
        
    }
    
    private void checkPrice(double cur, double pre){
        String status= "";
        double change = cur - pre;
        if(change > 0){
            status = "Tăng";
        } else{
            status = "Giảm";
        }
        
        if(stepPrice <= Math.abs(change)){
            displayTray(status, String.format("%.9f", change));
        }
        
    }
    
    public static int getPre(int current, int step){
        if(current < step){
            return SequenceNumber.MAX_VALUE + current - step;
        }
        return current - step;
    }
    
    public static <K, V> Map<K, V> createLRUMap(final int maxEntries) {
        return new LinkedHashMap<K, V>(maxEntries*10/7, 0.7f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > maxEntries;
            }
        };
    }
}
