package com.intellias.module12;

public class FirstTask {

    private static final int FIVE_SEC_IN_MILLIS = 5000;
    private static final int ONE_SEC_IN_MILLIS = 1000;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(FIVE_SEC_IN_MILLIS);
                    System.out.println("Five seconds have passed");

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        long startTime = System.currentTimeMillis();
        while (true) {
            Thread.sleep(ONE_SEC_IN_MILLIS);
            long differenceTimeSec = (System.currentTimeMillis() - startTime) / 1000;
            System.out.println(differenceTimeSec + " seconds have passed");
        }

    }
}
