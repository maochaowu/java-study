package com.mcw.cora.base.trouble;

/**
 * @Author yibi
 * Date 2019/6/1 20:19
 * Version 1.0
 **/
public class MathAbs {
    public static void main(String[] args) {
        //Math的abs方法并不是永远都返回的是正数，比如下面的就不是。
        System.out.println(Math.abs(Integer.MIN_VALUE));
    }
}
