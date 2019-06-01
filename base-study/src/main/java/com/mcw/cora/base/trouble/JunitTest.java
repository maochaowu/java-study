package com.mcw.cora.base.trouble;

/**
 * @Author yibi
 * Date 2019/6/1 13:40
 * Version 1.0
 **/
public class JunitTest extends junit.framework.TestCase{

    int number;

    public void test() {
        number = 0;
        Thread tt = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(number);
                assertEquals(2, number);
            }
        });
        number = 1;
        tt.start();
        number++;
        try {
            tt.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
