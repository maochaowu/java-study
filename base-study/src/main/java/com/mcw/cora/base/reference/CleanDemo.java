package com.mcw.cora.base.reference;

import sun.misc.Cleaner;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;

/**
 * @Author yibi
 * Date 2019/6/23 20:27
 * Version 1.0
 **/
public class CleanDemo {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        ByteBuffer byteBuffer1 = ByteBuffer.allocateDirect(256);
        ByteBuffer byteBuffer2 = ByteBuffer.allocateDirect(256);
        ByteBuffer byteBuffer3 = ByteBuffer.allocateDirect(256);

        Field cleanerField = byteBuffer1.getClass().getDeclaredField("cleaner");
        cleanerField.setAccessible(true);
        Cleaner cleaner = (Cleaner)cleanerField.get(byteBuffer1);

    }

}
