package com.mcw.cora.base.trouble;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author yibi
 * Date 2019/6/1 10:46
 * Version 1.0
 * <p>
 * <p>
 * 0: new           #2                  // class java/util/HashSet
 * 3: dup
 * 4: invokespecial #3                  // Method java/util/HashSet."<init>":()V
 * 7: astore_1
 * 8: iconst_0
 * 9: istore_2
 * 10: iload_2
 * 11: bipush        100
 * 13: if_icmpge     48
 * 16: aload_1
 * 17: iload_2
 * 18: invokestatic  #4                  // Method java/lang/Short.valueOf:(S)Ljava/lang/Short;
 * 21: invokeinterface #5,  2            // InterfaceMethod java/util/Set.add:(Ljava/lang/Object;)Z
 * 26: pop
 * 27: aload_1
 * 28: iload_2
 * 29: iconst_1
 * 30: isub
 * 31: invokestatic  #6                  // Method java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
 * 34: invokeinterface #7,  2            // InterfaceMethod java/util/Set.remove:(Ljava/lang/Object;)Z
 * 39: pop
 **/
public class ShortSet {
    public static void main(String[] args) {
        Set<Short> sortSet = new HashSet<Short>();
        for (short i = 0; i < 100; i++) {
            sortSet.add(i);
            //此处i-1 会将i转换成int之后，在进行减法操作，最后结果是int
            //remove是接收一个Object，所以int会boxing成Integer，
            //从字节码的第31条指令可以看出，会通过Integer.valueOf完成封箱操作。
            //由于remove过程中，hashMap考虑的是输入对象的hashcode和equals
            //此处虽然Short 1和 Integer 1的hashcode相同，但是两者并不相等，即不equals。
            sortSet.remove(i - 1);
        }
        System.out.println(sortSet.size());
    }
}
