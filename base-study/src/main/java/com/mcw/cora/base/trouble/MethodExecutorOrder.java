package com.mcw.cora.base.trouble;

/**
 * @Author yibi
 * Date 2019/6/1 13:57
 * Version 1.0
 * 方法执行顺序
 *
 *  static {};
 *     descriptor: ()V
 *     flags: ACC_STATIC
 *     Code:
 *       stack=2, locals=0, args_size=0
 *          0: new           #13                 // class com/mcw/cora/base/trouble/MethodExecutorOrder
 *          3: dup
 *          4: invokespecial #14                 // Method "<init>":()V
 *          7: putstatic     #7                  // Field ELVIS:Lcom/mcw/cora/base/trouble/MethodExecutorOrder;
 *         10: iconst_1
 *         11: invokestatic  #15                 // Method java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
 *         14: putstatic     #5                  // Field LIVING:Ljava/lang/Boolean;
 *         17: return
 **/
public class MethodExecutorOrder {

    public static final MethodExecutorOrder ELVIS = new MethodExecutorOrder();

    private MethodExecutorOrder() {
    }

    private static final Boolean LIVING = true;

    private final Boolean alive = LIVING;

    public final Boolean lives() {
        return alive;
    }
    //静态变量的赋值是在静态块中执行的，且据有先后顺序，先后顺序即定义的顺序；
    //成员变量在构造方式init执行时进行初始化赋值；
    //所以这里的执行顺序是首先执行 ELVIS = new MethodExecutorOrder() 这样会进行类的创建，并执行类的init方法
    //执行init方法之后，alive会被赋值，赋值为空，因为此时LIVING还未进行初始化。
    //Boolean LIVING = true，接下来执行该赋值。
    public static void main(String[] args) {
        System.out.println(ELVIS.alive);

        System.out.println(ELVIS.lives() ?
                "Hound Dog" : "Heartbreak Hotel");
    }

}
