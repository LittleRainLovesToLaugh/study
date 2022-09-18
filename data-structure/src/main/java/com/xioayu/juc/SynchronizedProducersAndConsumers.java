package com.xioayu.juc;


/**
 * 线程之间的通信问题：Synchronized版生产者和消费者问题！等待唤醒，通知唤醒
 * 线程交替执行 A B 操作同一个变量  num = 0
 * A num + 1
 * B num - 1
 * 问题存在，现在只要A B两个线程是没问题的那么如果再加入C D 2个线程呢！（虚假唤醒的问题）
 * 为什么会出现虚假唤醒呢？
 * 因为wait方法可以分为三个操作：
 * （1）释放锁并阻塞
 * （2）等待条件cond发生
 * （3）获取通知后，竞争获取锁
 * 解决办法：if 改为while notify 改为notifyAll
 */
public class SynchronizedProducersAndConsumers {

    public static void main(String[] args) {
        Date date = new Date();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    date.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "SynchronizedProducersAndConsumers").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    date.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    date.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "C").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    date.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "D").start();

    }
}

/**
 * 数字 资源类, 不建议实现Runable接口，真实的业务融合性必须降低
 */
class Date {

    private int number = 0;

    /**
     * number++ 操作
     *
     * @throws InterruptedException 当线程正在等待、休眠或以其他方式被占用，并且线程在活动之前或活动期间被中断时抛出。有时，方法可能希望测试当前线程是否被中断，如果是，则立即抛出此异常
     */
    public synchronized void increment() throws InterruptedException {
        if (number != 0) {
            // 等待其他线程的工作完成，此时当前线程进入等待状态（WAITING）
            this.wait();
        }
        number++;
        System.out.println(Thread.currentThread().getName() + " \t number的值==>" + number);
        // 唤醒所有的线程
        this.notifyAll();
    }

    /**
     * number-- 操作
     *
     * @throws InterruptedException 当线程正在等待、休眠或以其他方式被占用，并且线程在活动之前或活动期间被中断时抛出。有时，方法可能希望测试当前线程是否被中断，如果是，则立即抛出此异常
     */
    public synchronized void decrement() throws InterruptedException {
        if (number == 0) {
            // 等待其他线程的工作完成，此时当前线程进入等待状态（WAITING）
            this.wait();
        }
        number--;
        System.out.println(Thread.currentThread().getName() + "\t number的值==>" + number);
        // 唤醒所有的线程
        this.notifyAll();
    }
}

