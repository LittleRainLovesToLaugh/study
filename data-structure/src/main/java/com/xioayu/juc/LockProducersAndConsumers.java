package com.xioayu.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Description  Lock版 生产者和消费者问题
 * 演示Lock和Condition的使用
 * @author 小雨
 * createTime 2022年06月05日 10:55:00
 */
public class LockProducersAndConsumers {

    public static void main(String[] args) {
        Date2 date = new Date2();
        new Thread(() -> {for (int i = 0; i < 10; i++)date.increment();}, "A").start();
        new Thread(() -> {for (int i = 0; i < 10; i++)date.decrement();}, "B").start();
        new Thread(() -> {for (int i = 0; i < 10; i++)date.increment();}, "C").start();
        new Thread(() -> {for (int i = 0; i < 10; i++)date.decrement();}, "D").start();
    }

}
// 数字 资源类, 不建议实现Runable接口，真实的业务融合性必须降低
class Date2 {

    private int number = 0;

    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    // +1
    public void increment() {
        lock.lock();
        try {
            // 业务代码
            while (number != 0) {
                condition.await(); // 等待
            }
            number++;
            System.out.println(Thread.currentThread().getName() + "number的值==>" + number);
            condition.signalAll();  // 通知其他线程
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    // -1
    public void decrement() {
        lock.lock();
        try {
            while (number == 0) {
                condition.await(); // 等待
            }
            number--;
            System.out.println(Thread.currentThread().getName() + "number的值==>" + number);
            condition.signalAll();  // 通知其他线程
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}