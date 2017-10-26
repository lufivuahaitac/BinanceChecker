package vn.vnpay.beans;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author truongnq
 */
public class Bean {
    private String symbol;
    private double price;
    private long timestamp;

    public long getTimestamp() {
        return timestamp;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    public void setTimestamp() {
        this.timestamp = System.currentTimeMillis();
    }
}
