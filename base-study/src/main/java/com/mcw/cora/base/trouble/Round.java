package com.mcw.cora.base.trouble;

import java.util.Random;

/**
 * @Author yibi
 * Date 2019/6/1 20:34
 * Version 1.0
 **/
public class Round {
    public static void main(String[] args) {
        Random rnd = new Random();
        int i = rnd.nextInt();
        if (Math.round(i) != i) {
            System.out.println("Ground Round");
        }
    }
}
