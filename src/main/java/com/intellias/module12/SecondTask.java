package com.intellias.module12;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

public class SecondTask {
    private AtomicInteger currentValue = new AtomicInteger(1);

    private final Object lock = new Object();

    private int n;

    private Queue<String> numbersToPrint = new ArrayDeque<>();

    public SecondTask(int n) {
        this.n = n;
    }

    public void fizz()  {
        while (currentValue.get() <= n) {
            synchronized (lock) {
                if (currentValue.get() % 3 == 0 && currentValue.get() % 5 != 0) {
                    currentValue.incrementAndGet();
                    numbersToPrint.add("fizz");
                    lock.notifyAll();
                } else {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    public void buzz()  {
        while (currentValue.get() <= n) {
            synchronized (lock) {
                if (currentValue.get() % 5 == 0 && currentValue.get() % 3 != 0) {
                    currentValue.incrementAndGet();
                    numbersToPrint.add("buzz");
                    lock.notifyAll();
                } else {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    public void fizzbuzz() {
        while (currentValue.get() <= n) {
            synchronized (lock) {
                if (currentValue.get() % 3 == 0 && currentValue.get() % 5 == 0) {
                    numbersToPrint.add("fizzBuzz");
                    currentValue.incrementAndGet();
                    lock.notifyAll();
                } else {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    public void number() {
        while (currentValue.get() <= n) {
            synchronized (lock) {
                if (currentValue.get() % 3 != 0 && currentValue.get() % 5 != 0) {
                    System.out.println(currentValue.getAndIncrement());
                    lock.notifyAll();
                } else {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    while (!numbersToPrint.isEmpty()) {
                        System.out.println(numbersToPrint.poll());
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        SecondTask secondTask = new SecondTask(15);

        Thread threadA = new Thread(secondTask::fizz);
        Thread threadB = new Thread(secondTask::buzz);
        Thread threadC = new Thread(secondTask::fizzbuzz);
        Thread threadD = new Thread(secondTask::number);

        threadC.start();
        threadA.start();
        threadB.start();
        threadD.start();
    }
}
