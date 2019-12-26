package com.mcw.cora.base.trouble;

/**
 * @Author yibi
 * Date 2019/7/16 20:14
 * Version 1.0
 **/
public class LongDivision {
    private static final long SECOND_PER_DAY = 24 * 60 * 60 * 1000;

    private static final long MILLIS_PER_DAY = 24 * 60 * 60 * 1000 * 1000;

    public static void main(String[] args) {
        System.out.println(MILLIS_PER_DAY / SECOND_PER_DAY);
    }
}
