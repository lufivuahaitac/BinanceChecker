/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.vnpay.utils;


/**
 *
 * @author Truong
 */
public class SequenceNumber {

    public static final int MIN_VALUE = 0;
    public static final int DEFAULT_VALUE = 1;
    public static final int MAX_VALUE = 100;

    private int value;

    private SequenceNumber() {
        this.value = MIN_VALUE;
    }

    private static final class SingletonHolder {

        private static final SequenceNumber INSTANCE = new SequenceNumber();
    }

    public static SequenceNumber getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * Get the next number in this sequence's scheme. This method is
     * synchronized so its safe for multiple threads to call.
     *
     * @return
     */
    synchronized public int next() {
        // the next value is the current value
        int nextValue = this.value;
        if (this.value == MAX_VALUE) {
            this.value = MIN_VALUE;
        }
        this.value++;
        return nextValue;
    }

    /**
     * Get the next number in this sequence's scheme without causing it to move
     * to the next-in-sequence. This method returns the number that will be
     * returned by the next call to <code>next()</code> without actually
     * increasing the sequence. Multiple calls to <code>peek</code> will return
     * the same number until a call to <code>next()</code> is made.
     *
     * @return
     */
    synchronized public int peek() {
        return this.value;
    }

    /**
     * Reset the sequence scheme to the beginning of the sequence (min value
     * which is 1).
     */
    synchronized public void reset() {
        this.value = DEFAULT_VALUE;
    }

}