package com.xioayu.juc;

/**
 * Description
 *
 * @author XD
 * createTime 2022年03月29日 15:12:00
 */
/**
 * @Description 线程之间的通信问题：生产者和消费者问题！等待唤醒，通知唤醒
 * 线程交替执行 A B 操作同一个变量  num = 0
 * A num + 1
 * B num - 1
 * @Date 2020/11/10 21:43
 */

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description 线程之间的通信问题：生产者和消费者问题！等待唤醒，通知唤醒
 * 线程交替执行 A B 操作同一个变量  num = 0
 * A num + 1
 * B num - 1
 * @Date 2020/11/10 21:43
 */
public class A {



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
        }, "A").start();

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
     * @throws InterruptedException 当线程正在等待、休眠或以其他方式被占用，并且线程在活动之前或活动期间被中断时抛出。有时，方法可能希望测试当前线程是否被中断，如果是，则立即抛出此异常
     */
    public synchronized void increment() throws InterruptedException {
        if (number != 0) {
            // 等待其他线程的工作完成，此时当前线程进入等待状态（WAITING）
            this.wait();
        }
        number++;
        System.out.println(Thread.currentThread().getName() + "number的值==>" + number);
        // 唤醒所有的线程
        this.notifyAll();
    }
    /**
     * number-- 操作
     * @throws InterruptedException 当线程正在等待、休眠或以其他方式被占用，并且线程在活动之前或活动期间被中断时抛出。有时，方法可能希望测试当前线程是否被中断，如果是，则立即抛出此异常
     */
    public synchronized void decrement() throws InterruptedException {
        if (number == 0) {
            // 等待其他线程的工作完成，此时当前线程进入等待状态（WAITING）
            this.wait();
        }
        number--;
        System.out.println(Thread.currentThread().getName() + "number的值==>" + number);
        // 唤醒所有的线程
        this.notifyAll();
    }
}

