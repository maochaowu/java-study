package com.mcw.cora.base.trouble;

import java.util.Random;

/**
 * @Author yibi
 * Date 2019/6/1 20:22
 * Version 1.0
 **/
public class Hamlet {
    public static void main(String[] args) {
        Random rnd = new Random();
        boolean toBe = rnd.nextBoolean();
        Number result = (toBe || !toBe) ?
                new Integer(3) : new Float(1);
        System.out.println(result);
    }
}
