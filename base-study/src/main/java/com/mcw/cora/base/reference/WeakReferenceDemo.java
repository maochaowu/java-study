package com.mcw.cora.base.reference;

import java.lang.ref.WeakReference;

/**
 * @Author yibi
 * Date 2019/6/15 11:03
 * Version 1.0
 * 弱引用，即比强引用要弱一点的引用，当一个对象只有弱引用时，发生GC时就会进行回收。
 * -Xms20m -Xmx20m -Xmn10m -XX:SurvivorRatio=8 -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=d:/tmp/
 **/
public class WeakReferenceDemo {
    private static final int ONE_MB = 1024 * 1024;

    public static void main(String[] args) {
        byte[] byte1 = new byte[ONE_MB];
        byte[] byte2 = new byte[ONE_MB];
        byte[] byte3 = new byte[2 * ONE_MB];
        WeakReference<byte[]> byte3Ref = new WeakReference<byte[]>(byte3);
        byte3 = null;
        byte[] byte4 = new byte[2 * ONE_MB];
        byte[] byte5 = new byte[2 * ONE_MB];
        byte[] byte6 = new byte[2 * ONE_MB];
        byte[] byte7 = new byte[2 * ONE_MB];
        byte[] byte8 = new byte[2 * ONE_MB];
        byte[] byte9 = new byte[2 * ONE_MB];

    }
}
