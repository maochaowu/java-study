package com.mcw.cora.base.java8;

/**
 *@desc
 *@author mcw
 *@date 2019/11/20
 */
public class LambalDemo {

    private boolean flag = false;

    public void processed() {
        if (!flag) {
            System.out.println("第一次执行");
            flag = true;
            doProcessed(this::processed);
        }
        System.out.println("结束");
    }

    private void doProcessed(LambalDemoCallback callback) {
        callback.doWork();
    }

    public static void main(String[] args) {
        LambalDemo demo = new LambalDemo();
        demo.processed();
    }
}
