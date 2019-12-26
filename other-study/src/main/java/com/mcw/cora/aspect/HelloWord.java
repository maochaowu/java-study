package com.mcw.cora.aspect;

/**
 *@desc
 *@author mcw
 *@date 2019/9/4
 */
public class HelloWord {
    public void sayHello() {
        System.out.println("hello world !");
    }

    public static void main(String args[]) {
        HelloWord helloWord = new HelloWord();
        helloWord.sayHello();
    }
}
