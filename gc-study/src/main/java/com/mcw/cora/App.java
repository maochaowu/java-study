package com.mcw.cora;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        App app = new App();
        System.out.println("Created " + app);
        for (int i = 0; i < 1_000_000_000; i++) {
            if (i % 1_000_00 == 0) {
                System.gc();
            }
        }
        System.out.println("done.");
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("finalize object " + this);
    }
}
